package con.cuixe.munies.tools.vo;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
public class Record {
    private int index;
    private LocalDate fecha;
    private Double depositos;
    private Double retiros;
    private List<String> row = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Record record = (Record) o;
        return Objects.equals(fecha, record.fecha) && Objects.equals(depositos, record.depositos) && Objects.equals(retiros, record.retiros);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fecha, depositos, retiros, row);
    }
}
