import com.sun.tools.javac.Main;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import java.util.HashMap;
import java.util.Map;

public class Loader {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);
    private static final String URL = "http://lenta.ru/";
    private static final String TAG_ATTRIBUTE = "src";
    private static final String TAG = "img";
    private static final Path PATH_STRING =Paths.get("C:\\Users\\User\\Desktop\\Java\\Skillbox\\Lesson_9\\GetImages\\images");
    private static boolean OVERWRITE = false;

    public static void main(String[] args) {

        File images = new File(String.valueOf(PATH_STRING));
        images.mkdir();

        WebsiteInfo info = new WebsiteInfo(URL, TAG, TAG_ATTRIBUTE);

        try {
            Document doc = parseHtml(URL);
            Map<URI, URI> imagesLinks = getImagesLinks(doc, info);
            downloadImages(OVERWRITE, imagesLinks, PATH_STRING);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static Document parseHtml (String url) throws IOException {
        return Jsoup.connect(url).get();
    }

    private static Map<URI, URI> getImagesLinks (Document doc, WebsiteInfo info){
            Map<URI, URI> links = new HashMap<>();
            Elements imgs = doc.select(info.getTag());

            for (Element a : imgs) {
                String[] linkParts = a.attr(info.getTagAttribute()).split("/");
                String imageName = linkParts[linkParts.length - 1];

                String[] imageNameParts = imageName.split("\\.");
                String extension = imageNameParts[imageNameParts.length - 1];

                if (imageNameParts.length > 1) {
                    for (String imageExtension : ImageExtensions.getExtensions()) {
                        if (imageExtension.matches(extension)) {
                            links.put(URI.create(a.absUrl(TAG_ATTRIBUTE)), URI.create(imageName));
                        }
                    }
                }
            }
            return links;
    }

    private static void downloadImages (boolean overwrite, Map<URI, URI> imagesLinks, Path target) throws IOException {
            for (Map.Entry<URI, URI> entry : imagesLinks.entrySet()) {
                try {
                    Path path = Paths.get(target + "\\" + entry.getValue());
                    InputStream in = entry.getKey().toURL().openStream();
                        if (overwrite) {
                            Files.copy(in, path, StandardCopyOption.REPLACE_EXISTING);
                        } else Files.copy(in, path);
                        LOGGER.info(entry.getValue());
                } catch (FileNotFoundException ex) {
                        LOGGER.error("Can't find the link: {}", entry.getKey());
                } catch (FileAlreadyExistsException ex) {
                        LOGGER.error("File {} already exists!", entry.getKey());
                }
            }
        }
    }

