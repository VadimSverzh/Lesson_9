import com.sun.source.tree.ContinueTree;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.*;

public class Loader {
    private static final String URL = "http://lenta.ru/";
    private static final String TAG_ATTRIBUTE = "src";
    private static final String TAG = "img";
    private static final String PATH_STRING = "C:\\Users\\User\\Desktop\\Java\\Skillbox\\Lesson_9\\GetImages\\images";
    private static boolean OVERWRITE = true;

    public static void main(String[] args) {

        StringBuilder sb = new StringBuilder("Скаченные файлы:\n");
        File images = new File(PATH_STRING);
        images.mkdir();

        try {
            Document doc = Jsoup.connect(URL).get();
            Elements imgs = doc.select(TAG);

            for (Element a : imgs) {
                try {
                    String[] linkParts = a.attr(TAG_ATTRIBUTE).split("/");
                    String imageName = linkParts[linkParts.length - 1];

                    String[] imageNameParts = imageName.split("\\.");
                    String extension = imageNameParts[imageNameParts.length - 1];

                    if (imageNameParts.length > 1) {
                        for (String imageExtension : ImageExtensions.getExtensions()) {
                            if (imageExtension.matches(extension)) {
                                {
                                    Path path = Paths.get(PATH_STRING + "\\" + imageName);
                                    URI u = URI.create(a.absUrl(TAG_ATTRIBUTE));
                                    InputStream in = u.toURL().openStream();
                                    if (OVERWRITE) {
                                        Files.copy(in, path, StandardCopyOption.REPLACE_EXISTING);
                                    } else Files.copy(in, path);
                                    sb.append(imageName).append("\n");
                                }
                            }
                        }
                    }
                }
                catch(FileNotFoundException ex) {
                        System.out.println("Can't find the link " + a.absUrl(TAG_ATTRIBUTE));
                }
            }
        }
        catch(IOException ex) {
                ex.printStackTrace();
            }
            System.out.println(sb.toString());
        }
    }
