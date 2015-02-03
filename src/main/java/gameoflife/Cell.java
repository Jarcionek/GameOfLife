package gameoflife;

public class Cell {

    private final int y;
    private final int x;
    private final CellType cellType;

    public Cell(int y, int x, CellType cellType) {
        this.y = y;
        this.x = x;
        this.cellType = cellType;
    }

    public int y() {
        return y;
    }

    public int x() {
        return x;
    }

    public CellType type() {
        return cellType;
    }

    public Cell withType(CellType type) {
        return new Cell(y, x, type);
    }

}
