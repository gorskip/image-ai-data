package pl.pg.tools.imageconverter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.pg.tools.imageconverter.util.CsvUtil;
import pl.pg.tools.imageconverter.util.ImageUtil;

import java.nio.file.Path;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Value("${input.image}")
    private Path inputImage;

    @Value("${output.csv}")
    private Path outputCsv;

    @Value("${output.image}")
    private Path outputImage;

    @Value("${quality}")
    float quality;

    @Value("${greyscale}")
    boolean greyscale;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    public void run(String... args) {


        CsvUtil.write(
                outputCsv,
                new ImageData(inputImage)
                        .compress(quality)
                        .toGreyScale(greyscale)
                        .toMatrix()
        );

        ImageUtil.write(
                outputImage,
                ImageUtil.buildImage(
                        new ImageData(inputImage)
                                .compress(quality)
                                .toGreyScale(greyscale)
                                .toMatrix()
                )
        );
    }
}
