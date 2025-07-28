package com.example.muniescomparator.file;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ExcelFileWriter implements FileWriter {

    @Override
    public String write(String fileName, Map<Integer, List<String>> rows) throws IOException {
        fileName += ".csv";
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Datos");
            int i = 0;
            for(Map.Entry<Integer, List<String>> record : rows.entrySet()) {
                Row row = sheet.createRow(i++);
                List<String> values = record.getValue();
                for (int j = 0; j < values.size(); j++) {
                    row.createCell(j).setCellValue(values.get(j));
                }
            }
            try (FileOutputStream fos = new FileOutputStream("/tmp/" + fileName)) {
                workbook.write(fos);
            }
        }
        return fileName;
    }
}
