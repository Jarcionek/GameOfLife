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
        for (int y = 1; y < board.length - 1; y++) {
            for (int x = 1; x < board[y].length - 1; x++) {
                sb.append(board[y][x]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public boolean isCellLive(int y, int x) {
        return board[y + 1][x + 1] == 1;
    }

    public void setDead(int y, int x) {
        board[y + 1][x + 1] = 0;
    }

    public void setLive(int y, int x) {
        board[y + 1][x + 1] = 1;
    }



    private void updateBoard(Set<Cell> deadCells, Set<Cell> liveCells) {
        for (Cell cell : deadCells) {
            board[cell.y][cell.x] = 0;
        }
        for (Cell cell : liveCells) {
            board[cell.y][cell.x] = 1;
        }
    }

    private int liveNeighboursOf(Cell cell) {
        int count = 0;
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                if (dy != 0 || dx != 0) {
                    if (cellIsLive(cell.y + dy, cell.x + dx)) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    private boolean isLive(Cell cell) {
        return cellIsLive(cell.y, cell.x);
    }

    private boolean cellIsLive(int y, int x) {
        return board[y][x] == 1;
    }

    private Iterable<Cell> allCells() {
        List<Cell> allCells = new ArrayList<>();
        for (int y = 1; y < board.length - 1; y++) {
            for (int x = 1; x < board[y].length - 1; x++) {
                allCells.add(new Cell(y, x));
            }
        }
        return allCells;
    }

    @SuppressWarnings("ManualArrayCopy")
    private static int[][] boardWithMargin(int[][] board) {
        int[][] newBoard = new int[board.length + 2][board[0].length + 2];

        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length; x++) {
                newBoard[y + 1][x + 1] = board[y][x];
            }
        }

        return newBoard;
    }

    private static class Cell {

        private final int y;
        private final int x;

        public Cell(int y, int x) {
            this.y = y;
            this.x = x;
        }

    }

}
