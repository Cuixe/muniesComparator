package com.example.muniescomparator.format;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PositionalFormaterTest {

    @Test
    public void testFormat() {
        String positions = "5,5,10,15,7,3";

        PositionalFormater formatter = new PositionalFormater(positions,(outputFile, rows) -> {
            Collection<List<String>> records = rows.values();
            List<String> firstRow = records.stream().findFirst().orElseThrow();
            assertEquals("12345", firstRow.get(0));
            assertEquals("67890", firstRow.get(1));
            assertEquals("1234567890", firstRow.get(2));
            assertEquals("123456789012345", firstRow.get(3));
            assertEquals("6789012", firstRow.get(4));
            assertEquals("345", firstRow.get(5));
            return "";
        });
        String inputFile = "src/test/resources/formater/positionalFile.txt";

        try {
            formatter.format(inputFile, "");
        } catch (Exception e) {
            fail("Formatting failed with exception: " + e.getMessage());
        }
    }

}