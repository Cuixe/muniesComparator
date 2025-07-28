package com.example.muniescomparator.format;

import com.example.muniescomparator.file.FileWriter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class AceptedFormatter extends AbstractLinesFormater{

    public AceptedFormatter(FileWriter fileWriter) {
        super(fileWriter);
    }

    @Override
    public FileFormat getFormat() {
        return FileFormat.UVRA_ACEPTADAS;
    }

    private List<Integer> sizes = Arrays.asList(8,6,7,5,6,13,4,7,12,5,17,23,20);

    public Map<Integer, List<String>> getFormatedLines(List<String> input) throws Exception {
        log.debug("Converting file ");
        boolean beginBlock = false;
        Map<Integer, List<String>> rows = new HashMap<>();
        int index = 1;
        for(String line : input) {
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
        return rows;
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
