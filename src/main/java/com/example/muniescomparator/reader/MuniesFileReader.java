package com.example.muniescomparator.reader;

import com.example.muniescomparator.vo.FileRecords;

import java.io.IOException;
import java.io.InputStream;

public interface MuniesFileReader {

    FileRecords readFile(InputStream file) throws IOException;
}
