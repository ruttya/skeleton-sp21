package gitlet;

import java.io.File;

public class Blob {

    private String name;
    private byte[] content;

    public Blob(File file){
        content= Utils.readContents(file);
        name=Utils.sha1(content);
    }

    public String getName(){
        return name;
    }

    public byte[] getContent(){
        return content;
    }

}
