package gameoflife;

public enum Cell {

    DEAD(0),
    BLUE(1),
    RED(2),
    BLUE_TRAIL(3),
    RED_TRAIL(4);

    public final int value;

    private Cell(int value) {
        this.value = value;
    }

    public static Cell withValue(int value) {
        for (Cell cell : Cell.values()) {
            if (cell.value == value) {
                return cell;
            }
        }
        throw new IllegalArgumentException("No cell for value " + value);
    }

}
