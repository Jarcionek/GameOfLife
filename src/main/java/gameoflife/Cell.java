package gameoflife;

public class Cell {

    private final int y;
    private final int x;
    private final int color;

    public Cell(int y, int x, int color) {
        this.y = y;
        this.x = x;
        this.color = color;
    }

    public Cell(Cell cell, int color) {
        this.y = cell.y();
        this.x = cell.x();
        this.color = color;
    }

    public int y() {
        return y;
    }

    public int x() {
        return x;
    }

    public int type() {
        return color;
    }

}
