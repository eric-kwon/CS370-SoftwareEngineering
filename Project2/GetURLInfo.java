//      Name: Eric Kwon
//      Project Phase 2
//      Objectives:
//      1) Obtain URL information from given URL
//      2) Input from & output to a text file
//      3) Parse command line flag to designate file sources
//
//      Code Snippet from Java in a Nutshell by David Flanagan

import java.net.*;
import java.io.*;
import java.util.*;

public class GetURLInfo {

    // Reading queue where the String of URL will be added to
    static Queue<String> readQueue = new LinkedList<String>();

    // FileReader and BufferedReader which will the input file line by line
    public static void readFile (String x) {
        final String filename = x;
        FileReader fr = null;
        BufferedReader br = null;

        try {
            fr = new FileReader(filename);
            br = new BufferedReader(fr);

            String line;

            // Once each line isn't null, add the corresponding line to queue
            while ((line = br.readLine()) != null) {
                readQueue.add(line);
            }
        }

        // Exception Handler
        catch (IOException e) {
            e.printStackTrace();
        }

        // Close readers once operation is done
        finally {
            try {
                if (br == null)
                    br.close();
                if (fr == null)
                    fr.close();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    // FileWriter and BufferedWriter which will write the corresponding information to a file
    public static void writeFile (String x) throws MalformedURLException {
        final String filename = x;
        FileWriter fw = null;
        BufferedWriter bw = null;

        try {
            fw = new FileWriter(filename);
            bw = new BufferedWriter(fw);

            // While the "reading" queue is not empty
            while (!readQueue.isEmpty()) {
                // Remove the URL from queue and create new URL object
                URL url = new URL(readQueue.remove());

                // Open a new connection from the given URL
                URLConnection u = url.openConnection();

                // Print the external form of the URL from the connection
                bw.write(u.getURL().toExternalForm() + ":\n");

                // Print the content type from the connection (eg. pdf, image, text, etc)
                bw.write("  Content Type: " + u.getContentType()+"\n");

                // Print the content size from the connection (in bytes)
                bw.write("  Content Length: " + u.getContentLength()+"\n");

                // Print the last modified date from the connection
                bw.write("  Last Modified: " + new Date(u.getLastModified())+"\n");

                // Print the expiration of the content from the connection (stability attribute)
                bw.write("  Expiration: " + u.getExpiration()+"\n");

                // Print the content encoding header of the content from connection, null if unknown
                bw.write("  Content Encoding: " + u.getContentEncoding()+"\n");
                bw.newLine();
            }
        }

        // Exception handler
        catch (IOException e) {
            e.printStackTrace();
        }

        // Close writers once operation is finished
        finally {
            try {
                if (bw != null)
                    bw.close();
                if (fw != null)
                    fw.close();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws MalformedURLException {

        // String variables that will hold the input/output file names
        String inputName = "";
        String outputName = "";

        // Check command line flags:
        // 1. If at least 2 flags were passed
        // 2. If both of the flags are proper .txt names
        if (args.length < 2 || !args[0].endsWith(".txt") || !args[1].endsWith(".txt")) {
            System.err.println("Invalid Parameters: Please refer to readme.txt");
            return;
        }
        else {
            inputName = args[0];
            outputName = args[1];
        }

        // Read and write the file
        readFile(inputName);
        writeFile(outputName);
    }
}
