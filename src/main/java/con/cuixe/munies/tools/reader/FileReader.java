package con.cuixe.munies.tools.reader;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileReader {
    public void main(String[] args) throws Exception {
        List<String> lineas = Files.readAllLines(Paths.get("ruta/del/archivo.txt"));
        for (String linea : lineas) {
            System.out.println(linea);
        }
    }
}
