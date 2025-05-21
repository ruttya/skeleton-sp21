package gitlet;

import java.io.File;
import java.util.*;

/** Represents a gitlet commit object.
 *  does at a high level.
 *
 *  @author ruttya
 */
public class Commit {

    private String message;
    private String author;
    private String date;
    private String parent;
    private Map<File,String> files;

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

    private void setParent(String id){
        this.parent=id;
    }

    public String getID(){
        return Utils.sha1(this.author,this.date,this.message,this.parent);
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
