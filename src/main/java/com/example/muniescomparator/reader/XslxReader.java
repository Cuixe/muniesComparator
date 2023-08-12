package com.example.muniescomparator.reader;

import com.example.muniescomparator.vo.FileSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class XslxReader implements FileReader {

    @Override
    public FileSheet readFile(String fileLocation) throws IOException {
        FileInputStream file = new FileInputStream(new File(fileLocation));
        Workbook workbook = new XSSFWorkbook(file);

        Iterator<Sheet> iterator =workbook.iterator();
        while(iterator.hasNext()) {
            Sheet sheet = iterator.next();
            FileSheet fileSheet = new FileSheet();
            fileSheet.setName(sheet.getSheetName());


            Map<Integer, List<String>> data = new HashMap<>();
            int i = 0;
            for (Row row : sheet) {
                Map<Integer, String> headers = null;
                if (headers == null) {
                    headers = getHeaders(row);
                } else {
                    fileSheet file = new 
                }



                data.put(i, new ArrayList<String>());
                for (Cell cell : row) {
                    switch (cell.getCellType()) {

                    }
                }
                i++;
            }
        }


        return null;
    }

    private Map<Integer, String> getHeaders(Row row) {
        Map<Integer, String> headers;
        headers = new HashMap<>();
        for (Cell cell : row) {
            int index = cell.getColumnIndex();
            headers.put(index, cell.getStringCellValue());
        }
        return headers;
    }
}
