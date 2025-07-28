package con.cuixe.munies.tools.reader;

import con.cuixe.munies.tools.vo.Record;
import con.cuixe.munies.tools.vo.XslxFileRecords;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class XslxReader implements MuniesFileReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(XslxReader.class);

    @Override
    public XslxFileRecords readFile(InputStream inputStream) throws IOException {
        Workbook workbook = new XSSFWorkbook(inputStream);

        Iterator<Sheet> iterator = workbook.iterator();
        XslxFileRecords fileSheet = null;
        while (iterator.hasNext()) {
            Sheet sheet = iterator.next();
            fileSheet = new XslxFileRecords();
            fileSheet.setName(sheet.getSheetName());
            for (Row row : sheet) {
                if (fileSheet.getHeaders() == null) {
                    fileSheet.setHeaders(getHeaders(row));
                } else {
                    Record record = new Record();
                    record.setIndex(row.getRowNum());
                    for (Cell cell : row) {
                        int index = cell.getColumnIndex();
                        String columName = fileSheet.getHeaders().get(index);
                        if (columName == null) {
                            continue;
                        }
                        if (columName.equals("FECHA")) {
                            Date date = cell.getDateCellValue();
                            LocalDate localDate = date.toInstant()
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate();

                            record.setFecha(localDate);
                        } else if (columName.equals("DEPÓSITOS")) {
                            if(cell.getCellType() == CellType.NUMERIC) {
                                record.setDepositos(cell.getNumericCellValue());
                            } else {
                                record.setDepositos(0D);
                            }
                        } else if (columName.equals("RETIROS")) {
                            if(cell.getCellType() == CellType.NUMERIC) {
                                record.setRetiros(cell.getNumericCellValue());
                            } else {
                                record.setRetiros(0D);
                            }

                        }
                        cell.setCellType(CellType.STRING);
                        record.getRow().add(cell.getStringCellValue());
                    }
                    fileSheet.getRecordList().add(record);
                }
            }
        }
        return fileSheet;
    }

    private Map<Integer, String> getHeaders(Row row) {
        Map<Integer, String> headers;
        headers = new HashMap<>();
        for (Cell cell : row) {
            int index = cell.getColumnIndex();
            LOGGER.info("Header: " + index + "-" + cell.getStringCellValue());
            headers.put(index, cell.getStringCellValue());
        }
        return headers;
    }
}
