package com.example.muniescomparator;

import com.example.muniescomparator.vo.CsvFileSheet;
import com.example.muniescomparator.vo.Fields;
import com.example.muniescomparator.vo.FileSheet;
import com.example.muniescomparator.vo.XslxFileSheet;

import java.util.HashMap;
import java.util.Map;

public class MuniesComparator {

    public Map<String,FileSheet> compare(XslxFileSheet xslxFileSheet, CsvFileSheet csvFileSheet) {
        FileSheet equalsFileSheet = new FileSheet();
        FileSheet diffFileSheet = new FileSheet();
        for(Fields xslxFields : xslxFileSheet.getFieldsList()) {
            boolean found = false;
            for(Fields csvfields : csvFileSheet.getFieldsList()) {
                if (xslxFields.equals(csvfields)) {
                    equalsFileSheet.fieldsList.add(xslxFields);
                    found = true;
                }
            }
            if (!found) {
                diffFileSheet.fieldsList.add(xslxFields);
            }
        }

        Map<String, FileSheet> files = new HashMap<>();
        files.put("equals", equalsFileSheet);
        files.put("diff", diffFileSheet);
        equalsFileSheet.setHeaders(xslxFileSheet.getHeaders());
        diffFileSheet.setHeaders(xslxFileSheet.getHeaders());
        return files;
    }
}
