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

    static void init() {
        if (GITLET_DIR.exists()) {
            System.out.println("A Gitlet version-control system already exists in the current directory.");
            System.exit(0);
        }
        GITLET_DIR.mkdir();
        OBJS_DIR.mkdir();
        REFS_DIR.mkdir();
        HEADS_DIR.mkdir();
        try {
            STAGING_AREA.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            HEAD.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        writeObject(STAGING_AREA, new HashMap<>());

        //创建初始commit
        Commit commit = new Commit("initial commit", "master", new Date(0L), null, new HashMap<>());
        saveCommit(commit);

        //HEAD文件内写某branch路径:/refs/heads/master,master文件中存储commitID
        writeContents(HEAD, "ref: refs/heads/master");

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
    static boolean saveBlob(String fileName) {
        String name = getBlob(fileName);
        File blob = join(OBJS_DIR, name);
        if (blob.exists()) {
            return false;
        }
        byte[] content = readContents(join(CWD, fileName));
        Utils.writeContents(blob, content);
        return true;
    }

    static void saveCommit(Commit commit) {
        File file = join(OBJS_DIR, commit.getID());
        Utils.writeObject(file, commit);
    }

    static void add2Branch(String branch, Commit commit) {
        File file = join(HEADS_DIR, branch);
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

    static void addFile(String name) {
        File file = join(CWD, name);
        if (!file.exists()) {
            System.out.println("File does not exist.");
            System.exit(0);
        }
        Map<String, String> stagingArea = readStagingArea();
        Commit cur=getCurrentCommit();
        Map<String, String> lastStage=cur.getFiles();
        //新增到暂存区
        if (!stagingArea.containsKey(name)) {
            saveBlob(name);
            stagingArea.put(name, getBlob(name));
            // rm过的文件重新保存添加
        } else if (stagingArea.get(name)==null) {
            stagingArea.put(name, getBlob(name));
            //文件当前工作区版本与最近一次提交中的版本完全一致，且文件已在暂存区，则取消暂存
        }else if (stagingArea.get(name).equals(lastStage.get(name))){
            stagingArea.put(name,null);
        }
        saveStagingArea(stagingArea);
    }

    static void commit(String message) {
        /**
         *1.创建完整的commit对象：
         *  author：当前branch name=HEAD文件中/后的文件名
         *  设置parent：上一行文件名的文件内容
         *  files<>：读取index文件as
         *2.更新branch内容
         *3.更新HEAD（if necessary
         *4. 关于blob。如果add之后commit之前文件内容有修改的话,以add时状态为准，所以add()中存储blob
         * 5.commit完成后清空暂存区
         */
        String path = readContentsAsString(HEAD);
        String[] parts = path.split("/");
        String author = parts[parts.length - 1];
        String parentID = readContentsAsString(join(HEADS_DIR,author));

        Map<String, String> files = readStagingArea();
        Commit commit = new Commit(message, author, new Date(), parentID, files);
        saveCommit(commit);
        writeContents(join(HEADS_DIR, author), commit.getID());
        saveStagingArea(new HashMap<>());
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
        String branch = getCurrentBranch();
        String ID = readContentsAsString(join(HEADS_DIR, branch));
        return readObject(join(OBJS_DIR, ID), Commit.class);
    }
    private static String getCurrentBranch(){
        String[] parts = readContentsAsString(HEAD).split("/");
        return parts[parts.length - 1];
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

    /** checkout三种情形：
     * java gitlet.Main checkout -- [file name]
     * java gitlet.Main checkout [commit id] -- [file name]
     * java gitlet.Main checkout [branch name]
     */
    static void checkoutCommit(String id, String fileName){
        //回退工作目录某文件内容
        //从文件获取指定commit
        if (!join(OBJS_DIR,id).exists()){
            message("No commit with that id exists.");
            return;
        }
        Commit commit=readObject(join(OBJS_DIR,id),Commit.class);
        if (!commit.getFiles().containsKey(fileName)){
            message("File does not exist in that commit.");
            return;
        }
        //获取此commit中文件对应blob名
        String blob=getBlob(fileName);
        //获取blob内容覆写到工作文件，不需要暂存
        byte[] content=readContents(join(OBJS_DIR,blob));
        writeContents(join(CWD,fileName),content);
        //head不修改
    }

    public static void checkoutBranch(String branchName) {
        File branch= join(HEADS_DIR, branchName);
        if (!branch.exists()) {
            System.out.println("No such branch exists.");
            System.exit(0);
        } else if(branchName.equals(getCurrentBranch())) {
            System.out.println("No need to checkout the current branch.");
            System.exit(0);
        }

        Commit targetCommit = readObject(join(OBJS_DIR, readContentsAsString(branch)), Commit.class);
        Map<String,String> files=targetCommit.getFiles();
        Map<String,String> stagingArea=readStagingArea();
        for (String filename:files.keySet()){
            //没暂存的文件不处理
            if (stagingArea.get(filename)==null){
                System.out.println("There is an untracked file in the way; delete it, or add and commit it first.");
                continue;
            }
            writeContents(join(CWD,filename),readContents(join(OBJS_DIR,files.get(filename))));
        }
        //Any files that are tracked in the current branch but are not present in the checked-out branch are deleted.
        //暂存区有但checkout中没有的文件被删除
        for (String filename:stagingArea.keySet()){
            if (files.get(filename)==null){
                restrictedDelete(filename);
            }
        }
        saveStagingArea(new HashMap<>());
        writeContents(HEAD, "ref: refs/heads/" + branchName);
    }

    public static void checkoutFile(String fileName) {
        Commit cur=getCurrentCommit();
        checkoutCommit(cur.getID(),fileName);
    }

    public static void branch(String branchName){
        String cur=getCurrentBranch();
        File branch= join(HEADS_DIR, branchName);
        if (branch.exists()){
            error("A branch with that name already exists.");
            return;
        }
        writeContents(branch,readContents(join(HEADS_DIR,cur)));
        writeContents(HEAD,"ref: refs/heads/"+branchName);
    }

    public static void rmBranch(String branchName) {
        if (getCurrentBranch()==branchName){
            message("Cannot remove the current branch.");
            return;
        }
        File branch=join(HEADS_DIR,branchName);
        if (branch.exists()){
            message("A branch with that name does not exist.");
            return;
        }
        restrictedDelete(branch);
    }

    /**
     * 描述：
     * 1. 将工作目录的所有文件回退到指定提交的版本：
     *    - 恢复该提交中所有被跟踪的文件
     *    - 删除工作目录中存在的、但该提交未跟踪的文件
     * 2. 将当前分支的HEAD指针移动到该提交节点
     * 3. 清空暂存区
     * 4. 提交ID支持缩写形式（同checkout）
     *
     * 本质行为：带分支指针移动的增强版checkout
     */
    public static void reset(String commitID) {
        if (!join(OBJS_DIR,commitID).exists()){
            System.out.println("No commit with that id exists.");
            return;
        }
        //对于commit中有但目前未暂存的文件，提示并直接退出<-首先进行
        Commit commit=readObject(join(OBJS_DIR,commitID),Commit.class);
        Map<String, String> stagingArea=readStagingArea();
        for (String name:commit.getFiles().keySet()){
            if (!stagingArea.containsKey(name)){
                message("There is an untracked file in the way; delete it, or add and commit it first.");
                return;
            }
        }
        List<String> files=plainFilenamesIn(CWD);
        for (String file :files){
            if (commit.getFiles().keySet().contains(file)){
                String blob=commit.getFiles().get(file);
                writeContents(join(CWD,file),readContents(join(OBJS_DIR,blob)));
            }else { //commit中不存在的工作文件
                restrictedDelete(file);
            }
        }
        //移动HEAD,清空暂存区
        String branch=getCurrentBranch();
        writeContents(join(HEADS_DIR,branch),commit.getID());
        saveStagingArea(new HashMap<>());
    }

    //Merges files from the given branch into the current branch.
    public static void merge(String branchName) {
        //TODO:merge
    }
}
