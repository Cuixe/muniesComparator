package com.example.muniescomparator.reader;

import com.example.muniescomparator.vo.Fields;
import com.example.muniescomparator.vo.XslxFileSheet;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class XslxReader implements MuniesFileReader {

    @Override
    public XslxFileSheet readFile(InputStream inputStream) throws IOException {
        Workbook workbook = new XSSFWorkbook(inputStream);

        Iterator<Sheet> iterator = workbook.iterator();
        XslxFileSheet fileSheet = null;
        while (iterator.hasNext()) {
            Sheet sheet = iterator.next();
            if (!sheet.getSheetName().equals("GHD-431-Bnt")) {
                continue;
            }
            fileSheet = new XslxFileSheet();
            fileSheet.setName(sheet.getSheetName());
            for (Row row : sheet) {
                if (fileSheet.getHeaders() == null) {
                    fileSheet.setHeaders(getHeaders(row));
                } else {
                    Fields fields = new Fields();
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

                            fields.setFecha(localDate);
                        } else if (columName.equals("DEPÓSITOS")) {
                            if(cell.getCellType() == CellType.NUMERIC) {
                                fields.setDepositos(cell.getNumericCellValue());
                            } else {
                                fields.setDepositos(0D);
                            }
                        } else if (columName.equals("RETIROS")) {
                            if(cell.getCellType() == CellType.NUMERIC) {
                                fields.setRetiros(cell.getNumericCellValue());
                            } else {
                                fields.setRetiros(0D);
                            }

                        }
                        cell.setCellType(CellType.STRING);
                        fields.getRow().add(cell.getStringCellValue());
                    }
                    fileSheet.getFieldsList().add(fields);
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
            headers.put(index, cell.getStringCellValue());
        }
        return headers;
    }
}
