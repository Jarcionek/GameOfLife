package gameoflife;

public enum CellType {

    DEAD(0),
    BLUE(1),
    RED(2),
    BLUE_TRAIL(3),
    RED_TRAIL(4);

    public final int value;

    private CellType(int value) {
        this.value = value;
    }

    public static CellType valueOf(int value) {
        for (CellType cell : CellType.values()) {
            if (cell.value == value) {
                return cell;
            }
        }
        throw new IllegalArgumentException("No cell for value " + value);
    }

}
