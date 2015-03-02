package gameoflife;

import gameoflife.backend.Game;
import gameoflife.frontend.MainFrame;

import static gameoflife.Constants.BOARD_HEIGHT;
import static gameoflife.Constants.BOARD_WIDTH;
import static gameoflife.backend.Matrix.randomMatrix;

public class Main {

    public static void main(String[] args) {
        enableWorkAroundSwingBugs();

        new MainFrame(new Game(randomMatrix(BOARD_HEIGHT, BOARD_WIDTH)));
    }

    private static void enableWorkAroundSwingBugs() {
        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
    }

}
