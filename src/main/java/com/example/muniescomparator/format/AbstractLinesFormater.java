package com.example.muniescomparator.format;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.nio.charset.StandardCharsets.ISO_8859_1;

@Slf4j
public abstract class AbstractLinesFormater implements Formater {

    @Override
    public String format(String input, String output) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(input),  ISO_8859_1);
        return process(lines, output);
    }

    @Override
    public String format(InputStream input, String output) throws Exception {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return process(lines, output);
    }

    private String process(List<String> lines, String output) throws Exception {
        log.debug("{} Lines to format:", lines.size());
        return convert(lines, output);
    }

    public abstract String convert(List<String> input, String output) throws Exception;
}
