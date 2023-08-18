package com.example.muniescomparator;

import com.example.muniescomparator.vo.CsvFileSheet;
import com.example.muniescomparator.vo.Fields;
import com.example.muniescomparator.vo.FileSheet;
import com.example.muniescomparator.vo.XslxFileSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class MuniesComparator {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadController.class);

    public Map<String,FileSheet> compare(XslxFileSheet xslxFileSheet, CsvFileSheet csvFileSheet) {
        FileSheet equalsFileSheet = new FileSheet();
        FileSheet diffFileSheet = new FileSheet();
        int equals = 0;
        int diff = 0;
        for(Fields xslxFields : xslxFileSheet.getFieldsList()) {
            boolean found = false;
            for(Fields csvfields : csvFileSheet.getFieldsList()) {
                if (xslxFields.equals(csvfields)) {
                    equalsFileSheet.fieldsList.add(xslxFields);
                    found = true;
                    equals++;
                }
            }
            if (!found) {
                diffFileSheet.fieldsList.add(xslxFields);
                diff++;
            }
        }

        Map<String, FileSheet> files = new HashMap<>();
        files.put("equals", equalsFileSheet);
        files.put("diff", diffFileSheet);
        equalsFileSheet.setHeaders(xslxFileSheet.getHeaders());
        diffFileSheet.setHeaders(xslxFileSheet.getHeaders());
        LOGGER.info("Equals records: " + equals);
        LOGGER.info("Diff records: " + diff);
        return files;
    }
}
