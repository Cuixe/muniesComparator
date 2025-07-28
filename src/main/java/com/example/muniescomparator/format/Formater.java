package com.example.muniescomparator.format;

import java.io.InputStream;

public interface Formater {

    FileFormat getFormat();

    String format(String input, String output) throws Exception;

    String format(InputStream input, String output) throws Exception;
}
