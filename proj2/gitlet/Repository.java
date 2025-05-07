package gitlet;

import java.io.File;
import java.io.IOException;

import static gitlet.Utils.*;

/** Represents a gitlet repository.
 *
 *  does at a high level.
 *
 *  @author ruttya
 */
public class Repository {
    /**
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */
    private Commit[] commits;

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");

    /* TODO: fill in the rest of this class. */
    public static void initial(){
        if (!GITLET_DIR.exists()){
            GITLET_DIR.mkdir();
        }else {
            message("A Gitlet version-control system already exists in the current directory.");
        }
    }
    //TODO: move file to somewhere in /.gilet
    public static void addFile(String fileName){
        File f=join(GITLET_DIR,fileName);
        try {
            f.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
