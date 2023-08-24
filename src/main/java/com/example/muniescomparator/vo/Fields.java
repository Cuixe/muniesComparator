package com.example.muniescomparator.vo;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
public class Fields {
    private int index;
    private LocalDate fecha;
    private Double depositos;
    private Double retiros;
    private List<String> row = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fields fields = (Fields) o;
        return Objects.equals(fecha, fields.fecha) && Objects.equals(depositos, fields.depositos) && Objects.equals(retiros, fields.retiros);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fecha, depositos, retiros, row);
    }
}
