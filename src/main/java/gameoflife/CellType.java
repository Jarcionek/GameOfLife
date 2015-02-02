package gameoflife;

import java.awt.Color;

public enum CellType {

    DEAD      (0, false, new Color(255, 255, 255)),
    BLUE      (1, true,  new Color(0,   0,   255)),
    RED       (2, true,  new Color(255, 0,   0  )),
    BLUE_TRAIL(3, false, new Color(200, 200, 255)),
    RED_TRAIL (4, false, new Color(255, 200, 200));
    //TODO  Jarek: add walls (color = black)

    private final int value;
    private final boolean live;
    private final Color color;

    private CellType(int value, boolean live, Color color) {
        this.value = value;
        this.live = live;
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
        return live;
    }

    public Color color() {
        return color;
    }

}
