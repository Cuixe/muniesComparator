package com.example.muniescomparator.reader;

import com.example.muniescomparator.vo.CsvFileSheet;
import com.example.muniescomparator.vo.Fields;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class CsvReader implements MuniesFileReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(CsvReader.class);

    public CsvFileSheet  readFile(InputStream inputStream) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line = reader.readLine();
        CsvFileSheet fileSheet = new CsvFileSheet();
        fileSheet.setName("");
        int rowNumber = 0;
        while (line != null) {
            String[] row = line.split(",");
            if (fileSheet.getHeaders() == null) {
                fileSheet.setHeaders(getHeaders(row));
            } else {
                Fields fields = new Fields();
                fields.setIndex(rowNumber);
                int column = 0;
                for (String cell : row) {
                    String columName = fileSheet.getHeaders().get(column);
                    if (columName.contains("JRNL_ACCOUNTING_DATE")) {
                        fields.setFecha(LocalDate.parse(cell.substring(0,10)));
                    } else if (columName.contains("ENT_JRNL_LINE_DR")) {
                        if (!cell.isEmpty()) {
                            fields.setDepositos(Double.parseDouble(cell));
                        }
                    } else if (columName.contains("ENT_JRNL_LINE_CR")) {
                        if (!cell.isEmpty()) {
                            fields.setRetiros(Double.parseDouble(cell));
                        }
                    }
                    fields.getRow().add(cell);
                    column++;
                }
                fileSheet.getFieldsList().add(fields);
            }
            line = reader.readLine();
            rowNumber++;
        }
        return fileSheet;
    }

    private Map<Integer, String> getHeaders(String[] row) {
        Map<Integer, String> headers;
        headers = new HashMap<>();
        int index = 0;
        for (String cell : row) {
            headers.put(index, cell.strip());
            LOGGER.info("Header: " + index + "-" + cell);
            index++;
        }
        return headers;
    }
}
