package con.cuixe.munies.tools.format;

import con.cuixe.munies.tools.formater.domain.RejectedFormater;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class RejectedFormaterTest {

    @Test
    public void testFormat() {
        RejectedFormater formatter = new RejectedFormater((outputFile, rows) -> {
            assertEquals(78, rows.size());
            Collection<List<String>> records = rows.values();
            List<String> firstRow = records.stream().findFirst().orElseThrow();
            assertEquals("1526642412.02", firstRow.get(11).trim());
            return "";
        });

        String inputFile = "src/test/resources/formater/rejected.txt";

        try {
            formatter.format(inputFile, "");
        } catch (Exception e) {
            fail("Formatting failed with exception: " + e.getMessage());
        }
    }
}
