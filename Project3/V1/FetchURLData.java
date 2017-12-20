/**     Name: Eric Kwon
 *      Project Phase 3 (FetchURLData.java)
 * 
 *      Purpose of this File:
 *      1) Given a URL, fetch the source file
 *      2) After fetching the source file, search for the product components
 *      3) Read the product components using regex and output as a table
 */

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class FetchURLData {

    // Constants (Eg. Initial form of URL, deprecated paramters)
    final String initialForm   = "https://www.shopgoodwill.com/Listings";       // Initial form of URL
    final String searchGroup   = "sg=";                                         // Search Group (Deprecated)
    final String savedSearch   = "ss=0";                                        // Saved Search (Deprecated)
    final String useBuyerPrefs = "UseBuyerPrefs=true";                          // User Preference (Deprecated)

    // How many pages of result does this query contain
    int numPages = 0;

    // How many query such fetch processes
    int queryCount = 0;

    // Parameter Set
    String searchTerm, category, seller, lowPrice, highPrice, buyNow, pickup, nonPickup, oneCentShip, searchDesc;
    String closedAuction, searchDate, dateGoback, canadaShip, intlShip, sortBy, pageNumber, lastListingonpage, descending;

    // Default Constructor
    public FetchURLData() {
    searchTerm          = "st=";            // Search Text
    category            = "c=";             // Category
    seller              = "s=";             // Seller ID
    lowPrice            = "lp=0";           // Low Price Threshold (Default = 0)
    highPrice           = "hp=999999";      // High Price Threshold (Default = 999999)
    buyNow              = "sbn=false";      // Search buy now only (Default = False)
    pickup              = "spo=false";      // Search pickup only (Default = False)
    nonPickup           = "snpo=false";     // Search non pickup only (Default = False)
    oneCentShip         = "socs=false";     // Search free shipping (one-cent) only (Default = False)
    searchDesc          = "sd=false";       // Search title and descriptions (Default = False)
    closedAuction       = "sca=false";      // Search closed auctions (Default = False)
    searchDate          = "caed=";          // Search date (Used if closedAuction == True) (Default = Search Date)
    dateGoback          = "cadb=7";         // Search date range (Used if closedAuction == True) (Default = 7)
    canadaShip          = "scs=false";      // Ships to Canada (Default = False)
    intlShip            = "sis=false";      // Ships internationally (Default = False)
    sortBy              = "col=0";          // Sort by option (Default = 0)
    pageNumber          = "p=1";            // Search query page number (Default = 1)
    lastListingonpage   = "ps=40";          // Last listing on page of the search query
    descending          = "desc=false";     // Display result in descending order (Default = False)
    }

    // Sets the search term
    void set_searchTerm (String x) {
        searchTerm += x;
    }

    // Sets the category
    void set_category (int x) {
        category += Integer.toString(x);
    }

    // Sets the seller
    void set_seller (int x) {
        seller += Integer.toString(x);
    }

    // Sets the low price
    void set_lowPrice (int x) {
        lowPrice = "lp=" + Integer.toString(x);
    }

    // Sets the high price
    void set_highPrice (int x) {
        highPrice = "hp=" + Integer.toString(x);
    }

    // Sets buy now only option
    void set_buyNow (Boolean x) {
        buyNow = x ? "sbn=true" : "sbn=false";
    }

    // Sets pickup only option
    void set_pickup (Boolean x) {
        pickup = x ? "spo=true" : "spo=false";
    }

    // Sets non-pickup only option
    void set_nonPickup (Boolean x) {
        nonPickup = x ? "snpo=true" : "snpo=false";
    }

    // Sets free shipping only option
    void set_oneCentShip (Boolean x) {
        oneCentShip = x ? "socs=true" : "socs=false";
    }

    // Sets search by description option
    void set_searchDesc (Boolean x) {
        searchDesc = x ? "sd=true" : "sd=false";
    }

    // Sets search for closed auction option
    void set_closedAuction (Boolean x) {
        closedAuction = x ? "sca=true" : "sca=false";
    }

    // Sets closed auction search date range
    void set_searchDate (String x) {
        searchDate += x;
    }

    // Sets amount of days to go back for closed auction search
    void set_dateGoBack (int x) {
        dateGoback = "cadb=" + Integer.toString(x);
    }

    // Sets to search for Canada shipping items
    void set_canadaShip (Boolean x) {
        canadaShip = x ? "scs=true" : "scs=false";
    }

    // Sets to search for International shipping items
    void set_intlShip (Boolean x) {
        intlShip = x ? "sis=true" : "sis=false";
    }

    // Sets sort by option
    void set_sortBy (int x) {
        sortBy = "col=" + Integer.toString(x);
    }

    // Sets page number
    void set_pageNumber (int x) {
        pageNumber = "p=" + Integer.toString(x);
    }

    // Sets max lising per page
    void set_lastListingonpage (int x) {
        lastListingonpage = "ps=" + Integer.toString(x);
    }

    // Sets if result should display in descending order
    void set_descending (Boolean x) {
        descending = x ? "desc=true" : "desc=false";
    }

    // Function to concatenate initial form with the parameters
    public String getURL() {
        return  initialForm         + "?" +
                searchTerm          + "&" +
                searchGroup         + "&" +
                category            + "&" + 
                seller              + "&" +
                lowPrice            + "&" +
                highPrice           + "&" +
                buyNow              + "&" +
                pickup              + "&" +
                nonPickup           + "&" +
                oneCentShip         + "&" +
                searchDesc          + "&" +
                closedAuction       + "&" +
                searchDate          + "&" +
                dateGoback          + "&" +
                canadaShip          + "&" +
                intlShip            + "&" +
                sortBy              + "&" +
                pageNumber          + "&" +
                lastListingonpage   + "&" +
                descending          + "&" +
                savedSearch         + "&" + 
                useBuyerPrefs;
    }

    // Fetch the number of queries
    public int getQueryCount() {
        return queryCount;
    }

    // Method to fetch data from provided URL
    public String fetchData() {

        // Set query count to 0
        queryCount = 0;

        // Placeholder for the streams and buffers
        BufferedReader reader = null;
        FileWriter fw = null;
        BufferedWriter bw = null;

        // First page and last page
        int pageNumFirst = 1;
        int pageNumLast = 0;

        // Save query to another folder
        String queryFolder = "queries";
        Path queryPath = Paths.get(queryFolder);

        // If directory doesn't exist, create the folder
        if (!Files.exists(queryPath)) {

            // Try block
            try {
                Files.createDirectories(queryPath);
            }

            // Exception handler
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Query filename - by time
        DateFormat dateFormat = new SimpleDateFormat("MMddyyyy-HHmmss");
        String queryRunDT = dateFormat.format(new Date());
        String queryResult = queryFolder + "/" + queryRunDT + ".txt";
        
        // Try block
        try {

            // The writers
            fw = new FileWriter(queryResult);
            bw = new BufferedWriter(fw);

            // Do while loop to run through first and process until last page is met
            do {

                // Set page number and increment
                set_pageNumber(pageNumFirst++);

                // Try block to fetch the temporary html file as a text
                try{

                    // URL objects to fetch the data through URL stream reader
                    URL url = new URL(getURL());
                    reader = new BufferedReader(new InputStreamReader(url.openStream()));

                    // Get the code from the stream
                    String line;
                    
                    // Regex for pattern maching
                    Pattern product = Pattern.compile("(?<=Product #: </span>)(.*)(?=<\\/div>)");
                    Pattern lastPage = Pattern.compile("(?<=\"last\" data-page=\")(.*)(?=\" onclick)");

                    while ((line = reader.readLine()) != null) {

                        // Matchers to match the pattern above
                        Matcher prdMatch = product.matcher(line);
                        Matcher lpageMatch = lastPage.matcher(line);

                        // Last page number found > save
                        if (lpageMatch.find()) {
                            pageNumLast = Integer.valueOf(lpageMatch.group());
                        }

                        // Product number found > process
                        if (prdMatch.find()) {
                            fetchProduct(prdMatch.group(), bw);
                        }


                    }

                    // Close the incoming / outgoing stream once operation is done
                    reader.close();
                }

                // Catch block
                catch(IOException e) {
                    e.printStackTrace();
                }

                // Once operations are done, close out the readers
                finally {
                    try {
                        if (reader != null)
                            reader.close();
                    }
                    catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            } while(pageNumFirst <= pageNumLast);
        }

        // Catch block
        catch (IOException e) {
            e.printStackTrace();
        }

        // Once operations are done, close ou the writers
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

        // Return the name of the query file
        return queryResult;
    }

    // Method to fetch the interior product information from URL
    public void fetchProduct(String itemID, BufferedWriter bw) {

        // Essential product information
        String itemTitle = "";
        String itemPrice = "";
        String itemAuction = "";
        String itemSeller = "";
        String itemPic = "";

        // FetchImage class
        FetchImage getImage = new FetchImage();

        // Time and date of the query instance
        DateFormat dateFormt = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String itemQueryDT = dateFormt.format(new Date());

        // Lines to be stored on the table
        String tableLine = "";

        // Placeholder for buffers and stream
        BufferedReader prdReader = null;

        // Try block
        try {

            // URL of the product
            URL prdUrl = new URL("https://www.shopgoodwill.com/Item/"+itemID);
            prdReader = new BufferedReader(new InputStreamReader(prdUrl.openStream()));

            // Get the code from the stream
            String line2;

            // Regex for pattern matching
            Pattern title = Pattern.compile("(?<=\\<title>)(.*)(?=\\s-)");
            Pattern price = Pattern.compile("(?<=current-price\"\\>)(.*)(?=<\\/span>)");
            Pattern aucDate = Pattern.compile("(?<=Ends On: <\\/b>)(.*)(?=Pacific Time)");
            Pattern seller = Pattern.compile("(?<=Seller: <\\/b>)(.*)(?=<\\/li>)");
            Pattern picture = Pattern.compile("(?<=<img src=\")(.*)(?=\" alt=\"\" class=\"product-image\">)");

            while ((line2 = prdReader.readLine()) != null) {

                // Matchers to match the pattern above
                Matcher titleMatch = title.matcher(line2);
                Matcher priceMatch = price.matcher(line2);
                Matcher aucDMatch = aucDate.matcher(line2);
                Matcher sellerMatch = seller.matcher(line2);
                Matcher pictureMatch = picture.matcher(line2);

                // Title found
                if (titleMatch.find())
                    itemTitle = titleMatch.group();

                // Price found
                if (priceMatch.find())
                    itemPrice = priceMatch.group();
                
                // Auction date found
                if (aucDMatch.find())
                    itemAuction = aucDMatch.group();
                
                // Seller found
                if (sellerMatch.find())
                    itemSeller = sellerMatch.group();

                // Picture URL found
                if (pictureMatch.find()) {
                    String picURL = pictureMatch.group();
                    getImage.saveImage(picURL, itemID, true);
                    itemPic = getImage.get_imgPath();
                }
            }

            // Processing the line, and writing to query file, and increment query count
            tableLine = itemID + "|" + itemTitle + "|" + itemPrice + "|" + itemAuction + "|" + itemSeller + "|" + itemPic + "|" + itemQueryDT;
            tableLine = tableLine.replace("&amp;","&");
            tableLine = tableLine.replace("&#39;","\'");
            tableLine = tableLine.replace("&quot;","\"");
            bw.write(tableLine);
            bw.newLine();
            queryCount++;
        }

        // Catch block
        catch(IOException e) {
            e.printStackTrace();
        }

        // Once operations are done, close out the readers
        finally {
            try {
                if (prdReader != null)
                    prdReader.close();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    // Block for testing
    public static void main (String args[]) {

        FetchURLData test = new FetchURLData();
        test.set_searchTerm("chair");
        test.fetchData();
        
    }
}