package pl.pg.tools.imageconverter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.pg.tools.imageconverter.util.CsvUtil;
import pl.pg.tools.imageconverter.util.ImageUtil;

import java.awt.*;
import java.nio.file.Path;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Value("${input.image.orig}")
    private Path inputImageOrig;

    @Value("${output.image.greyscale}")
    private Path outputImageGreyscale;

    @Value("${output.image.low}")
    private Path outputImageLow;

    @Value("${output.image.greyscale.low}")
    private Path outputImageGreyscaleLow;

    @Value("${output.csv.orig}")
    private Path outputCsvOrig;

    @Value("${output.csv.greyscale}")
    private Path outputCsvGreyscale;

    @Value("${output.csv.low}")
    private Path outputCsvLow;

    @Value("${output.csv.greyscale.low}")
    private Path outputCsvGreyscaleLow;

    @Value("${output.image.from.matrix}")
    private Path outputImageFromMaTrix;

    @Value("${quality}")
    float quality;

    @Value("${greyscale}")
    boolean greyscale;



    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    public void run(String... args) {

        //origimage to csv
        CsvUtil.write(
                outputCsvOrig,
                new ImageData(inputImageOrig)
                        .toGreyScale(false)
                        .toMatrix()
        );

        //origimage to csv greyscale
        CsvUtil.write(
                outputCsvGreyscale,
                new ImageData(inputImageOrig)
                    .toGreyScale(true)
                    .toMatrix()
        );

        //origimage to csv low res
        CsvUtil.write(
                outputCsvLow,
                new ImageData(inputImageOrig)
                        .compress(quality)
                        .toGreyScale(false)
                        .toMatrix()
        );
//
        //origimage to csv greyscale low res
        CsvUtil.write(
                outputCsvGreyscaleLow,
                new ImageData(inputImageOrig)
                        .compress(quality)
                        .toGreyScale(true)
                        .toMatrix()
        );


        //origimage to greyscale
        ImageUtil.write(
                outputImageGreyscale,
                ImageUtil.buildImage(
                    new ImageData(inputImageOrig)
                        .toGreyScale(true)
                        .toMatrix())
        );

        //origimage to low
        ImageUtil.write(
                outputImageLow,
                ImageUtil.buildImage(
                        new ImageData(inputImageOrig)
                                .toGreyScale(false)
                                .compress(quality)
                                .toMatrix())
        );

        //orig image to greyscale low
        ImageUtil.write(
                outputImageGreyscaleLow,
                ImageUtil.buildImage(
                        new ImageData(inputImageOrig)
                                .toGreyScale(true)
                                .compress(quality)
                                .toMatrix())
        );

        String[][] greyscaleLowMatrix = CsvUtil.read(outputCsvGreyscaleLow);


        ImageUtil.createImage(greyscaleLowMatrix, outputImageFromMaTrix);
    }
}
