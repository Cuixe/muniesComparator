package com.example.muniescomparator.format;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.*;

@Slf4j
public class UVRAAceptadasFormatter extends AbstractLinesFormater{

    @Override
    public FileFormat getFormat() {
        return FileFormat.UVRA_ACEPTADAS;
    }

    private List<Integer> sizes = Arrays.asList(8,6,7,5,6,13,4,7,12,5,17,23,20);

    public String convert(List<String> lines, String output) throws IOException {
        log.debug("Converting file ");
        boolean beginBlock = false;
        Map<Integer, List<String>> rows = new HashMap<>();
        int index = 1;
        for(String line : lines) {
            if (line.startsWith(" --") ) {
                beginBlock = true;
            } else if (line.startsWith("X") && beginBlock) {
                beginBlock = false;
            } else if (beginBlock) {
                List<String> row = getRow(line);
                rows.put(index, row);
            }
            index++;
        }

        return SimpleFileWriterUtils.writeCsv(output, rows);
    }

    private List<String> getRow(String line) {
        List<String> row = new ArrayList<>();
        int init, end = 0;
        for (int size : sizes) {
            init = end; // +1 to skip the space after the previous column
            end += size; // +1 to account for the space after the column value
            if (end > line.length()) {
                end = line.length();
            }
            String columnValue = line.substring(init, end).replaceAll(",", "");
            row.add(columnValue);
        }
        return row;
    }

}
