package gitlet;

import java.io.File;
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
    private Commit pos;
    /**
     * Creates a new Gitlet version-control system in the current directory.
     * This system will automatically start with one commit:
     * a commit that contains no files and has the commit message
     * initial commit (just like that, with no punctuation).
     * It will have a single branch: master, which initially points to this initial commit,
     * and master will be the current branch.
     * The timestamp for this initial commit will be 00:00:00 UTC, Thursday, 1 January 1970
     * in whatever format you choose for dates (this is called “The (Unix) Epoch”,
     * represented internally by the time 0.) Since the initial commit in all repositories
     * created by Gitlet will have exactly the same content, it follows that all repositories
     * will automatically share this commit (they will all have the same UID) and all commits
     * in all repositories will trace back to it.
     */
    public void initial() {
        if (!GITLET_DIR.exists()) {
            GITLET_DIR.mkdir();
        } else {
            message("A Gitlet version-control system already exists in the current directory.");
        }
        commits = new ArrayList<>();
        Commit initial = new Commit("initial commit", null);
        commits.add(initial);
        pos=initial;
    }

    public void commit(String message) {
        Commit comm=new Commit(message,pos);
        commits.add(comm);
        pos=comm;
    }
    public void addFile(String fileName){
        if (!join(GITLET_DIR,fileName).exists()){
            return;
        }
    }
}
