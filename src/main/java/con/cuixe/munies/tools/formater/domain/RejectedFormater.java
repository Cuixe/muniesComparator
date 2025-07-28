package con.cuixe.munies.tools.formater.domain;

import con.cuixe.munies.tools.file.FileWriter;
import con.cuixe.munies.tools.formater.FileFormat;

import java.util.*;

public class RejectedFormater extends AbstractLinesFormater {

    private List<Integer> sizes = Arrays.asList(8,6,8,5,6,13,4,7,6,6,18,21,20,4);

    public RejectedFormater(FileWriter fileWriter) {
        super(fileWriter);
    }

    @Override
    public FileFormat getFormat() {
        return FileFormat.UVRA_RECHAZADAS;
    }

    public Map<Integer, List<String>> getFormatedLines(List<String> input) throws Exception {
        Map<Integer, List<String>> rows = new HashMap<>();
        boolean beginBlock = false;
        List<String> row = null;
        for(String line : input) {
            if (line.startsWith("  --") ) {
                beginBlock = true;
            } else if (line.startsWith("X") && beginBlock) {
                beginBlock = false;
            } else if (beginBlock) {
                row = getRow(line, row, rows);
            }
        }
        return rows;
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
