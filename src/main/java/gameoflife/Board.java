package gameoflife;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

public class Board {

    private final int[][] board;

    public Board(int[][] board) {
        this.board = boardWithMargin(board);
    }
    
    public void nextGeneration() {
        Set<Point> newDead = new HashSet<>();
        Set<Point> newLive = new HashSet<>();

        for (int i = 1; i < board.length - 1; i++) {
            for (int j = 1; j < board[i].length - 1; j++) {
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

    public String asString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < board.length - 1; i++) {
            for (int j = 1; j < board[i].length - 1; j++) {
                sb.append(board[i][j]);
            }
            sb.append("\n");
        }
        return sb.toString();
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

    @SuppressWarnings("ManualArrayCopy")
    private static int[][] boardWithMargin(int[][] board) {
        int[][] newBoard = new int[board.length + 2][board[0].length + 2];

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                newBoard[i + 1][j + 1] = board[i][j];
            }
        }

        return newBoard;
    }

}
