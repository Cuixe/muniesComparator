package com.example.muniescomparator;

import com.example.muniescomparator.comparator.EqualsMuniesComparator;
import com.example.muniescomparator.comparator.MuniesComparator;
import com.example.muniescomparator.controller.ComparatorController;
import com.example.muniescomparator.controller.ComparatorResponse;
import com.example.muniescomparator.reader.CsvReader;
import com.example.muniescomparator.reader.XslxReader;
import com.example.muniescomparator.uitls.FileUtils;
import com.example.muniescomparator.vo.ComparatorResult;
import com.example.muniescomparator.vo.CsvFileRecords;
import com.example.muniescomparator.vo.XslxFileRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class MuniesProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MuniesProcessor.class);

    private CsvReader csvReader = new CsvReader();
    private XslxReader xslxReader = new XslxReader();

    public ComparatorResponse processFiles(MultipartFile xslx, MultipartFile csv, MuniesComparator comparator) throws IOException {
        InputStream xslxInputStream = xslx.getInputStream();
        InputStream csvInputStream = csv.getInputStream();

        LOGGER.info("Processing file: " + StringUtils.cleanPath(csv.getOriginalFilename()));
        CsvFileRecords csvFileRecords = csvReader.readFile(csvInputStream);

        LOGGER.info("Processing file: " + StringUtils.cleanPath(xslx.getOriginalFilename()));
        XslxFileRecords xslxFileRecords = xslxReader.readFile(xslxInputStream);

        ComparatorResult result = comparator.compare(xslxFileRecords, csvFileRecords);

        String equalsFileName = FileUtils.createFile(result.getEqualFileRecords(), "equals_"+comparator.getComparatorName());
        String diffFileName = FileUtils.createFile(result.getDiffFileRecords(), "diff_"+comparator.getComparatorName());
        return new ComparatorResponse(result.getEquals(),result.getDiff(), equalsFileName, diffFileName);
    }
}
