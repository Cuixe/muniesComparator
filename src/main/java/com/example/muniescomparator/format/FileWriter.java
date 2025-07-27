package com.example.muniescomparator.format;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@Slf4j
public enum FileWriter {

    ExcelWriter {
        public String write(String output, Map<Integer, List<String>> rows) throws IOException {
            output += ".csv";
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
                try (FileOutputStream fos = new FileOutputStream("/tmp/" + output)) {
                    workbook.write(fos);
                }
            }
            return output;
        }
    },

    CSVWriter {
        public String write(String outputFile, Map<Integer, List<String>> rows) throws IOException {
            outputFile += ".csv";
            Path path = Paths.get(outputFile);
            log.debug("Writing file: {}", outputFile);
            boolean eliminado = Files.deleteIfExists(path);
            log.debug("Se eliminió el archivo {}?  {}", outputFile, eliminado);
            try (FileOutputStream fos = new FileOutputStream("/tmp/" + outputFile)) {
                StringBuilder sb = new StringBuilder();
                for (Map.Entry<Integer, List<String>> record : rows.entrySet()) {
                    List<String> values = record.getValue();
                    for (int j = 0; j < values.size(); j++) {
                        sb.append(values.get(j));
                        if (j < values.size() - 1) {
                            sb.append(",");
                        }
                    }
                    sb.append("\n");
                }
                fos.write(sb.toString().getBytes());
                fos.close();
                log.debug("File: {} has been written", outputFile);
            }
            return outputFile;
        }
    };

    public abstract String write(String outputFile, Map<Integer, List<String>> rows) throws IOException ;
}
