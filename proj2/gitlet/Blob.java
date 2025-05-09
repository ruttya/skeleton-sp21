package gitlet;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import static gitlet.Repository.GITLET_DIR;
import static gitlet.Utils.*;

public class Blob implements Serializable {
    static final File BLOB_FOLDER = join(GITLET_DIR, "objects","blob");

    private final byte[] content;   // 仅存储内容
    private final String id;        // 仅基于内容的哈希

    public Blob(byte[] content) {
        this.content = content;
        id = sha1(content);
    }

    public Object getContent(){
        return this.content;
    }

    public String getId(){
        return this.id;
    }

    /**
     * save blob file
     * @return if a new file saved
     */
    public boolean saveBlob() {
        // blob文件名=hash(content文件内容);blob文件内容=content文件内容
        File blob=join(BLOB_FOLDER,getId());
        if (blob.exists()){
            return false;
        }else {
            try {
                blob.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            writeContents(blob, content);
            return true;
        }
    }
}
