package com.d2s2.dataScience;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        CSVReader csvReader = new CSVReader();
        csvReader.read();
        System.out.println(csvReader.getColumnCount());
        csvReader.printNullValuedColumns();
        csvReader.replaceWithFrequent(12);
        csvReader.writeToCSV();


    }
}
