package com.example.muniescomparator.format;

import com.example.muniescomparator.file.FileWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PositionalFormater extends AbstractLinesFormater{

    private final List<Integer> positions;
    private final FileWriter writer;

    public PositionalFormater(String positions, FileWriter writer) {
        super(writer);
        this.positions = new ArrayList<>();
        this.writer = writer;
        for (String pos : positions.split(",")) {
            this.positions.add(Integer.parseInt(pos.trim()));
        }
    }

    public PositionalFormater(List<Integer> positions, FileWriter writer) {
        super(writer);
        this.positions = positions;
        this.writer = writer;
    }

    @Override
    public Map<Integer, List<String>> getFormatedLines(List<String> input) throws Exception {
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
        return rows;
    }

    @Override
    public FileFormat getFormat() {
        return FileFormat.POSITIONS;
    }
}
