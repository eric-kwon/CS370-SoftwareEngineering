import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FetchCategories {

    // Queue that will contain the matches
    Queue<Integer> queueID;
    Queue<String> queueCat;

    // Page where the entire seller list is contained
    final String advSearch = "https://www.shopgoodwill.com/AdvancedSearch";

    // Arrays that will contain the category ID & category name
    int[] arrayID;
    String[] arrayCat;
    
    // Instance counter
    int howMany = 0;

    // Method to fetch the categories
    public void fetchCats() {

        // Initiate the queues
        queueID = new LinkedList<Integer>();
        queueCat = new LinkedList<String>();
        BufferedReader reader = null;

        // Try block
        try {

            // Initiate a new bufferedreader
            URL url = new URL(advSearch);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            
            // Reader line
            String line;

            // Pattern to match
            Pattern patternCat = Pattern.compile("value=\"(\\d*?)\">([^-]*?)</option>");

            // Boolean to indicate whether the block containing the categories were found
            Boolean blockFound = false;

            // Start reading from the URL
            while ((line = reader.readLine()) != null) {
            	
                // Indicate the flag once the block with the category is found
                if (!blockFound && line.contains("All Categories")) blockFound = true;

                // Indicates the end of block to prevent matcher from reading more
                if (blockFound && line.contains("</select>")) break;

                // Regex matcher
                Matcher matchCat = patternCat.matcher(line);

                // If a match was found
                if (blockFound && matchCat.find()) {
                    queueID.add(Integer.valueOf(matchCat.group(1)));
                    queueCat.add(matchCat.group(2).replace("&amp;","&"));
                    howMany++;
                }
            }

            // Place the fetched components into the array
            arrayID = new int[howMany];
            arrayCat = new String[howMany];

            // Index for the array
            int i = 0;

            // Empty out the queue
            while (!queueID.isEmpty()) {
                arrayID[i] = queueID.remove();
                arrayCat[i++] = queueCat.remove();
            }
        }
        
        // Exception handler
        catch (IOException e) {
        	e.printStackTrace();
        }
        
        // Close out the readers once the operation is done
        finally {
        	try {
        		if (reader != null)
        			reader.close();
        	}
        	catch (IOException ex) {
        		ex.printStackTrace();
        	}
        } 
    }

    // Returns the array of categories
    public String[] getCatArray() {
    	return arrayCat;
    }
    
    // Returns the array of category ID's
    public int[] getIDArray() {
    	return arrayID;
    }
}
