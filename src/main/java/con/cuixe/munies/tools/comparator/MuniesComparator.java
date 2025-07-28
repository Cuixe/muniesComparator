package con.cuixe.munies.tools.comparator;

import con.cuixe.munies.tools.vo.ComparatorResult;
import con.cuixe.munies.tools.vo.CsvFileRecords;
import con.cuixe.munies.tools.vo.XslxFileRecords;

public interface MuniesComparator {

    ComparatorResult compare(XslxFileRecords xslxFileSheet, CsvFileRecords csvFileSheet);

    String getComparatorName();
}
