package gameoflife;

public class Matrix {

    private final int height;
    private final int width;
    private final CellType[][] cells;

    @SuppressWarnings("ManualArrayCopy")
    public Matrix(int[][] cells) {
        height = cells.length;
        width = cells[0].length;

        this.cells = new CellType[height + 2][width + 2];

        // set all to dead
        for (int y = 0; y < height + 2; y++) {
            for (int x = 0; x < width + 2; x++) {
                this.cells[y][x] = CellType.DEAD;
            }
        }

        // set all except margin to whatever was passed in
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                this.cells[y + 1][x + 1] = CellType.valueOf(cells[y][x]);
            }
        }
    }


    public void set(Cell cell) {
        cells[cell.y() + 1][cell.x() + 1] = cell.type();
    }

    public CellType get(int y, int x) {
        return cells[y + 1][x + 1];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
