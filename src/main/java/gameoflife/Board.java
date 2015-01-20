package gameoflife;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

public class Board {

    private final int[][] board;

    public Board(int[][] board) {
        this.board = board;
    }

    public void nextGeneration() {
        Set<Point> newDead = new HashSet<>();
        Set<Point> newLive = new HashSet<>();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (coordinatesAreOnMargin(i, j)) {
                    continue;
                }

                if (cellIsLive(i, j)) {
                    if (liveNeighboursOf(i, j) < 2) {
                        newDead.add(new Point(i, j));
                    } else if (liveNeighboursOf(i, j) > 3) {
                        newDead.add(new Point(i, j));
                    }
                } else {
                    if (liveNeighboursOf(i, j) == 3) {
                        newLive.add(new Point(i, j));
                    }
                }
            }
        }

        for (Point point : newDead) {
            board[point.x][point.y] = 0;
        }
        for (Point point : newLive) {
            board[point.x][point.y] = 1;
        }
    }

    private boolean coordinatesAreOnMargin(int i, int j) {
        return i == 0 || j == 0 || i == board.length - 1 || j == board[i].length - 1;
    }

    private int liveNeighboursOf(int i, int j) {
        int count = 0;
        for (int di = -1; di <= 1; di++) {
            for (int dj = -1; dj <= 1; dj++) {
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
