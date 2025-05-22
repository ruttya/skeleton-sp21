package gitlet;

import java.io.IOException;

import static gitlet.Repository.GITLET_DIR;

/**
 * Driver class for Gitlet, a subset of the Git version-control system.
 *
 * @author ruttya
 */
public class Main {

    /**
     * Usage: java gitlet.Main ARGS, where ARGS contains
     * <COMMAND> <OPERAND1> <OPERAND2> ...
     */
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("Please enter a command.");
            System.exit(0);
        }
        String firstArg = args[0];
        switch (firstArg) {
            case "init":
                Repository.init();
                break;
            case "add":
                checkGitlet();
                Repository.addFile(args[1]);
                break;
            case "commit":
                checkGitlet();
                Repository.commit(args[1]);
                break;
            case "rm":
                checkGitlet();
                Repository.rm(args[1]);
                break;
            case "log":
                checkGitlet();
                Repository.log();
                break;
            case "global-log":
                checkGitlet();
                Repository.globalLog();
                break;
            case "find":
                checkGitlet();
                Repository.find(args[1]);
                break;
            case "status":
                checkGitlet();
                Repository.status();
                break;
            case "checkout":
                checkGitlet();
                Repository.checkout(args[1]);
                break;
            default:
                System.out.println("No command with that name exists.");
                System.exit(0);
                // TODO: FILL THE REST IN
        }
    }

    public static void checkGitlet() {
        if (!GITLET_DIR.exists()) {
            System.out.println("Not in an initialized Gitlet directory.");
            System.exit(0);
        }
    }
}
