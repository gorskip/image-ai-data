package pl.pg.tools.imageconverter;

import pl.pg.tools.imageconverter.util.ImageUtil;

import java.awt.image.BufferedImage;
import java.nio.file.Path;

public class MatrixData {

    private final String[][] matrix;

    public MatrixData(String[][] matrix) {
        this.matrix = matrix;
    }

    public void createImage(Path path) {
        ImageUtil.createImage(matrix, path);
    }


}
