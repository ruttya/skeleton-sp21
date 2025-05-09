package gitlet;

import org.checkerframework.checker.units.qual.C;

import java.io.IOException;

import static gitlet.Utils.message;

/**
 * Driver class for Gitlet, a subset of the Git version-control system.
 *
 * @author ruttya
 */
public class Main {

    /**
     * Usage: java gitlet.Main ARGS, where ARGS contains
     * <COMMAND> <OPERAND1> <OPERAND2> ...
     * java gitlet.Main add hello.txt
     */
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Must have at least one argument");
            System.exit(-1);
        }
        String firstArg = args[0];
        Repository repo = new Repository();
        switch (firstArg) {
            case "init":
                repo.initial();
                break;
            case "add":
                validateNumArgs("add", args, 2);
                // args[1]: .txt file name
                repo.addFile(args[1]);
                break;
            case "commit":
                if (args.length<2){
                    message("Please enter a commit message.");
                    break;
                }
                // args[1]: message
                repo.commit(args[1]);
                break;
            case "rm":
                validateNumArgs("rm", args, 2);
                // args[1]: .txt file name
                repo.rm(args[1]);
                break;
            case "log":
                repo.log();
                break;
            case "global-log":
                break;
            case "find":
                break;
            case "status":
                break;
            case "checkout":
                break;
            case "branch":
                break;
            case "rm-branch":
                break;
            case "reset":
                break;
            case "merge":
                break;

        }
    }

    /**
     * Checks the number of arguments versus the expected number,
     * throws a RuntimeException if they do not match.
     *
     * @param cmd  Name of command you are validating
     * @param args Argument array from command line
     * @param n    Number of expected arguments
     */
    public static void validateNumArgs(String cmd, String[] args, int n) {
        if (args.length != n) {
            throw new RuntimeException(
                    String.format("Invalid number of arguments for: %s.", cmd));
        }
    }


}
