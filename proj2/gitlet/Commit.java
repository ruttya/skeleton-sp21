package gitlet;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.Formatter;
import java.util.Dictionary;

import static gitlet.Repository.GITLET_DIR;
import static gitlet.Utils.*;

/**
 * Represents a gitlet commit object.
 * as Dog.java in lab6
 * does at a high level.
 *
 * @author ruttya
 */
public class Commit implements Serializable {

    static final File COMMIT_FOLDER = join(GITLET_DIR, ".commits").toPath().toFile();

    private String message;
    private String author;
    private String date;
    private Dictionary<File, String> files; //<file,version> version=sha1-hash
    private Commit parent; //上一个commit

    //将以上所有信息hash后作为commitID
    public Commit(String message, Commit parent) {
        this.message = message;
        this.parent = parent;
        this.date = createDate();
    }

    public String getCommitID() {
        // commitID(40 length String(来自Util.sha1))
        return sha1(author, date, message, files, parent);
    }

    public Commit getParent() {
        return this.parent;
    }

    //TODO: 如果不单独保存ID则需要一个根据ID查找某commit的方法
    private String createDate() {
        // 获取当前时间
        Date now = new Date();
        Formatter formatter = new Formatter();
        formatter.format("%ta %tb %td %tT %tY %tz",
                now, now, now, now, now, now);
        String formattedDate = formatter.toString();
        formatter.close();
        return formattedDate;
    }

    // 保存，以id为文件名，文件内容是commit内容（commit作为一个obj）
    public void save() {
        File commi = join(COMMIT_FOLDER, getCommitID()).toPath().toFile();
        try {
            commi.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // write...() is an overwrite method,
        // so create a string variable if make append
        writeObject(commi, this);
    }
    /* TODO: fill in the rest of this class. */

}
