package com.example.muniescomparator.format;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UVRAAceptadasFormatterTest {

    //@Test
    void convert() throws Exception {

        UVRAAceptadasFormatter converter = new UVRAAceptadasFormatter();
        String inputFile = "/Users/alverik/Downloads/UVRA0274_ACEPTADAS_MN_CITI_24022025.TXT";
        String outputFile = "/Users/alverik/Downloads/UVRA0274_ACEPTADAS_MN_CITI_24022025.csv";

        // Ensure the output file is empty before conversion
        java.nio.file.Files.deleteIfExists(java.nio.file.Paths.get(outputFile));

        converter.format(inputFile, outputFile);

        // Check if the output file exists and is not empty
        assertTrue(java.nio.file.Files.exists(java.nio.file.Paths.get(outputFile)));
        assertTrue(java.nio.file.Files.size(java.nio.file.Paths.get(outputFile)) > 0);

    }

}