package com.example.muniescomparator.comparator;

import com.example.muniescomparator.vo.Record;

public class EqualsMuniesComparator extends AbstractComparator implements MuniesComparator {

    @Override
    public String getComparatorName() {
        return "Fecha_monto";
    }

    @Override
    public boolean recordsAreEquals(Record record1, Record record2) {
        return record1.equals(record2);
    }

}
