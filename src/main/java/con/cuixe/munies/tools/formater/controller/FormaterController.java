package con.cuixe.munies.tools.formater.controller;

import con.cuixe.munies.tools.file.CvsFileWriter;
import con.cuixe.munies.tools.file.FileWriter;
import con.cuixe.munies.tools.formater.FileFormat;
import con.cuixe.munies.tools.formater.domain.AceptedFormatter;
import con.cuixe.munies.tools.formater.domain.PositionalFormater;
import con.cuixe.munies.tools.formater.domain.RejectedFormater;
import con.cuixe.munies.tools.formater.domain.S274Formater;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class FormaterController {

    @GetMapping("/")
    public String formatoNuevo(Model model) {
        model.addAttribute("formats", FileFormat.values());
        return "formatoNuevo";
    }

    @PostMapping("/upload")
    public ResponseEntity<Resource> uploadFile(Model model, @RequestParam("file") MultipartFile file, @RequestParam String format,
                                               @RequestParam String fileName, @RequestParam String positions) throws Exception {

        log.info("Iniciando formateador de archivo: {}", fileName);
        log.info("Iniciando formateador de tipo: {}", format);

        FileFormat fileFormat = FileFormat.valueOf(format.toUpperCase());
        InputStream stream = file.getInputStream();
        fileName = fileName.substring(0, fileName.lastIndexOf('.'));
        String output =fileName;
        String convertedFile = null;
        FileWriter cvsFileWriter = new CvsFileWriter();
        convertedFile = switch (fileFormat) {
            case UVRA_ACEPTADAS -> new AceptedFormatter(cvsFileWriter).format(stream, output);
            case UVRA_RECHAZADAS -> new RejectedFormater(cvsFileWriter).format(stream, output);
            case S274 -> new S274Formater(cvsFileWriter).format(stream, output);
            case POSITIONS -> new PositionalFormater(positions, cvsFileWriter).format(stream, output);
        };
        log.info("Formato completo del archivo: {}", convertedFile);
        Path path = Paths.get("/tmp/"+ convertedFile );
        Resource resource = new InputStreamResource(Files.newInputStream(path));
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"" + path.getFileName() + "\"")
                .body(resource);
    }
}
