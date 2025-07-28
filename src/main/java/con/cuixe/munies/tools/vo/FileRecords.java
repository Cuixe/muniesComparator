package con.cuixe.munies.tools.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class FileRecords {

    public String name;

    public Map<Integer, String> headers;

    public List<Record> recordList = new ArrayList<>();

}
