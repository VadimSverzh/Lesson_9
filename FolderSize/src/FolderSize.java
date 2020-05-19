import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class FolderSize {
    private static long fileSize = 0;

    public static void main(String[] args) {

        Path myFolder = Paths.get("C:\\Users\\User\\Desktop\\Java");

        long folderSize = calculateFolderSize(myFolder);
        String readableSize = getHumanReadableSize(FileSize.BYTE, folderSize);
        printHumanReadableSize(myFolder, readableSize);

        printHumanReadableSize(myFolder, getHumanReadableSize(FileSize.KILOBYTE, folderSize));
        printHumanReadableSize(myFolder, getHumanReadableSize(FileSize.MEGABYTE, folderSize));
        printHumanReadableSize(myFolder, getHumanReadableSize(FileSize.GIGABYTE, folderSize));

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

    private static String getHumanReadableSize(FileSize sizeType, double fileSize) {
        String size = "";
        double sizeForString = 0.0;

        switch (sizeType) {
            case BYTE:
                sizeForString = fileSize;
                size = "байт";
                break;
            case KILOBYTE:
                sizeForString = fileSize / 1024;
                size = "килобайт";
                break;
            case MEGABYTE:
                sizeForString = fileSize / 1024 / 1024;
                size = "мегабайт";
                break;
            case GIGABYTE:
                sizeForString = fileSize / 1024 / 1024 / 1024;
                size = "гигабайт";
                break;
        }

        if (sizeForString < 1) {
            return String.format("%.2g " + size, sizeForString);
        } else if (sizeForString > 1 && fileSize < 10) {
            return String.format("%.2f " + size, sizeForString);
        } else {
            return String.format("%.0f " + size, sizeForString);
        }
    }

    private static void printHumanReadableSize (Path folder, String size) {
        System.out.println("Размер папки " + folder + " составил " + size);
    }

}



