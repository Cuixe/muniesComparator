package con.cuixe.munies.tools.comparator;

import con.cuixe.munies.tools.vo.Record;

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
