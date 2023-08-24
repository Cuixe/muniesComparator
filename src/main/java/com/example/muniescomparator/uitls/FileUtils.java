package com.example.muniescomparator.uitls;

import com.example.muniescomparator.vo.FileRecords;
import com.example.muniescomparator.vo.Record;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {

    public static String createFile(FileRecords fileRecords, String name) throws IOException {
        String fileName = name  + System.currentTimeMillis() + ".csv";
        String fullPath = "/tmp/" + fileName;

        FileWriter fileWriter = new FileWriter(fullPath);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        String header = String.join(",", fileRecords.getHeaders().values());
        bufferedWriter.write(header);
        bufferedWriter.newLine();
        for(Record record : fileRecords.getRecordList()) {
            String row = String.join(",", record.getRow());
            bufferedWriter.write(row);
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
        fileWriter.close();
        return fileName;
    }
}
