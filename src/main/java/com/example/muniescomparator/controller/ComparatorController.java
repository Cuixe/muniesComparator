package com.example.muniescomparator.controller;

import com.example.muniescomparator.MuniesProcessor;
import com.example.muniescomparator.comparator.EqualsMuniesComparator;
import com.example.muniescomparator.comparator.QuantityComparator;
import com.example.muniescomparator.reader.CsvReader;
import com.example.muniescomparator.reader.XslxReader;
import com.example.muniescomparator.uitls.FileUtils;
import com.example.muniescomparator.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class ComparatorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComparatorController.class);

    @Autowired
    private MuniesProcessor processor;

    @GetMapping("/compare")
    public String homepage() {
        return "index";
    }

    @GetMapping("/")
    public String error() {
        return "error";
    }

    private final Path root = Paths.get("/tmp");

    @PostMapping("/upload")
    public String uploadFile(Model model, @RequestParam("xslx") MultipartFile xslx, @RequestParam("csv") MultipartFile csv) {
        try {
            ComparatorResponse equals = processor.processFiles(xslx, csv, new EqualsMuniesComparator());
            ComparatorResponse quantity = processor.processFiles(xslx, csv, new QuantityComparator());
            model.addAttribute("equals", equals);
            model.addAttribute("quantity", quantity);
            return "files";
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            return "error";
        }
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> download(@PathVariable("fileName") String fileName) throws MalformedURLException {
        Path file = root.resolve("/tmp/"+fileName);
        Resource resource = new UrlResource(file.toUri());
        if (resource.exists() || resource.isReadable()) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
        } else {
            throw new RuntimeException("Could not read the file!");
        }
    }

}