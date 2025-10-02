package utilities;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;

public class ExcelFileManager {
    XSSFWorkbook workbook;
    Sheet sheet;

    public ExcelFileManager(String filepath , String sheetname) {
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(filepath));
            workbook = new XSSFWorkbook(fileInputStream);
            sheet = workbook.getSheet(sheetname);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getColumnsCount() {
        int count=0 ;
        try {
            sheet.getRow(0).getPhysicalNumberOfCells();
        }catch (Exception e){
            e.printStackTrace();
        }
        return count;
    }
    public  String getSpecificCellValue(int rowNum , int colNum){
        return sheet.getRow(rowNum).getCell(colNum).getStringCellValue();
    }
    public Object[][] getSheetDataAsArray() {
        if (sheet == null) return new Object[0][0];

        int rowCount = sheet.getPhysicalNumberOfRows();
        int colCount = sheet.getRow(0).getPhysicalNumberOfCells();

        Object[][] data = new Object[rowCount - 1][colCount]; // Skipping header row

        for (int i = 1; i < rowCount; i++) {
            Row row = sheet.getRow(i);
            for (int j = 0; j < colCount; j++) {
                DataFormatter formatter = new DataFormatter();
                data[i - 1][j] = formatter.formatCellValue(row.getCell(j));
            }
        }

        return data;
    }


}
