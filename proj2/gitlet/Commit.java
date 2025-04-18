package gitlet;

// TODO: any imports you need here

import java.io.File;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date; // TODO: You'll likely use this in this class
import java.util.Dictionary;

import static gitlet.Utils.sha1;

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Commit implements Serializable {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /** The message of this Commit. */
    private String message;
    private String author;
    private String date;
    private Dictionary<File,String> files; //<file,version> version=sha1-hash
    private String parentID; //上一个commitID，只保存id(40 length String(来自Util.sha1))
    //将以上所有信息hash后作为commitID, initial commit's parent is null
    public Commit(String msg){
        message=msg;
        date= Instant.now().toString();
        parentID=null;
    }
    public String getCommitID() {
        return sha1(message, author, date, files, parentID);
    }
    //TODO: 如果不单独保存ID则需要一个根据ID查找某commit的方法
    public Commit createCommit(){
        Commit res=new Commit("");
        res.parentID=getCommitID();
        return res;
    }
    public void save(){

    }
    /* TODO: fill in the rest of this class. */
}
