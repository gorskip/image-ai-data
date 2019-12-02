package pl.pg.tools.imageconverter.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import pl.pg.tools.imageconverter.exception.*;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Path;

@Slf4j
@UtilityClass
public class ImageUtil {

    public static BufferedImage toBufferedImage(Path path) {
        return toBufferedImage(path.toFile());
    }

    public static BufferedImage toBufferedImage(File file) {
        try {
            return ImageIO.read(file);
        } catch (IOException e) {
            throw new CannotReadImageFile(file.getAbsolutePath(), e);
        }
    }

    public static BufferedImage toGreyScale(BufferedImage colorImage) {
        log.info("Converting to grey scale...");
        BufferedImage image = new BufferedImage(colorImage.getWidth(), colorImage.getHeight(),
                BufferedImage.TYPE_BYTE_GRAY);
        Graphics g = image.getGraphics();
        g.drawImage(colorImage, 0, 0, null);
        g.dispose();
        return image;
    }

    public static BufferedImage toCompressedBufferedImage(BufferedImage image, float quality) {
        log.info("Compressing...");
        try {
            byte[] bytes = toCompressedByteArray(image, quality);
            return toBufferedImage(bytes);
        } catch (Exception e) {
            throw new CannotCompressImage(e);
        }
    }

    public static byte[] toCompressedByteArray(BufferedImage image, float quality) throws IOException {
        ByteArrayOutputStream compressed = new ByteArrayOutputStream();
        ImageOutputStream outputStream = ImageIO.createImageOutputStream(compressed);
        ImageWriter jpgWriter = ImageIO.getImageWritersByFormatName("jpg").next();

        ImageWriteParam jpgWriteParam = jpgWriter.getDefaultWriteParam();
        jpgWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        jpgWriteParam.setCompressionQuality(quality);
        jpgWriter.setOutput(outputStream);

        jpgWriter.write(null, new IIOImage(image, null, null), jpgWriteParam);
        jpgWriter.dispose();
        return compressed.toByteArray();
    }

    public static BufferedImage toBufferedImage(byte[] imageInByte) {
        try {
            return ImageIO.read(new ByteArrayInputStream(imageInByte));
        } catch (IOException e) {
            throw new CannotReadImageBytes(e);
        }
    }

    public static void write(Path path, BufferedImage image) {
        try {
            log.info("Writing image: ".concat(path.toString()));
            ImageIO.write(image, "jpg", path.toFile());
        } catch (IOException e) {
            throw new CannotWriteImage(path.toString(), e);
        }
    }


    public static BufferedImage buildImage(String[][] matrix) {
        int width = matrix.length;
        int height = matrix[0].length;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                image.setRGB(x, y, Color.decode(matrix[x][y]).getRGB());
            }
        }
        return image;
    }

    public static void createImage(String[][] colorsMatrix, Path path) {
        write(path, buildImage(colorsMatrix));
    }
}
