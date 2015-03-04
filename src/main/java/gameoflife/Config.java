package gameoflife;

import gameoflife.backend.Game;
import gameoflife.frontend.MainFrame;

import static gameoflife.Constants.BOARD_HEIGHT;
import static gameoflife.Constants.BOARD_WIDTH;
import static gameoflife.backend.Matrix.emptyMatrix;
import static gameoflife.backend.Matrix.randomMatrix;

public class Config {

    public static void createNewFrame() {
        new MainFrame(new Game(randomMatrix(BOARD_HEIGHT, BOARD_WIDTH)));
    }

    public static void createNewFrameWithEmptyMatrix() {
        new MainFrame(new Game(emptyMatrix(BOARD_HEIGHT, BOARD_WIDTH)));
    }

}
