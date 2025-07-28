package con.cuixe.munies.tools.reader;

import con.cuixe.munies.tools.vo.FileRecords;

import java.io.IOException;
import java.io.InputStream;

public interface MuniesFileReader {

    FileRecords readFile(InputStream file) throws IOException;
}
