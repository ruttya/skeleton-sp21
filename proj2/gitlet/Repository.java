package gitlet;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

import static gitlet.Utils.*;

/**
 * Represents a gitlet repository.
 * 因为Main.java中存在repo对象创建位置问题，所以Repo所有方法为静态方法
 * repo所有信息储存在文件中，靠文件读写处理所有操作
 *
 * @author ruttya
 */
public class Repository {

    /**
     * The current working directory.
     */
    public static final File CWD = new File(System.getProperty("user.dir"));
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    public static final File OBJS_DIR = join(GITLET_DIR, "objects");
    public static final File REFS_DIR = join(GITLET_DIR, "refs");
    public static final File HEADS_DIR = join(REFS_DIR, "heads");
    public static final File STAGING_AREA = join(GITLET_DIR, "index");
    public static final File HEAD = join(GITLET_DIR, "HEAD");

    static void init() throws IOException {
        if (GITLET_DIR.exists()) {
            System.out.println("A Gitlet version-control system already exists in the current directory.");
            System.exit(0);
        }
        GITLET_DIR.mkdir();
        OBJS_DIR.mkdir();
        REFS_DIR.mkdir();
        HEADS_DIR.mkdir();
        STAGING_AREA.createNewFile();
        HEAD.createNewFile();

        writeObject(STAGING_AREA, new HashMap<String, String>());

        //创建初始commit
        Commit commit = new Commit("initial commit", "master", new Date(0L), null, new HashMap<>());
        saveCommit(commit);

        //HEAD文件内写某branch路径:/refs/heads/master,master文件中存储commitID
        writeContents(HEAD, "ref: refs/heads/master\n");

        //此处仅更新对应branch当前所在commit，chain问题未考虑
        add2Branch("master", commit);
    }

    static String getBlob(String file) {
        byte[] content = Utils.readContents(join(CWD, file));
        String name = Utils.sha1(content);
        return name;
    }

    // 保存某个文件作为blob，
    //照此写法Blob.java可以删掉了,返回是否创建了新的blob
    static boolean saveBlob(String fileName) throws IOException {
        String name = getBlob(fileName);
        File blob = join(OBJS_DIR, name);
        if (blob.exists()) {
            return false;
        }
        blob.createNewFile();
        byte[] content = readContents(join(CWD, fileName));
        Utils.writeContents(blob, content);
        return true;
    }

    static void saveCommit(Commit commit) throws IOException {
        File file = join(OBJS_DIR, commit.getID());
        if (!file.exists()) {
            file.createNewFile();
        }
        Utils.writeObject(file, commit);
    }

    static void add2Branch(String branch, Commit commit) throws IOException {
        File file = join(HEADS_DIR, branch);
        if (!file.exists()) {
            file.createNewFile();
        }
        Utils.writeContents(file, commit.getID());
    }

    // 读取暂存区
    //@SuppressWarnings("unchecked")
    private static Map<String, String> readStagingArea() {
        if (!STAGING_AREA.exists()) {
            return new HashMap<>();
        }
        return readObject(STAGING_AREA, HashMap.class);
    }

    private static void saveStagingArea(Map<String, String> area) {
        writeObject(STAGING_AREA, (Serializable) area);
    }

    static void addFile(String name) throws IOException {
        File file = join(CWD, name);
        if (!file.exists()) {
            System.out.println("File does not exist.");
            return;
        }
        Map<String, String> stagingArea = readStagingArea();
        //新增到暂存区
        if (stagingArea.get(name) == null) {
            saveBlob(name);
            stagingArea.put(name, getBlob(name));
            // 无改动的文件移出暂存区
        } else if (stagingArea.get(name).equals(getBlob(name))) {
            stagingArea.put(name, null);
        }
        //TODO:若该文件当时正处于待删除状态（通过gitlet rm标记），此命令将撤销其待删除标记。
        saveStagingArea(stagingArea);
    }

    static void commit(String message) throws IOException {
        /**
         *1.创建完整的commit对象：
         *  author：当前branch name=HEAD文件中/后的文件名
         *  设置parent：上一行文件名的文件内容
         *  files<>：读取index文件as
         *2.更新branch内容
         *3.更新HEAD（if necessary
         *4. 关于blob。如果add之后commit之前文件内容有修改的话,以add时状态为准，所以add()中存储blob
         */
        String path = readContentsAsString(HEAD);
        String parentID = readContentsAsString(join(CWD, path));
        String[] parts = path.split("/");
        String author = parts[parts.length - 1];
        Map<String, String> files = readStagingArea();

        Commit commit = new Commit(message, author, new Date(), parentID, files);
        saveCommit(commit);
        writeContents(join(CWD, path), commit.getID());
    }

