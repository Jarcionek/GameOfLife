package gameoflife;

import java.util.Random;

public class Main {

    private static final int BOARD_WIDTH = 100;
    private static final int BOARD_HEIGHT = 60;
    public static final int CELL_SIZE = 10;

    public static void main(String[] args) {
        int[][] initBoard = new int[BOARD_HEIGHT][BOARD_WIDTH];
        Random random = new Random();
        for (int y = 0; y < BOARD_HEIGHT; y++) {
            for (int x = 0; x < BOARD_WIDTH; x++) {
                if (random.nextInt(3) > 1) {
                    switch (random.nextInt(2)) {
                        case 0: initBoard[y][x] = CellType.RED.value(); break;
                        case 1: initBoard[y][x] = CellType.BLUE.value(); break;
                    }
                }
            }
        }

        new MainFrame(new Game(new Matrix(initBoard)));
    }

}
