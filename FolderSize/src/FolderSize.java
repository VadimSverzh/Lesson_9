import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class FolderSize {
    private static long fileSizeBytes = 0;
    private final static Path myFolder = Paths.get("C:\\Users\\User\\Desktop\\Замечания_по сайту");
    private static Path currentFile;

    public static void main(String[] args) {
        try {
            Files.walkFileTree(myFolder, new SimpleFileVisitor<>() {
                public FileVisitResult visitFile(Path file, BasicFileAttributes attr) {
                    currentFile = file;
                    fileSizeBytes = fileSizeBytes + attr.size();
                    return FileVisitResult.CONTINUE;
                }
            });
            printSizeBytes();
            printSizeKilobytes();
            printSizeMegabytes();
            printSizeGigabytes();
        }
        catch (IOException e) {
            System.out.println("Файл " + myFolder + " не найден");
        }
        catch (SecurityException e){
            System.out.println("Файл " + currentFile + " скрыт. Невозможно посчитать размер всех файлов");
        }
    }

    private static void printSizeBytes() {
            System.out.printf("Размер папки " + myFolder + " составил %,d байт%n", fileSizeBytes);
    }

    private static void printSizeKilobytes() {
            if (fileSizeBytes / 1024 != 0) {
                long fileSizeKb = fileSizeBytes / 1024;
                System.out.printf("Размер папки " + myFolder + " составил %,d килобайт%n", fileSizeKb);
            } else {
                double fileSizeKb = (double) fileSizeBytes / 1024;
                System.out.printf("Размер папки " + myFolder + " составил %.0g килобайт%n", fileSizeKb);
            }
    }

    private static void printSizeMegabytes() {
            if (fileSizeBytes / 1024 / 1024 != 0) {
            long fileSizeMb = fileSizeBytes / 1024 / 1024;
            System.out.printf("Размер папки " + myFolder + " составил %,d мегабайт%n", fileSizeMb);
            } else {
            double fileSizeMb = (double) fileSizeBytes / 1024 / 1024;
            System.out.printf("Размер папки " + myFolder + " составил %.0g мегабайт%n", fileSizeMb);
            }
    }

    private static void printSizeGigabytes(){
            if (fileSizeBytes / 1024 / 1024 / 1024 != 0) {
            long fileSizeGb = fileSizeBytes / 1024 / 1024 / 1024;
            System.out.printf("Размер папки " + myFolder + " составил %,d гигабайт%n", fileSizeGb);
            }
            else {
            double fileSizeGb = (double) fileSizeBytes / 1024 / 1024 / 1024;
            System.out.printf("Размер папки " + myFolder + " составил %.0g гигабайт%n", fileSizeGb);
            }
    }
}
