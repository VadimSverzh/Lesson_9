import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class FolderSize {
    private static long fileSize = 0;
    private static double fileSizeBytes;
    private static double fileSizeKb;
    private static double fileSizeMb;
    private static double fileSizeGb;

    private final static Path myFolder = Paths.get("C:\\Users\\User\\Desktop\\Java");

    public static void main(String[] args) {
        try {
            Files.walkFileTree(myFolder, new SimpleFileVisitor<>() {
                public FileVisitResult visitFile(Path file, BasicFileAttributes attr) {
                    fileSize = fileSize + attr.size();
                    return FileVisitResult.CONTINUE;
                }
            });
            fileSizeBytes = (double) fileSize;
            fileSizeKb = fileSizeBytes / 1024;
            fileSizeMb = fileSizeKb / 1024;
            fileSizeGb = fileSizeMb / 1024;

            printSize(fileSizeBytes);
            printSize(fileSizeKb);
            printSize(fileSizeMb);
            printSize(fileSizeGb);
        }
        catch (IOException e) {
            System.out.println("IOException was thrown!");
        }
        catch (SecurityException e){
            System.out.println("The security manager denies access to the starting file " + myFolder+ "!");
        }
    }

    private static void printSize(double fileSize) {
        String size = "";

        if (fileSize == fileSizeBytes) size = "байт";
        else if (fileSize == fileSizeKb) size = "килобайт";
        else if (fileSize == fileSizeMb) size = "мегабайт";
        else size = "гигабайт";

        if (fileSize < 1) System.out.printf("Размер папки " + myFolder + " составил %.2g " + size + "\n", fileSize);
        else if (fileSize > 1 && fileSize < 10) {
               System.out.printf("Размер папки " + myFolder + " составил %.2f " + size + "\n", fileSize);
        }
        else  {System.out.printf("Размер папки " + myFolder + " составил %.0f " + size + "\n", fileSize);}
    }
}
