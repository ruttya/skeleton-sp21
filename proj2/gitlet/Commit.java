package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.*;

/** Represents a gitlet commit object.
 *  does at a high level.
 *
 *  @author ruttya
 */
public class Commit implements Serializable {

    private String message;
    private String author;
    private String date;
    private String parent;
    private Map<String,String> files; //<content file, blob name>

    public Commit(String message, String author, String date, String parent, Map<String, String> files) {
        this.message = message;
        this.author = author;
        this.date = date;
        this.parent = parent;
        this.files = files;
    }

    public Commit(String message){
        this.message=message;
        this.date=createDate();
        this.parent=null;
        this.files=new HashMap<>();
    }

    public Commit createCommit(String message){
        Commit res=new Commit(message);
        res.setParent(this.getID());
        return res;
    }

    public void setParent(String id){
        this.parent=id;
    }
    public void setDate(Date date){
        // 创建Formatter对象
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb, Locale.US);
        // 格式化日期
        formatter.format("%ta %tb %td %tT %tY %tz",
                date, date, date, date, date, date);
        // 关闭Formatter
        formatter.close();
        this.date=sb.toString();
    }

    public String getID(){
        return Utils.sha1(this.author,this.date,this.message,this.parent);
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    public void setFiles(Map<String,String> files){
        this.files=files;
    }

    private String createDate(){
        // 获取当前时间
        Date now = new Date();
        // 创建Formatter对象
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb, Locale.US);
        // 格式化日期
        formatter.format("%ta %tb %td %tT %tY %tz",
                now, now, now, now, now, now);
        // 关闭Formatter
        formatter.close();
        return sb.toString();
    }

}
