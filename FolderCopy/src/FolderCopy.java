import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class FolderCopy {
    public static void main(String[] args) {
        String pathCopyFrom = "C:\\Users\\User\\Desktop\\Свет_и_очаг";
        String pathCopyTo = "C:\\Users\\User\\Desktop\\Свет_и_очаг_2\\";

        File folder = new File(pathCopyTo);
        folder.mkdir();

        Path folderCopyFrom = Paths.get(pathCopyFrom);

        Path folderCopyTo =Paths.get(pathCopyTo);

        try {
        Files.walkFileTree(folderCopyFrom, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
                FileInputStream is = new FileInputStream(path.toString());
                File file = new File(pathCopyTo + path.getFileName().toString());
                Files.copy(is, file.toPath());
                return FileVisitResult.CONTINUE;
            }
        });
    }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
