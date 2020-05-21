import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class FolderSize {
    private static long fileSize = 0;

    public static void main(String[] args) {

        Path myFolder = Paths.get("C:\\Users\\User\\Desktop\\Свет_и_очаг");

        long folderSize = calculateFolderSize(myFolder);
        String readableSize = getHumanReadableSize(folderSize);
        printHumanReadableSize(myFolder, readableSize);

    }

    private static long calculateFolderSize(Path folder) {
        try {
            Files.walkFileTree(folder, new SimpleFileVisitor<>() {
                public FileVisitResult visitFile(Path file, BasicFileAttributes attr) {
                    fileSize = fileSize + attr.size();
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            System.out.println("IOException was thrown!");
        } catch (SecurityException e) {
            System.out.println("The security manager denies access to the starting file " + folder + "!");
        }
        return fileSize;
    }

    private static String getHumanReadableSize(double fileSize) {
        String[] size = {"байт", "килобайт", "мегабайт", "гигабайт"};
        final int UNIT = 1024;

        int i = (int) (Math.log(fileSize) / Math.log(UNIT));
        return String.format("%.2f %s", fileSize / (Math.pow(UNIT, i)), size[i]);
    }

    private static void printHumanReadableSize (Path folder, String size) {
        System.out.println("Размер папки " + folder + " составил " + size);
    }
}



