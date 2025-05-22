package gitlet;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

import static gitlet.Utils.*;

/** Represents a gitlet repository.
 * 因为Main.java中存在repo对象创建位置问题，所以Repo所有方法为静态方法
 * repo所有信息储存在文件中，靠文件读写处理所有操作
 *  @author ruttya
 */
public class Repository {

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    public static final File OBJS_DIR = join(GITLET_DIR, "objects");
    public static final File REFS_DIR = join(GITLET_DIR, "refs");
    public static final File HEADS_DIR = join(REFS_DIR, "heads");
    public static final File STAGING_AREA = join(GITLET_DIR, "index");
    public static final File HEAD = join(GITLET_DIR, "HEAD");

    static void init() throws IOException {
        if (GITLET_DIR.exists()){
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
        Commit commit=new Commit("initial commit");
        commit.setDate(new Date(0L));
        saveCommit(commit);

        //HEAD文件内写某branch路径:/refs/heads/master,master文件中存储commitID
        writeContents(HEAD, "ref: refs/heads/master\n");

        //此处仅更新对应branch当前所在commit，chain问题未考虑
        add2Branch("master",commit);
    }

    static String getBlob(String file){
        byte[] content=Utils.readContents(join(CWD,file));
        String name=Utils.sha1(content);
        return name;
    }
    // 保存某个文件作为blob，
    //照此写法Blob.java可以删掉了,返回是否创建了新的blob
    static boolean saveBlob(String fileName) throws IOException {
        String name=getBlob(fileName);
        File blob=join(OBJS_DIR,name);
        if (blob.exists()){
            return false;
        }
        blob.createNewFile();
        byte[] content=readContents(join(CWD,fileName));
        Utils.writeContents(blob,content);
        return true;
    }

    static void saveCommit(Commit commit) throws IOException {
        File file=join(OBJS_DIR,commit.getID());
        if (!file.exists()){
            file.createNewFile();
        }
        Utils.writeObject(file,commit);
    }

    static void add2Branch(String branch,Commit commit) throws IOException {
        File file=join(HEADS_DIR,branch);
        if (!file.exists()){
            file.createNewFile();
        }
        Utils.writeContents(file,commit.getID());
    }

    // 读取暂存区
    //@SuppressWarnings("unchecked")
    private static Map<String, String> readStagingArea() {
        if (!STAGING_AREA.exists()) {
            return new HashMap<>();
        }
        return readObject(STAGING_AREA, HashMap.class);
    }

    static void addFile(String name) throws IOException {
        File file=join(CWD,name);
        if (!file.exists()){
            System.out.println("File does not exist.");
            return;
        }
        Map<String,String> stagingArea=readStagingArea();
        //新增到暂存区
        if (stagingArea.get(name)==null){
            saveBlob(name);
            stagingArea.put(name,getBlob(name));
            // 无改动的文件移出暂存区
        }else if (stagingArea.get(name).equals(getBlob(name))){
            stagingArea.remove(name);
        }
        //TODO:若该文件当时正处于待删除状态（通过gitlet rm标记），此命令将撤销其待删除标记。
        writeObject(STAGING_AREA, (Serializable) stagingArea);
    }

    static void commit(String message){
        /**
         *1.创建完整的commit对象：
         *  author：当前branch name=HEAD文件中/后的文件名
         *  设置parent：上一行文件名的文件内容
         *  files<>：读取index文件as
         *2.更新branch内容
         *3.更新HEAD（if necessary
         *4. 关于blob。如果add之后commit之前文件内容有修改的话？目前觉得应该以commit时为准
         */
        String path=readContentsAsString(HEAD);
        String parentID=readContentsAsString(join(CWD,path));
        String[] parts = path.split("/");
        String author=parts[parts.length - 1];
        HashMap<String,String> files=readObject(STAGING_AREA,HashMap.class);

        Commit commit=new Commit(message);
        commit.setParent(parentID);
        commit.setAuthor(author);
        commit.setFiles(files);
    }

}
