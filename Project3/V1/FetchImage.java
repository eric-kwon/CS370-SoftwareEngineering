/*      
 *      Name    : Eric Kwon
 *      Project Phase 3 (FetchImage.java)
 *      Purpose : 
 *      1) Receive a URL and save the image according to its itemID to an appropriate folder
 */
 
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
 
public class FetchImage {

    static String imageLocationPath = "";

    // Set the path of the image
    static void set_imgPath(String x) {
        imageLocationPath = x + "/";
    }

    // Get the path of the image
    static String get_imgPath() {
        return imageLocationPath;
    }

    public static void saveImage(String x, String itemID, Boolean primary) {
        
        // Image will be stored according to their item ID for easier view
        String filepath = "assets/" + itemID;
        final String filename = "img";

        // Set image path
        Path imagePath = Paths.get(filepath);

        // If directory doesn't exist, create the path
        if (!Files.exists(imagePath)) {

            // Try block to create the path
            try {
                Files.createDirectories(imagePath);
            }

            // Exception Handler
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Empty stream objects
        InputStream incoming = null;
        OutputStream outgoing = null;

        // Try block to fetch the image from provided URL and save to appropriate directory
        try {

            // URL object and image number for additional images that needs to be processed
            URL url = new URL(x);
            int imageNum = 1;

            // Check if there is any image already and increment image number if so
            String imageLoc = filepath + "/" + filename + Integer.toString(imageNum) + ".jpg";
            imagePath = Paths.get(imageLoc);

            // Check until the highest number
            while (Files.exists(imagePath) && !primary) {
                imageLoc = filepath + "/" + filename + Integer.toString(++imageNum) + ".jpg";
                imagePath = Paths.get(imageLoc);
            }

            // Incoming and outgoing streams
            incoming = url.openStream();
            outgoing = new FileOutputStream(filepath + "/" + filename + Integer.toString(imageNum) + ".jpg");
            set_imgPath(filepath);

            // Declare the byte to copy by bytes
            byte[] b = new byte[2048];
            int length;

            // As long as incoming byte isn't empty, copy and write
            while ((length = incoming.read(b)) != -1) {
                outgoing.write(b, 0, length);
            }
        }

        // Exception Handler
        catch (IOException e) {
            e.printStackTrace();
        }

        // Close out the stream once the operations are done
        finally {
            try {
                if (incoming != null)
                    incoming.close();
                if (outgoing != null)
                    outgoing.close();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}