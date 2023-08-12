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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    public ResponseEntity<Resource> uploadFile(@RequestParam("xslx") MultipartFile xslx, @RequestParam("csv") MultipartFile csv, RedirectAttributes attributes) {

        // normalize the file path
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(xslx.getOriginalFilename()));

        // save the file on the local file system
        try {
            InputStream xslxInputStream = xslx.getInputStream();
            InputStream csvInputStream = csv.getInputStream();

            CsvReader csvReader = new CsvReader();
            CsvFileSheet csvFileSheet = csvReader.readFile(csvInputStream);
            XslxReader xslxReader = new XslxReader();
            XslxFileSheet xslxFileSheet = xslxReader.readFile(xslxInputStream);

            MuniesComparator comparator = new MuniesComparator();
            FileSheet fileSheet = comparator.compare(xslxFileSheet, csvFileSheet);

            String resultFileName = "/tmp/restult" + System.currentTimeMillis() + ".csv";

            FileWriter fileWriter = new FileWriter(resultFileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            String header = String.join(",",fileSheet.getHeaders().values());
            bufferedWriter.write(header);
            bufferedWriter.newLine();
            for(Fields fields : fileSheet.getFieldsList()) {
                String row = String.join(",",fields.getRow());
                bufferedWriter.write(row);
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
            fileWriter.close();

            Path file = root.resolve(resultFileName);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {

                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
            } else {
                throw new RuntimeException("Could not read the file!");
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not read the file!");
        }

    }

}