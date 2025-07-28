package con.cuixe.munies.tools.file;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface FileWriter {

    String write(String outputFile, Map<Integer, List<String>> rows) throws IOException;
}
