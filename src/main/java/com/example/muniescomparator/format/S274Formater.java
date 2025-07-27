package com.example.muniescomparator.format;

import java.util.*;

public class S274Formater extends PositionsFormater {

    private static final List<Integer> POSITIONS = Arrays.asList(3,8,18,6,20,7,64,12,13);

    public S274Formater() {
        super(POSITIONS);
    }

    @Override
    public String convert(List<String> input, String output) throws Exception {
        return "";
    }

    @Override
    public FileFormat getFormat() {
        return FileFormat.S274;
    }
}
