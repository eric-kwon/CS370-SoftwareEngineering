import java.util.LinkedHashMap;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {

        // Default input and output filename set at input.txt & output.txt
        String inputFile = "input.txt";
        String outputFile = "output.txt";
        Boolean runGUI = false;

        for (int i=0 ; i < args.length ; i++) {
            if (args[i].startsWith("-")) {
                String argument = args[i].toLowerCase();
                switch (argument) {

                    // For case of -i set following argument to input filename
                    case "-i":
                        inputFile = args[++i];
                        break;

                    // For case of -o set following argument to output filename
                    case "-o":
                        outputFile = args[++i];
                        break;

                    // For case of -gui set GUI boolean to true so it can be invoked
                    case "-gui":
                        runGUI = true;
                        break;

                    // For case of -help display usage help and exit
                    case "-help":
                        System.out.println("Usage: java main [-i filename] [-o filename] [-gui]");
                        System.out.println("    -i filename:  Designate input text file name (eg. -i input.txt)");
                        System.out.println("    -o filename:  Designate output text file name (eg. -o output.txt)");
                        System.out.println("    -gui       :  Invoke GUI instead of console");
                        return;
                    default:
                        System.err.println("Invalid Flag: type -help for valid usage");
                        return;
                }

            }
        }

        // If -gui flag is detected, invoke JFrame and return so console method is not invoked
        if (runGUI == true) {
            DBGui mainFrame = new DBGui(inputFile, outputFile);
            mainFrame.setVisible(true);
            return;
        }


        // Create new database instance
        database newDBInstance = new database();
        LinkedHashMap<String, LinkedHashMap<String, String>> products = newDBInstance.readFile(inputFile);

        // Scanner to take in the flag for desired operation
        Scanner input = new Scanner(System.in);
        char operation;
        do {
            System.out.println("::: MAIN MENU :::");
            System.out.println("COMMAND: P - PROCESS DATA / E - ENTER EDIT MODE");
            System.out.print("ENTER COMMAND: ");
            operation = input.nextLine().toLowerCase().charAt(0);

            if (operation == 'e') {
                System.out.println();
                newDBInstance.editMode(products);
            }
            else if ((operation != 'p') && (operation != 'e'))
                System.out.println("INVALID COMMAND");
        } while (operation != 'p');

        // Write the result to file after desired operations are done
        newDBInstance.writeFile(products,outputFile);
    }
}
