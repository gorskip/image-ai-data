package pl.pg.tools.imageconverter.output;

public class ConsoleOutput implements Output {

    @Override
    public void write(String[][] matrix) {
        int width = matrix.length;
        int height = matrix[0].length;
        for(int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                System.out.print(matrix[i][j] + "  ");
            }
            System.out.println();
        }
    }
}
