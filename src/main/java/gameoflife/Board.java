package gameoflife;

public class Board {

    private final int[][] board;

    public Board(int[][] board) {
        this.board = board;
    }

    public void nextGeneration() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (cellIsLive(i, j)) {
                    if (liveNeighboursOf(i, j) < 2) {
                        board[i][j] = 0;
                    }
                }
            }
        }
    }

    private int liveNeighboursOf(int i, int j) {
        int count = 0;
        for (int di = -1; di < 1; di++) {
            for (int dj = -1; dj < 1; dj++) {
                if (di != 0 || dj != 0) {
                    if (cellIsLive(i + di, j + dj)) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    private boolean cellIsLive(int i, int j) {
        return board[i][j] == 1;
    }

    public String asString() {
        StringBuilder sb = new StringBuilder();
        for (int[] row : board) {
            for (int value : row) {
                sb.append(value);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}
