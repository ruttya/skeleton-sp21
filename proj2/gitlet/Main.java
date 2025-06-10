package gitlet;

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
    public static void main(String[] args) {
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
                if (args.length==1){
                    System.out.println("Please enter a commit message.");
                    System.exit(0);
                }
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
                int l = args.length;
                if (l == 2) {
                    Repository.checkoutBranch(args[1]);
                } else if (l == 3 && args[1].equals("--")) {
                    Repository.checkoutFile(args[2]);
                } else if (l == 4 && args[2].equals("--")) {
                    Repository.checkoutCommit(args[1], args[3]);
                } else {
                    System.out.println("Invalid parameter. ");
                    System.exit(0);
                }
                break;
            case "branch":
                Repository.branch(args[1]);
                break;
            case "rm-branch":
                Repository.rmBranch(args[1]);
                break;
            case "reset":
                Repository.reset(args[1]);
                break;
            case "merge":
                Repository.merge(args[1]);
                break;
            default:
                System.out.println("No command with that name exists.");
                System.exit(0);
        }
    }

    public static void checkGitlet() {
        if (!GITLET_DIR.exists()) {
            System.out.println("Not in an initialized Gitlet directory.");
            System.exit(0);
        }
    }
}
