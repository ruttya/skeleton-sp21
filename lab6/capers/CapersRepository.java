package capers;

import java.io.File;
import java.io.IOException;

import static capers.Dog.DOG_FOLDER;
import static capers.Dog.fromFile;
import static capers.Utils.*;

/**
 * A repository for Capers
 *
 * @author ruttya
 * The structure of a Capers Repository is as follows:
 * <p>
 * .capers/ -- top level folder for all persistent data in your lab12 folder
 * - dogs/ -- folder containing all of the persistent data for dogs
 * - story -- file containing the current story
 */
public class CapersRepository {
    /**
     * Current Working Directory.
     */
    static final File CWD = new File(System.getProperty("user.dir"));

    /**
     * Main metadata folder.
     */
    static final File CAPERS_FOLDER = join(CWD, ".capers").toPath().toFile();

    /**
     * Does required filesystem operations to allow for persistence.
     * (creates any necessary folders or files)
     * Remember: recommended structure (you do not have to follow):
     * <p>
     * .capers/ -- top level folder for all persistent data in your lab12 folder
     * - dogs/ -- folder containing all of the persistent data for dogs
     * - story -- file containing the current story
     */

    public static void setupPersistence() {
        if (!CAPERS_FOLDER.exists()) {
            CAPERS_FOLDER.mkdir();
        }
        if (!DOG_FOLDER.exists()) {
            DOG_FOLDER.mkdir();
        }
        // System.out.println("CAPERS_FOLDER:"+CAPERS_FOLDER.toString());
        // System.out.println("DOG_FOLDER:"+DOG_FOLDER.toString());
    }

    /**
     * Appends the first non-command argument in args
     * to a file called `story` in the .capers directory.
     *
     * @param text String of the text to be appended to the story
     */
    public static void writeStory(String text) {
        File story=join(CAPERS_FOLDER,"story").toPath().toFile();
        try {
            story.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String string=readContentsAsString(story);
        // writeContents() is an overwrite method,
        // so create a string variable to make append
        writeContents(story,string,text,"\n");
        System.out.println(readContentsAsString(story));
    }

    /**
     * Creates and persistently saves a dog using the first
     * three non-command arguments of args (name, breed, age).
     * Also prints out the dog's information using toString().
     */
    public static void makeDog(String name, String breed, int age) {
        Dog dog=new Dog(name,breed,age);
        dog.saveDog();
        System.out.println(dog);
    }

    /**
     * Advances a dog's age persistently and prints out a celebratory message.
     * Also prints out the dog's information using toString().
     * Chooses dog to advance based on the first non-command argument of args.
     *
     * @param name String name of the Dog whose birthday we're celebrating.
     */
    public static void celebrateBirthday(String name) {
        Dog dog=fromFile(name);
        if (dog!=null){
            dog.haveBirthday();
        }
    }
}
