package com.example.muniescomparator.format;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PositionsFormater extends AbstractLinesFormater{

    private final List<Integer> positions;

    public PositionsFormater(String positions) {
        this.positions = new ArrayList<>();
        for (String pos : positions.split(",")) {
            this.positions.add(Integer.parseInt(pos.trim()));
        }
    }

    public PositionsFormater(List<Integer> positions) {
        this.positions = positions;
    }

    @Override
    public String convert(List<String> input, String output) throws Exception {
        Map<Integer, List<String>> rows = new HashMap<>();
        for(String line : input) {
            int init, end = 0;
            List<String> row = new ArrayList<>();
            for (int size : positions) {
                init = end;
                end += size;
                if (end > line.length()) {
                    end = line.length();
                }
                String columnValue = line.substring(init, end).replaceAll(",", "");
                row.add(columnValue);
            }
            rows.put(rows.size(), row);
        }
        return SimpleFileWriterUtils.writeCsv(output, rows);
    }

    @Override
    public FileFormat getFormat() {
        return FileFormat.POSITIONS;
    }
}
