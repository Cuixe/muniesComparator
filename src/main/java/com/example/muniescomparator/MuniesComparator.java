package com.example.muniescomparator;

import com.example.muniescomparator.vo.CsvFileSheet;
import com.example.muniescomparator.vo.Fields;
import com.example.muniescomparator.vo.FileSheet;
import com.example.muniescomparator.vo.XslxFileSheet;

public class MuniesComparator {

    public FileSheet compare(XslxFileSheet xslxFileSheet, CsvFileSheet csvFileSheet) {
        FileSheet fileSheet = new FileSheet();
        for(Fields xslxFields : xslxFileSheet.getFieldsList()) {
            for(Fields csvfields : csvFileSheet.getFieldsList()) {
                if (xslxFields.equals(csvfields)) {
                    fileSheet.fieldsList.add(xslxFields);
                }
            }
        }
        fileSheet.setHeaders(xslxFileSheet.getHeaders());
        return fileSheet;
    }
}
