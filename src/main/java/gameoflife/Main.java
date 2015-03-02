package gameoflife;

import gameoflife.backend.Game;
import gameoflife.backend.Matrix;
import gameoflife.frontend.MainFrame;

public class Main {

    private static final int BOARD_WIDTH = 160;
    private static final int BOARD_HEIGHT = 80;
    public static final int CELL_SIZE = 10;

    public static void main(String[] args) {
        enableWorkAroundSwingBugs();

        new MainFrame(new Game(Matrix.randomMatrix(BOARD_HEIGHT, BOARD_WIDTH)));
    }

    private static void enableWorkAroundSwingBugs() {
        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
    }

}
