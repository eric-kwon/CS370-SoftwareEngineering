
    // Name : Eric Kwon
    // CSCI370 Project Phase #1
    // Objective : Create a data storage using hashtable

import java.io.BufferedReader;;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat;

@SuppressWarnings("unchecked")
class database {

    // LinkedHashMap instance where the product information will be stored
    // LHM is used to retain the order in which data is inserted
    // LHM format: LinkedHashMap <Product Name, LinkedHashMap <Attribute, Value>>
    public static LinkedHashMap <String,String> putContents (String[] x) {
        LinkedHashMap<String,String> product = new LinkedHashMap<String, String>();
        product.put ("itemDesc" , x[1]);
        product.put ("itemQty" , x[2]);
        product.put ("itemPrice" , x[3]);
        product.put ("itemCost" , x[4]);
        product.put ("datePurchased" , x[5]);
        product.put ("dateModified" , x[6]);
        return product;
    }

    // Method to read the input file
    // Parameter: input file name
    public static LinkedHashMap <String, LinkedHashMap<String, String>> readFile(String x) {
        final String fileName = x;
        FileReader fr = null;
        BufferedReader br = null;
        LinkedHashMap<String,LinkedHashMap<String, String>> table = new LinkedHashMap<String, LinkedHashMap<String, String>>();

        // FileReader, and BufferedReader as data will be read line by line
        try {
            fr = new FileReader(fileName);
            br = new BufferedReader(fr);

            // "|" is designated as the delimiter as input file is expected to use "|" as the delimiter
            String line;
            String delimiter = "[|]";

            // Per each line, using "|" as the delimiter, the line is broken up into array of String values
            while ((line = br.readLine()) != null) {

                // If the input file contains header, skip to next iteration
                if (line.startsWith("ITEM NAME"))
                    continue;
                String[] data = line.split(delimiter);
                table.put (data[0], putContents(data));
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }

        // Closing FileReader and BufferedReader once the data processing is done
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

        // Return the created LHM to be used in main method
        return table;
    }

    // Method to take the LHM along with output file name as parameter
    // Input LHM - the instance created from the input
    // Input string - the filename for the output file
    public static void writeFile (LinkedHashMap y, String x) {
        FileWriter fw = null;
        BufferedWriter bw = null;

        try {
            fw = new FileWriter(x);
            bw = new BufferedWriter(fw);

            // Print header for the output file
            bw.write("ITEM NAME|DESCRIPTION|QTY|PRICE|COST|DATE PURCHASED|DATE MODIFIED\n");

            // Iterator to browse through all the existing keys in the main LinkedHashMap
            // pKey = Names of the products inserted into the LinkedHashMap
            Set pKeySet = y.entrySet();
            Iterator pKeyIter = pKeySet.iterator();

            while (pKeyIter.hasNext()) {

                // Create map entry for all existing primary keys
                Map.Entry<String, LinkedHashMap<String, String>> pKeyEntry = (Map.Entry) pKeyIter.next();
                String pKey = pKeyEntry.getKey();
                bw.write(pKey);

                // Iterator to browse through all the existing keys WITHIN the primary key LHM
                // fKey = Names of the attributes and their corresponding values
                Set fKeySet = ((LinkedHashMap)(y.get(pKey))).entrySet();
                Iterator fKeyIter = fKeySet.iterator();

                while (fKeyIter.hasNext()) {

                    // Create map entry for all existing foreign keys
                    Map.Entry<String, String> fKeyEntry = (Map.Entry) fKeyIter.next();
                    String fValue = fKeyEntry.getValue();
                    bw.write("|" + fValue);
                }
                bw.newLine();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        // Close the filewriter and the bufferedwriter once data processing is finished
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

    // Method to allow editing of the LHM entries
    public void editMode (LinkedHashMap<String,LinkedHashMap<String,String>> y) {

        // Scanner to determine the desired action, and the operation flag (type char)
        Scanner input = new Scanner(System.in);
        char operation;

        do {
            System.out.println("::: RECORD EDIT MODE :::");
            System.out.println("COMMAND: A - ADD RECORD / M - MODIFY EXISTING RECORD / V - VIEW RECORD / D - DELETE RECORD / X - EXIT");
            System.out.print("ENTER COMMAND: ");
            operation = input.next().toLowerCase().charAt(0);

            if (operation == 'a')
                addMode_Frame(y);

            else if (operation == 'm')
                modifyRecord(y);

            else if (operation == 'v')
                viewRecord(y);

            else if (operation == 'd')
                deleteRecord(y);

        } while (operation != 'x');

        System.out.println();
    }

    // Method to allow adding of additional records once the input file is taken in
    public void addMode_Frame (LinkedHashMap<String, LinkedHashMap<String,String>> y) {

        // Scanner to take in the desired values, and break them into array of strings
        Scanner input = new Scanner(System.in);
        String[] data = new String[7];
        System.out.println();
        System.out.println("::: ADD RECORD :::");
        System.out.print("ENTER ITEM NAME: ");
        data[0] = input.nextLine().toUpperCase();
        System.out.print("ENTER ITEM DESCRIPTION: ");
        data[1] = input.nextLine();
        System.out.print("ENTER ITEM QUANTITY: ");
        data[2] = input.nextLine();
        System.out.print("ENTER ITEM PRICE: ");
        data[3] = input.nextLine();
        System.out.print("ENTER ITEM COST: ");
        data[4] = input.nextLine();
        System.out.print("ENTER PURCHASED DATE (YYYY-MM-DD): ");
        data[5] = input.nextLine();

        // Date in which the addition was made - using timestamp
        data[6] = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        // Create additional record
        y.put(data[0],putContents(data));

        System.out.println("RECORD FOR " + data[0] + " ADDED");
        System.out.println();
    }

    // Method to allow for modification of existing records
    public void modifyRecord (LinkedHashMap<String, LinkedHashMap<String,String>> y) {

        // Scanner to take in the record that needs to be edited
        Scanner input = new Scanner(System.in);
        String modify;
        System.out.println();
        System.out.println("::: MODIFY RECORD :::");
        System.out.print("ENTER NAME OF ITEM TO MODIFY: ");
        modify = input.nextLine().toUpperCase();

        if (y.get(modify) != null) {

            // Ask user which part of the record needs to be modified
            char operation;
            System.out.println();
            System.out.println("::: " + modify + " SELECTED :::");
            do {
                System.out.println("COMMAND: D - DESCRIPTION / Q - QUANTITY / P - PRICE / C - COST / V - PURCHASE DATE / X - EXIT");
                System.out.print("ENTER COMMAND: ");
                operation = input.nextLine().toLowerCase().charAt(0);
                String newValue;

                switch (operation) {
                    case 'd':
                        System.out.print("ENTER NEW VALUE: ");
                        newValue = input.nextLine();
                        y.get(modify).put("itemDesc",newValue);
                        System.out.println("RECORD MODIFIED TO: " + newValue);
                        System.out.println();
                        break;
                    case 'q':
                        System.out.print("ENTER NEW VALUE: ");
                        newValue = input.nextLine();
                        y.get(modify).put("itemQty",newValue);
                        System.out.println("RECORD MODIFIED TO: " + newValue);
                        System.out.println();
                        break;
                    case 'p':
                        System.out.print("ENTER NEW VALUE: ");
                        newValue = input.nextLine();
                        y.get(modify).put("itemPrice",newValue);
                        System.out.println("RECORD MODIFIED TO: " + newValue);
                        System.out.println();
                        break;
                    case 'c':
                        System.out.print("ENTER NEW VALUE: ");
                        newValue = input.nextLine();
                        y.get(modify).put("itemCost",newValue);
                        System.out.println("RECORD MODIFIED TO: " + newValue);
                        System.out.println();
                        break;
                    case 'v':
                        System.out.print("ENTER NEW VALUE (YYYY-MM-DD): ");
                        newValue = input.nextLine();
                        y.get(modify).put("datePurchased",newValue);
                        System.out.println("RECORD MODIFIED TO: " + newValue);
                        System.out.println();
                        break;
                    case 'x':
                        System.out.println();
                        break;
                    default:
                        System.out.println("INVALID COMMAND");
                        System.out.println();
                }

                // Enter the date in which the modification was made, using timestamp
                newValue = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                y.get(modify).put("dateModified",newValue);

            } while (operation != 'x');
        }

        // If the requested record does not exist
        else
            System.out.println(modify + " DOES NOT EXIST");
    }

    // Method to view existing records
    public static void viewRecord (LinkedHashMap<String,LinkedHashMap<String,String>> y) {

        // Scanner to take in the name of the record that needs to be viewed
        Scanner input = new Scanner(System.in);
        System.out.println();
        System.out.println("::: VIEW RECORD :::");
        System.out.print("ENTER THE ITEM NAME TO VIEW: ");
        String viewer = input.nextLine().toUpperCase();

        // As long as the pointer is not pointing to a null value, proceed with the operation
        // Display the primary key
        if (y.get(viewer) != null) {
            Set fKeySet = ((LinkedHashMap)(y.get(viewer))).entrySet();
            Iterator fKeyIter = fKeySet.iterator();
            System.out.print(viewer);

            // Display the values for each corresponding foreign keys
            while (fKeyIter.hasNext()) {
                Map.Entry<String, String> fKeyEntry = (Map.Entry) fKeyIter.next();
                String fValue = fKeyEntry.getValue();
                System.out.print("|" + fValue);
            }
            System.out.println("\n");
        }

        // If the record does not exist, display so
        else {
            System.out.println("INVALID NAME ENTERED");
            System.out.println();
        }

    }

    // Method to allow for deletion of existing records
    public static void deleteRecord (LinkedHashMap<String, LinkedHashMap<String,String>> y) {

        // Scanner to take the target record name
        Scanner input = new Scanner(System.in);
        String delete;
        System.out.println();
        System.out.println("::: DELETE RECORD :::");
        System.out.print("ENTER NAME OF RECORD TO DELETE: ");
        delete = input.nextLine().toUpperCase();

        // As long as the pointer is not pointing to null, proceed
        if (y.get(delete) != null) {
            y.remove(delete);
            System.out.println(delete + " HAS BEEN DELETED");
            System.out.println();
        }

        // Display notification if the record does not exist
        else {
            System.out.println("INVALID NAME");
        }
    }
}