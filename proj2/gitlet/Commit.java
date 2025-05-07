package gitlet;


import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.Formatter;
import java.util.Dictionary;

import static gitlet.Utils.sha1;

/**
 * Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 * @author ruttya
 */
public class Commit implements Serializable {
    /**
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /**
     * The message of this Commit.
     */
    private String message;
    private String author;
    private String date;
    private Dictionary<File, String> files; //<file,version> version=sha1-hash
    private String parentID; //上一个commitID，只保存id(40 length String(来自Util.sha1))

    //将以上所有信息hash后作为commitID
    public Commit(String author, String message, Dictionary<File, String> files, String date, String parentID) {
        this.author = author;
        this.message = message;
        this.files = files;
        this.date = date;
        this.parentID = parentID;
    }

    public String getCommitID() {
        return sha1(author, date, message, files, parentID);
    }

    //TODO: 如果不单独保存ID则需要一个根据ID查找某commit的方法
    public Commit createCommit() {
        Commit res = new Commit("","",null,"",null);
        res.date = getNow();
        res.parentID = getCommitID();
        return res;
    }
    private String getNow(){
        // 获取当前时间
        Date now = new Date();
        Formatter formatter = new Formatter();
        formatter.format("%ta %tb %td %tT %tY %tz",
                now, now, now, now, now, now);
        String formattedDate = formatter.toString();
        formatter.close();
        return formattedDate;
    }
    public void save() {

    }
    /* TODO: fill in the rest of this class. */
}
