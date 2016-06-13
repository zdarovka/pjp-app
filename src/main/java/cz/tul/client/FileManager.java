package cz.tul.client;

import org.springframework.beans.factory.annotation.Value;

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
    public static FileManager get() throws IOException {
        return new FileManager();
    }

    @Value("${dataimg.path}")
    private String picsPath;

    private FileManager() throws IOException {
        Path path = Paths.get(picsPath);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }

    private Path getImagePath(String filename) {
        assert (filename != null);
        return Paths.get(picsPath).resolve(filename + ".jpg");
    }

    public void saveImageData(String filename, InputStream giftData) throws IOException {
        assert (giftData != null);
        Path target = getImagePath(filename);
        Files.copy(giftData, target, StandardCopyOption.REPLACE_EXISTING);
    }
}
