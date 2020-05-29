import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class FolderCopy {

    private final static boolean OVERWRITE = true;
    private final static Path SOURCE = Paths.get("fff\\");
    private final static Path TARGET = Paths.get("fff\\g");

    public static void main(String[] args) {
            copyFile(SOURCE, TARGET, OVERWRITE);
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