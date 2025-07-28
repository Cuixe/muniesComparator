package con.cuixe.munies.tools.format;

import con.cuixe.munies.tools.formater.domain.AceptedFormatter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AceptedFormatterTest {

    @Test
    public void testFormat() {
        AceptedFormatter formatter = new AceptedFormatter( (outputFile, rows) -> {
            assertTrue(rows.size() > 19);
            return "";
        });
        String inputFile = "formater/aceptadas.txt";

        try {
            formatter.format(inputFile, "");
        } catch (Exception e) {
            fail("Formatting failed with exception: " + e.getMessage());
        }
    }

}