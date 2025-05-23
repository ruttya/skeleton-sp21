package gitlet;

import java.io.Serializable;
import java.util.*;

/**
 * Represents a gitlet commit object.
 * does at a high level.
 *
 * @author ruttya
 */
public class Commit implements Serializable {

    private String message;
    private String author;
    private String date;
    private String parent;
    private Map<String, String> files; //<content file, blob name>

    public Commit(String message, String author, Date date, String parent, Map<String, String> files) {
        this.message = message;
        this.author = author;
        this.date = createDate(date);
        this.parent = parent;
        this.files = files;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public void setFiles(Map<String, String> files) {
        this.files = files;
    }
    public void setParent(String id) {
        this.parent = id;
    }

    public String getID() {
        return Utils.sha1(this.author+this.date+this.message+this.parent+this.files.toString());
    }
    public Map<String, String> getFiles(){
        return files;
    }
    public String getDate(){
        return this.date;
    }
    public String getMessage(){
        return this.message;
    }
    public String getParent(){
        return this.parent;
    }
    public String getAuthor() {
        return this.author;
    }

    private String createDate(Date d) {
        // 创建Formatter对象
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb, Locale.US);
        // 格式化日期
        formatter.format("%ta %tb %td %tT %tY %tz",
                d, d, d, d, d, d);
        // 关闭Formatter
        formatter.close();
        return sb.toString();
    }

    public void printCommit(){
        System.out.println("===");
        System.out.println("commit "+getID());
        System.out.println("Date: "+date);
        System.out.println(message+"\n");
    }


}
