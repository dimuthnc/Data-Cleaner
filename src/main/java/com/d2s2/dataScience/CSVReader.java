package com.d2s2.dataScience;

/**
 * Created by Dimuth on 4/13/2017.
 */


import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CSVReader {
    private ArrayList<String[]> csvContent ;
    private  static int columnCount;
    private ArrayList<Integer> hasNullValueColumns = new ArrayList<Integer>();
    private ArrayList<Integer> avarageReplacableColumns = new ArrayList<Integer>();


    public void read() {

        String csvFile = "test.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        columnCount =0;



        try {
            csvContent = new ArrayList<String[]>();

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator

                String[] country = line.split(cvsSplitBy);
                updateColumnCount(country.length);
                csvContent.add(country);


            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void printNullValuedColumns(){
        for(int index=0; index<columnCount;index++){
            boolean hasNullValues = hasNullValues(index);



                System.out.println("Column "+index+" have empty values "+hasNullValues);
                if(hasNullValues){
                    hasNullValueColumns.add(index);

                }








        }
        System.out.println(hasNullValueColumns);
        for(int index =0;index< hasNullValueColumns.size();index++){
            boolean canReplaceWithAvarage = isNumerical(hasNullValueColumns.get(index));
            if(canReplaceWithAvarage){
                avarageReplacableColumns.add(hasNullValueColumns.get(index));
                System.out.println("Column "+ hasNullValueColumns.get(index)+" only have numeric values "+ isNumerical(hasNullValueColumns.get(index)));

            }
        }
        System.out.println(avarageReplacableColumns);

        for(int index =0;index< avarageReplacableColumns.size();index++){
            replaceWithAvarage(avarageReplacableColumns.get(index));
        }

    }

    private static void updateColumnCount(int value){
        if(value>columnCount) {
            columnCount = value;
        }

    }


    private void replaceWithAvarage(int column){
        String SValue;
        double valueSum = 0;
        ArrayList<Integer> nullIndexes = new ArrayList<Integer>();
        for(int index = 1 ; index < csvContent.size() ; index++ ){
            SValue = csvContent.get(index)[column];
            if(!SValue.equals("")){
                valueSum += Double.parseDouble(SValue);

            }
            else{
                nullIndexes.add(index);
            }


        }
        double average = valueSum/(csvContent.size()-1-nullIndexes.size());
        System.out.println(column+" Avarage is "+average);
    }

    public int getColumnCount(){
        return columnCount;
    }

    private boolean hasNullValues(int columnNo){
        int index =0;
        try{
            for(index=1 ; index < csvContent.size() ; index++ ){
                String value =csvContent.get(index)[columnNo];

                if(value.equals("")){
                    //System.out.println("Null value found. Row :"+index+" column : "+columnNo );
                    return true;
                }
            }
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Problem finding values for column no :"+columnNo+" in index of "+index);

            return true;



        }

        return false;

    }

    private boolean isNumerical(int columnNo){
        for(int index=1; index < csvContent.size();index++){
            String value = csvContent.get(index)[columnNo];
             try {
                Double.parseDouble(value);
            }
            catch (NumberFormatException e){

                if(!value.equals("")){
                    System.out.println(columnNo+" not numeric because of "+value+ " in index "+index);
                    return false;
                }

            }
        }
        return true;
    }

    public void replaceWithFrequent(int columnNo){
        ArrayList<String> column = new ArrayList<String>();
        HashMap<String,Integer> map= new HashMap<String,Integer>();

        for(int index =1 ; index < csvContent.size() ; index ++){
            String content = "";

            if(csvContent.get(index).length>columnNo){
                content = csvContent.get(index)[columnNo];
            }





            column.add(content);

            if(!content.equals("")){
                if(map.get(content)==null){
                    map.put(content,1);
                }
                else{
                    map.put(content,map.get(content)+1);

                }
            }
        }
        String mostFrequent ="" ;
        int mFrequentValue =0;
        int valueSum =0;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {


            String key = entry.getKey();
            int value = entry.getValue();

            if(value>mFrequentValue){
                mostFrequent = key;
                mFrequentValue = value;
            }
            valueSum += value;
            System.out.println(key+"   "+value);
            // ...
        }
        System.out.println(mostFrequent);
        System.out.println(mFrequentValue);

        double percentage = mFrequentValue*100/valueSum;
        DecimalFormat numberFormat = new DecimalFormat("#.00");


        System.out.println(numberFormat.format(percentage)+ "%");


    }
    public void writeToCSV(){
        PrintWriter pw = null;
        try{
            pw = new PrintWriter(new File("out.csv"));
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        for(int index =0 ; index < csvContent.size() ;index++ ) {
            String[] line = csvContent.get(index);
            for (int column = 0; column < line.length; column++) {
                sb.append(line[column]);
                sb.append(',');
            }
            sb.append('\n');
        }
        pw.write(sb.toString());
        pw.close();
        System.out.println("done!");
    }



}