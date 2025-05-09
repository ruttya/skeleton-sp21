package gitlet;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.Formatter;
import java.util.Map;

import static gitlet.Blob.BLOB_FOLDER;
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

    static final File COMMIT_FOLDER = join(GITLET_DIR, "objects","commit");

    private String message;
    private String author;
    private String date;
    private Map<File, String> files; //<file,version> version=sha1-hash
    private String parent; //上一个commitID

    //将以上所有信息hash后作为commitID
    public Commit(String message, String parent) {
        this.message = message;
        this.parent = parent;
        this.date = createDate();
    }

    public String getCommitID() {
        // commitID(40 length String(来自Util.sha1))
        return sha1(author, date, message, files, parent);
    }

    public Commit getParent() {
        return getCommit(this.parent);
    }

    public String getParentID(){
        return this.parent;
    }

    public String getMessage(){
        return this.message;
    }

    public String getDate(){
        return this.date;
    }

    public void setDate(String date){
        this.date=date;
    }

    private String createDate() {
        // 获取当前时间戳
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

    /**
     * 在当前commit基础上创建新的commit并返回
     * @return
     * @throws IOException
     */
    public Commit createCommit(String message) throws IOException {
        boolean changed=false;
        Commit res=new Commit(message,this.getCommitID());
        // 遍历files中的key(文件名)，将文件内容hash后作为blob的文件名，
        // 如果blob文件不存在则创建
        // 然后将{content文件名:blob文件名}添加到新commit的files<,>中
        // 如果该blob已存在则直接添加到新commit
        for (File file:files.keySet()){
            Blob b=new Blob(readContents(file));
            File blob=join(BLOB_FOLDER,sha1(readContentsAsString(file)));
            if (!blob.exists()){
                b.saveBlob();
                changed=true;
            }
            res.files.put(file,sha1(readContentsAsString(file)));
        }
        if (changed){
            return res;
        }
        else {
            message("No changes added to the commit.");
            return null;
        }
    }

    public void addFile(String filename){
        File file=join(GITLET_DIR,filename).toPath().toFile();
        files.put(file,"");
    }

    public void rmFile(String filename){
        File file=join(GITLET_DIR,filename).toPath().toFile();
        if (files.containsKey(file)){
            files.remove(file);
        }
        else {
            message("No reason to remove the file.");
        }
    }

    public void printCommit(){
        System.out.println("===\ncommit "+getCommitID()+"\nDate: "+date+"\n"+message+"\n\n");
    }

    public static Commit getCommit(String id){
        File f=join(COMMIT_FOLDER,id).toPath().toFile();
        Commit item=readObject(f, Commit.class);
        return item;
    }
}
