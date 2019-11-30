package pl.pg.tools.imageconverter;

import pl.pg.tools.imageconverter.util.ImageUtil;

import java.awt.image.BufferedImage;

public class MatrixData {

    private final String[][] matrix;

    public MatrixData(String[][] matrix) {
        this.matrix = matrix;
    }

    public BufferedImage createImage() {
        return ImageUtil.createImage(matrix);
    }


}
