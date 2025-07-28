package com.example.muniescomparator.file;

import lombok.extern.slf4j.Slf4j;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@Slf4j
public class CvsFileWriter implements FileWriter {

    @Override
    public String write(String fileName, Map<Integer, List<String>> rows) throws IOException {
        fileName += ".csv";
        Path path = Paths.get(fileName);
        log.debug("Writing file: {}", fileName);
        boolean eliminado = Files.deleteIfExists(path);
        log.debug("Se eliminió el archivo {}?  {}", fileName, eliminado);
        try (FileOutputStream fos = new FileOutputStream("/tmp/" + fileName)) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<Integer, List<String>> record : rows.entrySet()) {
                List<String> values = record.getValue();
                for (int j = 0; j < values.size(); j++) {
                    sb.append(values.get(j));
                    if (j < values.size() - 1) {
                        sb.append(",");
                    }
                }
                sb.append("\n");
            }
            fos.write(sb.toString().getBytes());
            fos.close();
            log.debug("File: {} has been written", fileName);
        }
        return fileName;
    }
}