    static void rm(String fileName) {
        /**
         * 将某文件移出暂存区
         * 1.若文件已暂存（待添加）：
         * 将其移出暂存区（取消暂存）。
         * 2.若文件被当前提交跟踪（即存在于最新提交中）：
         * 标记该文件为 待删除（stage for removal）；
         * 如果用户尚未手动删除该文件，则从工作目录中物理删除该文件（仅在文件被跟踪时执行删除）。
         * Failure cases: If the file is neither staged nor tracked by the head commit,
         * print the error message No reason to remove the file.
         */
        Map<String, String> stagingArea = readStagingArea();
        Commit cur = getCurrentCommit();
        File f = join(CWD, fileName);

        if (!stagingArea.containsKey(fileName) || !f.exists()) {
            System.out.println("No reason to remove the file.");
            return;
        }
        if (cur.getFiles().containsKey(fileName)) {
            //将file加入待删除列表
            stagingArea.put(fileName, null);
            restrictedDelete(fileName);
            saveStagingArea(stagingArea);
        }
    }

    private static Commit getCurrentCommit() {
        String path = readContentsAsString(HEAD);
        String ID = readContentsAsString(join(CWD, path));
        return readObject(join(OBJS_DIR, ID), Commit.class);
    }

    private static Commit getParent(Commit commit) {
        String parID = commit.getParent();
        return readObject(join(OBJS_DIR, parID), Commit.class);
    }

    static void log() {
        Commit cur = getCurrentCommit();
        while (cur != null) {
            cur.printCommit();
            cur = getParent(cur);
        }
    }

    static void globalLog() {
        List<String> branchs = plainFilenamesIn(HEADS_DIR);
        for (String branch : branchs) {
            String id = readContentsAsString(join(CWD, branch));
            Commit cur = readObject(join(OBJS_DIR, id), Commit.class);
            while (cur != null) {
                cur.printCommit();
                cur = getParent(cur);
            }
        }
    }

    //find commit by message and print commitID
    static void find(String message) {
        List<String> res = new ArrayList<>();
        List<String> branches = plainFilenamesIn(HEADS_DIR);
        assert branches != null;
        for (String branch : branches) {
            String id = readContentsAsString(join(CWD, branch));
            Commit cur = readObject(join(OBJS_DIR, id), Commit.class);
            while (cur != null) {
                if (cur.getMessage().equals(message)) {
                    res.add(cur.getID());
                }
                cur = getParent(cur);
            }
        }
        if (res.isEmpty()) {
            System.out.println("Found no commit with that message.");
        } else {
            for (String id : res) {
                System.out.println(id);
            }
        }
    }

    //print current status
    static void status() {
        List<String> branches = plainFilenamesIn(HEADS_DIR);
        System.out.println("=== Branches ===");
        assert branches != null;
        for (String branch : branches) {
            System.out.println(branch);
        }

        List<String> files = plainFilenamesIn(CWD);
        Map<String, String> stagingArea = readStagingArea();
        List<String> stageFile = new ArrayList<>();
        List<String> removeFile = new ArrayList<>();
        List<String> mod = new ArrayList<>();
        List<String> unTrack = new ArrayList<>();

        for (String key : stagingArea.keySet()) {
            assert files != null;
            if (!files.contains(key)){
                unTrack.add(key);
            }
            else if (stagingArea.get(key) == null) {
                removeFile.add(key);
            } else {
                stageFile.add(key);
            }
        }
        System.out.println("\n=== Staged Files ===");
        for (String name : stageFile) {
            System.out.println(name);
        }
        System.out.println("\n=== Removed Files ===");
        for (String name : removeFile) {
            System.out.println(name);
        }
        System.out.println("\n=== Modifications Not Staged For Commit ===");
        /** 在当前提交中跟踪，在工作目录中更改，但未暂存;或
         暂存以进行添加，但内容与工作目录中的内容不同;或
         暂存以进行添加，但在工作目录中删除;或
         不是暂存以供删除，而是在当前提交中跟踪并从工作目录中删除。
         * TODO:如果currentCommit中存在但files中不存在则为deleted?
         * 如果files中存在但blob后与commit中不一致则为modified?
         * junk.txt (deleted)
         * wug3.txt (modified)
         */
        System.out.println("\n=== Untracked Files ===");
        /**工作目录中但既不暂存以进行添加也未被跟踪的文件。这包括已暂存以供删除的文件，
         * stagingArea中存在但files中不存在的文件?
         */
    }

    static void checkout(String str){
        /**三种情形：
         * java gitlet.Main checkout -- [file name]
         * java gitlet.Main checkout [commit id] -- [file name]
         * java gitlet.Main checkout [branch name]
         */
    }

}
