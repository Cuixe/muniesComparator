package com.example.muniescomparator;

import com.example.muniescomparator.reader.CsvReader;
import com.example.muniescomparator.reader.XslxReader;
import com.example.muniescomparator.vo.CsvFileSheet;
import com.example.muniescomparator.vo.Fields;
import com.example.muniescomparator.vo.FileSheet;
import com.example.muniescomparator.vo.XslxFileSheet;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;

@Controller
public class UploadController {

    private final String UPLOAD_DIR = "./uploads/";

    @GetMapping("/")
    public String homepage() {
        return "index";
    }

    private final Path root = Paths.get("/tmp");

    @PostMapping("/upload")
    public String uploadFile(Model model, @RequestParam("xslx") MultipartFile xslx, @RequestParam("csv") MultipartFile csv) {
        // save the file on the local file system
        try {
            Map<String,FileSheet> files = getFileSheet(xslx, csv);
            String equalsFileName = createFile(files.get("equals"), "iguales").replace("/tmp/","");
            String diffFileName = createFile(files.get("diff"), "diferentes").replace("/tmp/","");
            model.addAttribute("equals", equalsFileName);
            model.addAttribute("diff", diffFileName);
            return "files";
        } catch (Exception ex) {
            ex.printStackTrace();
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

    private static String createFile(FileSheet fileSheet, String name) throws IOException {
        String resultFileName = "/tmp/" + name  + System.currentTimeMillis() + ".csv";

        FileWriter fileWriter = new FileWriter(resultFileName);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        String header = String.join(",", fileSheet.getHeaders().values());
        bufferedWriter.write(header);
        bufferedWriter.newLine();
        for(Fields fields : fileSheet.getFieldsList()) {
            String row = String.join(",",fields.getRow());
            bufferedWriter.write(row);
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
        fileWriter.close();
        return resultFileName;
    }

    private static Map<String,FileSheet> getFileSheet(MultipartFile xslx, MultipartFile csv) throws IOException {
        InputStream xslxInputStream = xslx.getInputStream();
        InputStream csvInputStream = csv.getInputStream();

        CsvReader csvReader = new CsvReader();
        CsvFileSheet csvFileSheet = csvReader.readFile(csvInputStream);
        XslxReader xslxReader = new XslxReader();
        XslxFileSheet xslxFileSheet = xslxReader.readFile(xslxInputStream);

        MuniesComparator comparator = new MuniesComparator();
        return comparator.compare(xslxFileSheet, csvFileSheet);
    }

}