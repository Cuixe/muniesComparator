package com.example.muniescomparator.controller;

import com.example.muniescomparator.file.CvsFileWriter;
import com.example.muniescomparator.file.FileWriter;
import com.example.muniescomparator.format.*;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/formater")
public class FormateadorController {

    @GetMapping("/")
    public String formatoNuevo(Model model) {
        model.addAttribute("formats", FileFormat.values());
        return "formatoNuevo";
    }

    @PostMapping("/upload")
    public ResponseEntity<Resource> uploadFile(Model model, @RequestParam("file") MultipartFile file, @RequestParam String format,
                                               @RequestParam String fileName, @RequestParam String positions) throws Exception {

        FileFormat fileFormat = FileFormat.valueOf(format.toUpperCase());
        InputStream stream = file.getInputStream();
        fileName = fileName.substring(0, fileName.lastIndexOf('.'));
        String output =fileName;
        String convertedFile = null;
        FileWriter cvsFileWriter = new CvsFileWriter();
        switch (fileFormat) {
            case UVRA_ACEPTADAS:
                convertedFile = new AceptedFormatter(cvsFileWriter).format(stream, output);
                break;
            case UVRA_RECHAZADAS:
                convertedFile = new RejectedFormater(cvsFileWriter).format(stream, output);
                break;
            case S274:
                convertedFile = new S274Formater(cvsFileWriter).format(stream, output);
                break;
            case POSITIONS:
                convertedFile = new PositionalFormater(positions, cvsFileWriter).format(stream, output);
                break;
        }
        Path path = Paths.get("/tmp/"+ convertedFile );
        Resource resource = new InputStreamResource(Files.newInputStream(path));
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"" + path.getFileName() + "\"")
                .body(resource);
    }
}
