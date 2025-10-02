package utilities;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CSVFileManager {
    CSVParser csvparser;
    Iterable<CSVRecord>  csvRecord;
    public CSVFileManager(String csvFilePath) {
       try {
           FileReader fileReader = new FileReader(csvFilePath);
           csvparser= CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(fileReader);
           FileReader reader = new FileReader(csvFilePath);
           csvRecord = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);

       } catch (Exception e) {
           e.printStackTrace();
       }

    }
    public List<String> getColoumnsName(){
        try {
           return  new ArrayList<>(csvparser.getHeaderNames());
        }catch (Exception e){
            throw new RuntimeException();

        }
    }

    public  List<String[]> getRows(){
        List<String[]> rows = new ArrayList<>();

 for (CSVRecord record :csvRecord )
 { String []data = new String[record.size()];
    for(int i =0 ; i < data.length ; i++)
    {
        data[i] = record.get(i);}
    rows.add(data);  }  return  rows;
    }

    public Object[][] getDataAsObjectArray() {
        List<String[]> rows = getRows();
        Object[][] data = new Object[rows.size()][];
        for (int i = 0; i < rows.size(); i++) {
            data[i] = rows.get(i);
        }
        return data;
    }
}