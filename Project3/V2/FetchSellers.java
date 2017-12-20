import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FetchSellers {

    // Queue that will contain the matches
    Queue<Integer> queueID;
    Queue<String> queueSel;

    // Page where the entire seller list is contained
    final String advSearch = "https://www.shopgoodwill.com/AdvancedSearch";

    // Arrays that will contain the seller ID & seller name
    int[] arrayID;
    String[] arraySel;
    
    // Instance counter
    int howMany = 1;

    // Method to fetch the sellers
    public void fetchSellers() {

        // Initiate the queues
        queueID = new LinkedList<Integer>();
        queueSel = new LinkedList<String>();
        BufferedReader reader = null;

        // Try block
        try {

            // Initiate a new bufferedreader
            URL url = new URL(advSearch);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            
            // Reader line
            String line;

            // Pattern to match
            Pattern patternSel = Pattern.compile("<option value=\"(\\d*?)\">(([A-Z]{2}) - (.*?) - (.*?))</option>");

            // Boolean to indicate whether the block containing the sellers were found
            Boolean blockFound = false;

            // Start reading from the URL
            while ((line = reader.readLine()) != null) {
            	
                // Indicate the flag once the block with the seller is found
                if (!blockFound && line.contains("All Sellers")) {
                	blockFound = true;
                	queueID.add(0);
                	queueSel.add("All Sellers");
                	continue;
                }

                // Indicates the end of block to prevent matcher from reading more
                if (blockFound && line.contains("</select>")) break;

                // Regex matcher
                Matcher matchSel = patternSel.matcher(line);

                // If a match was found
                if (blockFound && matchSel.find()) {
                    queueID.add(Integer.valueOf(matchSel.group(1)));
                    queueSel.add(matchSel.group(5).replace("&amp;", "&"));
                    howMany++;
                }
            }

            // Place the fetched components into the array
            arrayID = new int[howMany];
            arraySel = new String[howMany];

            // Index for the array
            int i = 0;

            // Empty out the queue
            while (!queueSel.isEmpty()) {
                arrayID[i] = queueID.remove();
                arraySel[i++] = queueSel.remove();
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

    // Returns the array of sellers
    public String[] getSellerArray() {
    	return arraySel;
    }
    
    // Returns the array of seller ID's
    public int[] getIDArray() {
    	return arrayID;
    }
}
