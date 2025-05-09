package gitlet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static gitlet.Utils.*;

/**
 * Represents a gitlet repository.
 *
 * @author ruttya
 */
public class Repository {
    public static final File CWD = new File(System.getProperty("user.dir"));
    public static final File GITLET_DIR = join(CWD, ".gitlet");

    private List<Commit> commits;
    private Commit cur;
    /**
     * Creates a new Gitlet version-control system in the current directory.
     * This system will automatically start with one commit:
     * a commit that contains no files and has the commit message
     * initial commit (just like that, with no punctuation).
     *
     * It will have a single branch: master, which initially points to this initial commit,
     * and master will be the current branch.
     *
     * The timestamp for this initial commit will be 00:00:00 UTC, Thursday, 1 January 1970
     * in whatever format you choose for dates (this is called “The (Unix) Epoch”,
     * represented internally by the time 0.)
     *
     * Since the initial commit in all repositories
     * created by Gitlet will have exactly the same content, it follows that all repositories
     * will automatically share this commit (they will all have the same UID) and all commits
     * in all repositories will trace back to it.
     */
    public void initial() {
        if (!GITLET_DIR.exists()) {
            GITLET_DIR.mkdir();
        } else {
            message("A Gitlet version-control system already exists in the current directory.");
            return;
        }
        commits = new ArrayList<>();
        Commit initial = new Commit("initial commit", null);
        initial.setDate("00:00:00 UTC, Thursday, 1 January 1970");
        commits.add(initial);
        initial.save();
        cur = initial;
    }

    /** check every content file and copy file if was modified,
     * then renew file version number.
     * inherit version number if file wasn't modified.
     * move cur pointer of master branch.
     *
     * @param message commit message given by 'commit xxx' command
     */
    public void commit(String message) throws IOException {
        Commit comm=cur.createCommit(message);
        commits.add(comm);
        comm.save();
        cur=comm;
    }

    /**
     * `add xx.txt` command in Main,
     * @param fileName file like `xx.txt` added to repo's file list
     */
    public void addFile(String fileName){
        if (!join(GITLET_DIR,fileName).exists()){
            message("File does not exist.");
            return;
        }
        else {
            cur.addFile(fileName);
        }
    }

    public void rm(String fileName){
        // 查找文件是否在commit的files中，而不是判断文件是否在磁盘中。
        cur.rmFile(fileName);
    }

    public void log(){
        //打印所有commit信息
        Commit item=cur;
        item.printCommit();
        while (item.getParent()!=null){
            item.getParent().printCommit();
            item=item.getParent();
        }
    }
}
