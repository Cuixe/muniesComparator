package com.example.muniescomparator.reader;

import com.example.muniescomparator.vo.FileSheet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public interface MuniesFileReader {

    FileSheet readFile(InputStream file) throws IOException;
}
