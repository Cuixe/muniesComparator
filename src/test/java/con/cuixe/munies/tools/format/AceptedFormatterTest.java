package con.cuixe.munies.tools.format;

import con.cuixe.munies.tools.formater.domain.AceptedFormatter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AceptedFormatterTest {

    @Test
    public void testFormat() {
        AceptedFormatter formatter = new AceptedFormatter( (outputFile, rows) -> {
            assertEquals(19, rows.size());
            return "";
        });
        String inputFile = "src/test/resources/formater/acepted.txt";

        try {
            formatter.format(inputFile, "");
        } catch (Exception e) {
            fail("Formatting failed with exception: " + e.getMessage());
        }
    }

}