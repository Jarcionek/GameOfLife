package gameoflife.backend;

import java.util.Random;

public class Matrix extends TwoDimensionalArray<CellType> {

    private static final Random RANDOM = new Random();

    public static Matrix emptyMatrix(int height, int width) {
        return new Matrix(height, width, CellType.DEAD);
    }

    public static Matrix randomMatrix(int height, int width) {
        Matrix matrix = new Matrix(height, width, CellType.DEAD);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (RANDOM.nextInt(3) > 1) {
                    switch (RANDOM.nextInt(2)) {
                        case 0: matrix.set(y, x, CellType.RED); break;
                        case 1: matrix.set(y, x, CellType.BLUE); break;
                    }
                }
            }
        }

        return matrix;
    }

    private Matrix(int height, int width, CellType defaultCell) {
        super(height, width, defaultCell);
    }

    public void set(Cell cell) {
        set(cell.y(), cell.x(), cell.type());
    }

}
