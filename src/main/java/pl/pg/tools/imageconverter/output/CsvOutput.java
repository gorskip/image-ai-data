package pl.pg.tools.imageconverter.output;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import pl.pg.tools.imageconverter.exception.CannotReadFile;
import pl.pg.tools.imageconverter.exception.CannotWriteCsv;
import pl.pg.tools.imageconverter.util.CsvUtil;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class CsvOutput implements Output {

    private final Path path;

    public CsvOutput(Path path) {
        this.path = path;
    }

    @Override
    public void write(String[][] matrix) {
        CsvUtil.write(path, matrix);
    }
}
