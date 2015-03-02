package gameoflife.backend;

public class Matrix extends TwoDimensionalArray<CellType> {

    public Matrix(int[][] cells) {
        super(cells.length, cells[0].length, CellType.DEAD);

        for (int y = 0; y < height(); y++) {
            for (int x = 0; x < width(); x++) {
                set(y, x, CellType.valueOf(cells[y][x]));
            }
        }
    }

    public void set(Cell cell) {
        set(cell.y(), cell.x(), cell.type());
    }

}
