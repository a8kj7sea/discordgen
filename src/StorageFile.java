import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import lombok.Getter;

@Getter
public class StorageFile {

    private File file;
    private String path;

    public StorageFile(String path) throws IOException {

        if (path == null) {

            this.path = System.getProperty("user.dir");
        }

        this.file = new File(this.path, "links.txt");
        this.file.getParentFile().mkdirs();

        if (!file.exists()) {
            file.createNewFile();
        }
    }

     public void write(String toWrite) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.append(toWrite).append(System.lineSeparator());
        }
    }

}
