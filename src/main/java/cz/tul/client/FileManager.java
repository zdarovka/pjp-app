package cz.tul.client;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Created by Petr on 13. 6. 2016.
 */
public class FileManager {

    private String picsPath;
    public FileManager(String picsPath) throws IOException {
        this.picsPath = picsPath;

        Path path = Paths.get(this.picsPath);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }

    private Path getImagePath(String filename) {
        assert (filename != null);
        return Paths.get(this.picsPath).resolve(filename + ".jpg");
    }

    public void saveImageData(String filename, InputStream giftData) throws IOException {
        assert (giftData != null);
        Path target = getImagePath(filename);
        Files.copy(giftData, target, StandardCopyOption.REPLACE_EXISTING);
    }
}
