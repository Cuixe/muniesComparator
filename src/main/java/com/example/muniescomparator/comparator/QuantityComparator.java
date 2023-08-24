package com.example.muniescomparator.comparator;

import com.example.muniescomparator.vo.Record;

public class QuantityComparator extends AbstractComparator implements MuniesComparator {

    public String getComparatorName() {
        return "Monto";
    }

    @Override
    public boolean recordsAreEquals(Record record1, Record record2) {
        return record1.getDepositos().equals(record2.getDepositos()) && record1.getRetiros().equals(record2.getRetiros());
    }
}
