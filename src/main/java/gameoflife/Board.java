package gameoflife;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Board {

    private final int[][] board;

    public Board(int[][] board) {
        this.board = boardWithMargin(board);
    }

    public void nextGeneration() {
        Set<Cell> deadCells = new HashSet<>();
        Set<Cell> liveCells = new HashSet<>();

        for (Cell cell : allCells()) {
            int liveNeighbours = liveNeighboursOf(cell);
            if (isLive(cell)) {
                if (liveNeighbours < 2 || 3 < liveNeighbours) {
                    deadCells.add(cell);
                }
            } else {
                if (liveNeighbours == 3) {
                    liveCells.add(cell);
                }
            }
        }

        updateBoard(deadCells, liveCells);
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



    private void updateBoard(Set<Cell> deadCells, Set<Cell> liveCells) {
        for (Cell cell : deadCells) {
            board[cell.i][cell.j] = 0;
        }
        for (Cell cell : liveCells) {
            board[cell.i][cell.j] = 1;
        }
    }

    private int liveNeighboursOf(Cell cell) {
        int count = 0;
        for (int di = -1; di <= 1; di++) {
            for (int dj = -1; dj <= 1; dj++) {
                if (di != 0 || dj != 0) {
                    if (cellIsLive(cell.i + di, cell.j + dj)) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    private boolean isLive(Cell cell) {
        return cellIsLive(cell.i, cell.j);
    }

    private boolean cellIsLive(int i, int j) {
        return board[i][j] == 1;
    }

    private Iterable<Cell> allCells() {
        List<Cell> allCells = new ArrayList<>();
        for (int i = 1; i < board.length - 1; i++) {
            for (int j = 1; j < board[i].length - 1; j++) {
                allCells.add(new Cell(i, j));
            }
        }
        return allCells;
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

    private static class Cell {

        private final int i;
        private final int j;

        public Cell(int i, int j) {
            this.i = i;
            this.j = j;
        }

    }

}
