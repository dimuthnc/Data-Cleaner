package com.d2s2.dataScience;

import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        Scanner sc = new Scanner(System.in);
        CSVReader csvReader = null;
        System.out.println("Please Enter the name of CSV file");

        String  filename = sc.nextLine();
        if(filename.length()>4 && filename.substring(filename.length()-4,filename.length()).equals(".csv")){
            filename = filename.substring(0,filename.length()-4);
        }

        csvReader = new CSVReader(filename);

        csvReader.processFile();

        /*
        System.out.println(csvReader.getColumnCount());
        csvReader.printNullValuedColumns();
        csvReader.replaceWithFrequent(12);
        csvReader.writeToCSV();
*/






    }
}
