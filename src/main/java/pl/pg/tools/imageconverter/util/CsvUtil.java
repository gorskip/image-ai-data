package pl.pg.tools.imageconverter.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import pl.pg.tools.imageconverter.exception.CannotReadCsv;
import pl.pg.tools.imageconverter.exception.CannotReadFile;
import pl.pg.tools.imageconverter.exception.CannotWriteCsv;

import java.awt.*;
import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class CsvUtil {

    public static void write(Path path, String[][] matrix) {
        List<String[]> data = Arrays.asList(matrix);
        FileWriter out = buildFileWriter(path);
        try {
            CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT);
            log.info("Writing csv: ".concat(path.toString()));
            printer.printRecords(data);
        } catch (IOException e) {
            throw new CannotWriteCsv(path.toString(), e);
        }
    }

    private static FileWriter buildFileWriter(Path path) {
        try {
            return new FileWriter(path.toFile());
        } catch (IOException e) {
            throw new CannotReadFile(path.toString(), e);
        }
    }

    private static FileReader buildFileReader(Path path) {
        try {
            return new FileReader(path.toString());
        } catch (FileNotFoundException e) {
            throw new CannotReadFile(path.toString(), e);
        }
    }

    public static String[][] read(Path path) {
        log.info("Reading csv: ".concat(path.toString()));
        List<List<String>> rawMatrix = new ArrayList<>();
        try {
            List<CSVRecord> records = CSVFormat.DEFAULT.parse(buildFileReader(path)).getRecords();
            for (CSVRecord record : records) {
                List<String> row = new ArrayList<>();
                for(int i = 0; i < record.getRecordNumber()-1; i++) {
                    row.add(record.get(i));
                }
                rawMatrix.add(row);
            }
        } catch (IOException e) {
            throw new CannotReadCsv(path.toString(), e);
        }
        int width = rawMatrix.size();
        int height = rawMatrix.get(0).size();
        String[][] matrix = new String[width][height];
        List<String[]> rows = rawMatrix.stream().map(array -> array.toArray(new String[0])).collect(Collectors.toList());
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                matrix[x][y] = rows.get(y)[x];
            }
        }
        return matrix;
    }

}
