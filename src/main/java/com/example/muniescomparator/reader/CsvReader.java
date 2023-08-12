package com.example.muniescomparator.reader;

import com.example.muniescomparator.vo.CsvFileSheet;
import com.example.muniescomparator.vo.Fields;

import java.io.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class CsvReader implements MuniesFileReader {

    public CsvFileSheet  readFile(InputStream inputStream) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line = reader.readLine();
        CsvFileSheet fileSheet = new CsvFileSheet();
        fileSheet.setName("");

        while (line != null) {
            String[] row = line.split(",");
            if (fileSheet.getHeaders() == null) {
                fileSheet.setHeaders(getHeaders(row));
            } else {
                Fields fields = new Fields();
                int index = 0;
                for (String cell : row) {
                    String columName = fileSheet.getHeaders().get(index);;
                    if (columName.equals("JRNL_ACCOUNTING_DATE")) {
                        fields.setFecha(LocalDate.parse(cell.substring(0,10)));
                    } else if (columName.equals("ENT_JRNL_LINE_DR")) {
                        if (!cell.isEmpty()) {
                            fields.setDepositos(Double.parseDouble(cell));
                        }
                    } else if (columName.equals("ENT_JRNL_LINE_CR")) {
                        if (!cell.isEmpty()) {
                            fields.setRetiros(Double.parseDouble(cell));
                        }
                    }
                    fields.getRow().add(cell);
                    index++;
                }
                fileSheet.getFieldsList().add(fields);
                // read next line

            }
            line = reader.readLine();
        }
        return fileSheet;
    }

    private Map<Integer, String> getHeaders(String[] row) {
        Map<Integer, String> headers;
        headers = new HashMap<>();
        int index = 0;
        for (String cell : row) {
            headers.put(index, cell);
            System.out.println("Header: " + index + "-" + cell);
            index++;
        }
        return headers;
    }
}
