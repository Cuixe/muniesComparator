package com.example.muniescomparator.comparator;

import com.example.muniescomparator.vo.ComparatorResult;
import com.example.muniescomparator.vo.CsvFileRecords;
import com.example.muniescomparator.vo.XslxFileRecords;

public interface MuniesComparator {

    ComparatorResult compare(XslxFileRecords xslxFileSheet, CsvFileRecords csvFileSheet);

    String getComparatorName();
}
