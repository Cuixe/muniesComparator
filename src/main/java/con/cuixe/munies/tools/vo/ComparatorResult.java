package con.cuixe.munies.tools.vo;

import lombok.Data;

@Data
public class ComparatorResult {

    private int equals = 0;
    private int diff = 0;
    private FileRecords equalFileRecords;
    private FileRecords diffFileRecords;

}
