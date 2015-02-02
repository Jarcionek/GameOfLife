package gameoflife;

public class Cell {

    private final int y;
    private final int x;
    private final int value;

    public Cell(int y, int x, int value) {
        this.y = y;
        this.x = x;
        this.value = value;
    }

    public Cell(Cell cell, int value) {
        this.y = cell.y();
        this.x = cell.x();
        this.value = value;
    }

    public int y() {
        return y;
    }

    public int x() {
        return x;
    }

    public int value() {
        return value;
    }

}
