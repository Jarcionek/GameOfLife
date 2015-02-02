package gameoflife;

import java.awt.Color;

public enum CellType {

    DEAD      (0, false, false, new Color(255, 255, 255)),
    WALL      (1, false, true,  new Color(0,   0,   0)),
    BLUE      (2, true,  false, new Color(0,   0,   255)),
    RED       (3, true,  false, new Color(255, 0,   0  )),
    BLUE_TRAIL(4, false, false, new Color(200, 200, 255)),
    RED_TRAIL (5, false, false, new Color(255, 200, 200));

    private final int value;
    private final boolean isLive;
    private final boolean isFixed;
    private final Color color;

    private CellType(int value, boolean isLive, boolean isFixed, Color color) {
        this.value = value;
        this.isLive = isLive;
        this.isFixed = isFixed;
        this.color = color;
    }

    public static CellType valueOf(int value) {
        for (CellType cell : CellType.values()) {
            if (cell.value == value) {
                return cell;
            }
        }
        throw new IllegalArgumentException("No cell for value " + value);
    }

    public int value() {
        return value;
    }

    public boolean isLive() {
        return isLive;
    }

    public boolean isFixed() {
        return isFixed;
    }

    public Color color() {
        return color;
    }

}
