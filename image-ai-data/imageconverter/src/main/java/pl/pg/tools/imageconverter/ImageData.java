package pl.pg.tools.imageconverter;

import lombok.extern.slf4j.Slf4j;
import pl.pg.tools.imageconverter.model.Rgb;
import pl.pg.tools.imageconverter.util.ImageUtil;

import java.awt.image.BufferedImage;
import java.nio.file.Path;

@Slf4j
public class ImageData {

    private final Path path;
    private final BufferedImage originalImage;
    private BufferedImage image;

    public ImageData(Path path) {
        this.path = path;
        this.originalImage = ImageUtil.toBufferedImage(path);
        this.image = originalImage;

    }

    public ImageData compress(float quality) {
        this.image = ImageUtil.toCompressedBufferedImage(this.image, quality);
        return this;
    }

    public ImageData toGreyScale(boolean greyscale) {
        if(greyscale) {

            this.image = ImageUtil.toGreyScale(this.image);
        }
        return this;
    }

    public String[][] toMatrix() {
        return toMatrix(this.image);
    }

    public String[][] originalToMatrix() {
        return toMatrix(this.originalImage);
    }

    private String[][] toMatrix(BufferedImage bufferedImage) {
        log.info("Building matrix...");
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        String[][] pixels = new String[width][height];
        for(int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                pixels[i][j] = readPixelColor(bufferedImage, i, j).getHex();
            }
        }
        return pixels;
    }

    private Rgb readPixelColor(BufferedImage image, int x, int y) {
        int pixel = image.getRGB(x,y);
        int  red = (pixel & 0x00ff0000) >> 16;
        int  green = (pixel & 0x0000ff00) >> 8;
        int  blue = pixel & 0x000000ff;
        return new Rgb(red, green, blue);
    }

}
