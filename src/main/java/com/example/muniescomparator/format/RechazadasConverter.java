package com.example.muniescomparator.format;

import java.io.IOException;
import java.util.*;

public class RechazadasConverter extends AbstractLinesFormater {

    private List<Integer> sizes = Arrays.asList(8,6,8,5,6,13,4,7,6,6,18,21,20,4);

    @Override
    public FileFormat getFormat() {
        return FileFormat.UVRA_RECHAZADAS;
    }

    public String convert(List<String> lines, String output) throws IOException {
        Map<Integer, List<String>> rows = new HashMap<>();
        boolean beginBlock = false;
        List<String> row = null;
        for(String line : lines) {
            if (line.startsWith("  --") ) {
                beginBlock = true;
            } else if (line.startsWith("X") && beginBlock) {
                beginBlock = false;
            } else if (beginBlock) {
                row = getRow(line, row, rows);
            }
        }
        FileWriter writer = FileWriter.CSVWriter;
        return writer.write(output, rows);
    }

    private List<String> getRow(String line, List<String> row, Map<Integer, List<String>> rows) {
        if (row == null) {
            row = new ArrayList<>();
            int init, end = 1;
            for (int size : sizes) {
                init = end; // +1 to skip the space after the previous column
                end += size; // +1 to account for the space after the column value
                if (end > line.length()) {
                    end = line.length();
                }
                String columnValue = line.substring(init, end).replaceAll(",", "");
                row.add(columnValue);
            }
        } else {
            row.add(line.substring(35, line.length()-1));
            rows.put(rows.size() + 1, row);
            row = null; // Reset for the next row
        }
        return row;
    }
}
