package com.example.muniescomparator.vo;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class Fields {

    private LocalDate fecha;
    private String depositos;
    private String retiros;
    private List<String> row;



}
