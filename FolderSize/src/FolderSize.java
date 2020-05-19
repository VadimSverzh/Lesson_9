import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class FolderSize {
    private static long fileSize = 0;

    public static void main(String[] args) {

        Path myFolder = Paths.get("C:\\Users\\User\\Desktop\\Полиграфия_и_макеты");

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
        double sizeForString = fileSize;
        int i;

        for (i = 0; i < 4; i++) {
            if (sizeForString < 1000 || i == 3){
                break;
            }
            else {
                sizeForString = sizeForString / 1024;
            }
        }

        if (sizeForString < 1) {
            return String.format("%.2g " + size[i], sizeForString);
        }
        else if (sizeForString > 1 && sizeForString < 10) {
            return String.format("%.2f " + size[i], sizeForString);
        }
        else {
            return String.format("%.0f " + size[i], sizeForString);
        }
    }

    private static void printHumanReadableSize (Path folder, String size) {
        System.out.println("Размер папки " + folder + " составил " + size);
    }
}



