package Presentation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


/**
 * constructor
 * @author Roland
 *
 */
public class Validate {
    public String[] Fileinput;
    public Validate() {
        Fileinput = ReadInput();
    }

    /**
     * returns lines read from a text file given as input
     * @return
     */
    public static String[] ReadInput() {
        String line[] = new String[100];
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("D:\\FACULTA\\ANU 2\\eclipseProjects\\Assignment3\\input.txt.txt"));

            int i =0;
            while((line[i] = br.readLine())!=null)
                i++;

        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                br.close();
            }catch(IOException e) {
                e.printStackTrace();
            }
        }
        return line;
    }

    /**
     * returns a command broken down into an array of words
     * @param input
     * @return
     */
    public static String[] BreakDownCommand(String input) {

        String[] arr = input.split("\\s|,\\s|:\\s");
        return arr;

    }
    /**
     * checks if the command put in has a valid form and if  it actually a valid command
     * @param command
     * @return
     */
    public int ValidateCommand(String[] command) {
        if(!(command[0].contentEquals("Delete") || command[0].contentEquals("Insert") || command[0].contentEquals("Report") || command[0].contentEquals("Order"))) return 0;
        if(!(command[0].contentEquals("Order")) && !(command[1].contentEquals("client") || command[1].contentEquals("product") || command[1].contentEquals("order"))) return 0;
        return 1;
    }

    /**
     * returns a number that represents a command
     * @param command
     * @return
     */
    public int SelectCommand(String[] command) {
        if(command[0].contentEquals("Delete") && command[1].contentEquals("client"))return 1;
        if(command[0].contentEquals("Delete") && command[1].contentEquals("product"))return 2;
        if(command[0].contentEquals("Insert") && command[1].contentEquals("client"))return 3;
        if(command[0].contentEquals("Insert") && command[1].contentEquals("product"))return 4;
        if(command[0].contentEquals("Order"))return 5;
        if(command[0].contentEquals("Report") && command[1].contentEquals("client"))return 6;
        if(command[0].contentEquals("Report") && command[1].contentEquals("product"))return 7;
        if(command[0].contentEquals("Report") && command[1].contentEquals("order"))return 8;
        return 0;
    }
}
