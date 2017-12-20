import java.io.*;
import java.util.*;

public class Main {

    public static void main (String[] args) {

        // Default input and output filename set at input.txt & output.txt
        String inputFile = "input.txt";
        String outputFile = "output.txt";

        // Run through the provided flags and perform appropriate action
        for (int i=0 ; i < args.length ; i++) {

            // Argument flag found
            if (args[i].startsWith("-")) {
                String argument = args[i].toLowerCase();
                switch (argument) {

                    // For case of -i set the following argument to input filename
                    case "-i":
                        inputFile = args[++i];
                        break;

                    // For case of -o set the following argument to output filename
                    case "-o":
                        outputFile = args[++i];
                        break;

                    // For case of -help display the usage
                    case "-help":
                        System.out.println("Usage: java main [-i filename] [-o filename]");
                        System.out.println("    -i filename:  Designate input text file name (eg. -i input.txt)");
                        System.out.println("    -o filename:  Designate output text file name (eg. -o output.txt)");
                        return;

                    // Case invalid flags are received, display prompt for help
                    default:
                        System.err.println("Invalid Flag: Type -help for valid flags");
                        return;
                }
            }
        }

        // Run the program
        GUI mainProgram = new GUI(inputFile,outputFile);
    }
}