package com.example.muniescomparator.controller;

import lombok.Data;

@Data
public class ComparatorResponse {

    private int equals;
    private int diff;
    private String fileNameEquals;
    private String fileNameDiff;

    public ComparatorResponse(int equals, int diff, String fileNameEquals, String fileNameDiff) {
        this.equals = equals;
        this.diff = diff;
        this.fileNameEquals = fileNameEquals;
        this.fileNameDiff = fileNameDiff;
    }
}
