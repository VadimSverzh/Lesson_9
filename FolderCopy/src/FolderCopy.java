import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class FolderCopy {

    private final static boolean overwrite = true;
    private final static Path source = Paths.get("C:\\Users\\User\\Desktop\\Свет_и_очаг");
    private final static Path target = Paths.get("C:\\Users\\User\\Desktop\\Свет_и_очаг_2");

    public static void main(String[] args) {
            copyFile(source, target, overwrite);
    }
        private static void copyFile(Path source, Path target, boolean overwrite) {
            try {
                Files.walkFileTree(source, new SimpleFileVisitor<>() {
                    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                        Path newDir = target.resolve(source.relativize(dir));
                        if (Files.notExists(newDir)) Files.copy(dir, newDir);
                            return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
                        CopyOption[] options = (overwrite) ? new CopyOption[]{REPLACE_EXISTING} : new CopyOption[]{};
                        Files.copy(path, target.resolve(source.relativize(path)), options);
                            return FileVisitResult.CONTINUE;
                    }
                });

            } catch (IOException e) {
                System.out.format("Unable to copy %s - %s%n", source, e.getClass().getSimpleName());
            }
        }
    }