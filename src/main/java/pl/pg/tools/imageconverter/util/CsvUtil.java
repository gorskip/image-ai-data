package pl.pg.tools.imageconverter.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;
import pl.pg.tools.imageconverter.exception.CannotReadCsv;
import pl.pg.tools.imageconverter.exception.CannotReadFile;
import pl.pg.tools.imageconverter.exception.CannotWriteCsv;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class CsvUtil {

    public static void write(Path path, String[][] matrix) {
        List<String[]> data = Arrays.asList(matrix);
        FileWriter out = buildFileWriter(path);
        try {
            CSVPrinter printer = new CSVPrinter(out, CSVFormat.EXCEL.withSkipHeaderRecord().withIgnoreEmptyLines());
            log.info("Writing csv: ".concat(path.toString()));
            printer.printRecords(data);
            printer.flush();
        } catch (IOException e) {
            throw new CannotWriteCsv(path.toString(), e);
        }
    }

    private static void writeOnMyOwn(Path path, String[][] matrix) {


        for(int y = 0; y < matrix.length -1; y++){
            for(int x = 0; x < matrix[0].length; x++){
                try {
                    FileUtils.write(path.toFile(), matrix[x][y], "UTF-8", true);
                } catch (IOException e) {
                    throw new CannotWriteCsv(path.toString(), e);
                }
            }
        }
//        return matrix;
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
            int height =records.get(0).size();
            int width =  records.size();
            String[][] matrix = new String[width][height];
            for(int x = 0; x < width; x++) {
                CSVRecord record = records.get(x);
                for(int y = 0; y <height; y++) {
                    matrix[x][y] = record.get(y);
                }
            }

            return matrix;
        } catch (IOException e) {
            throw new CannotReadCsv(path.toString(), e);
        }
    }


    private static Reader getReader(Path path) {
        try {
            return new InputStreamReader(new FileInputStream(path.toString()), "UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            throw new CannotReadFile(path.toString(), e);
        }
    }

}
