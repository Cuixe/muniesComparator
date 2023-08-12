package com.example.muniescomparator.reader;

import com.example.muniescomparator.vo.FileSheet;

import java.io.IOException;

public interface FileReader {

    FileSheet readFile(String file) throws IOException;
}
