package con.cuixe.munies.tools.comparator;

import con.cuixe.munies.tools.controller.ComparatorController;
import con.cuixe.munies.tools.vo.*;
import con.cuixe.munies.tools.vo.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractComparator implements MuniesComparator {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComparatorController.class);

    public abstract boolean recordsAreEquals(Record record1, Record record2);
    @Override
    public ComparatorResult compare(XslxFileRecords xslxFileSheet, CsvFileRecords csvFileSheet) {
        ComparatorResult result = new ComparatorResult();
        FileRecords equalsFileRecords = new FileRecords();
        FileRecords diffFileRecords = new FileRecords();
        int equals = 0;
        int diff = 0;
        List<Integer> xslxProcessedRows = new ArrayList<>();
        List<Integer> csvProcessedRows = new ArrayList<>();
        for(Record xslxRecord : xslxFileSheet.getRecordList()) {
            boolean found = false;
            for(Record csvRecord : csvFileSheet.getRecordList()) {
                if (recordsAreEquals(xslxRecord, csvRecord)  && !csvProcessedRows.contains(csvRecord.getIndex()) && !xslxProcessedRows.contains(xslxRecord.getIndex())) {
                    equalsFileRecords.recordList.add(xslxRecord);
                    csvProcessedRows.add(csvRecord.getIndex());
                    xslxProcessedRows.add(xslxRecord.getIndex());
                    found = true;
                    equals++;
                }
            }
            if (!found) {
                diffFileRecords.recordList.add(xslxRecord);
                diff++;
            }
        }
        equalsFileRecords.setHeaders(xslxFileSheet.getHeaders());
        diffFileRecords.setHeaders(xslxFileSheet.getHeaders());
        LOGGER.info("Equals records: " + equals);
        LOGGER.info("Diff records: " + diff);
        result.setDiff(diff);
        result.setEquals(equals);
        result.setDiffFileRecords(diffFileRecords);
        result.setEqualFileRecords(equalsFileRecords);
        return result;
    }
}
