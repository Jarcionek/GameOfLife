package gameoflife;

public class Matrix {

    private final CellType[][] cells;
    private final int width;
    private final int height;

    @SuppressWarnings("ManualArrayCopy")
    public Matrix(int[][] cells) {
        width = cells.length;
        height = cells[0].length;

        this.cells = new CellType[width + 2][height + 2];

        // set all to dead
        for (int y = 0; y < width + 2; y++) {
            for (int x = 0; x < height + 2; x++) {
                this.cells[y][x] = CellType.DEAD;
            }
        }

        // set all except margin to whatever was passed in
        for (int y = 0; y < width; y++) {
            for (int x = 0; x < height; x++) {
                this.cells[y + 1][x + 1] = CellType.valueOf(cells[y][x]);
            }
        }
    }


    public void set(int y, int x, int type) {
        cells[y + 1][x + 1] = CellType.valueOf(type);
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
