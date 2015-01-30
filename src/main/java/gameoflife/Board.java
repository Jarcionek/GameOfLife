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
        Set<ColorCell> deadCells = new HashSet<>();
        Set<ColorCell> liveCells = new HashSet<>();

        for (Cell cell : allCells()) {
            int liveNeighbours = liveNeighboursOf(cell);
            if (isLive(cell)) {
                if (liveNeighbours < 2 || 3 < liveNeighbours) {

                    if (board[cell.y][cell.x] == gameoflife.Cell.BLUE.value) {
                        deadCells.add(new ColorCell(cell.y, cell.x, gameoflife.Cell.BLUE_TRAIL.value));
                    } else if (board[cell.y][cell.x] == gameoflife.Cell.RED.value) {
                        deadCells.add(new ColorCell(cell.y, cell.x, gameoflife.Cell.RED_TRAIL.value));
                    } else {
                        throw new RuntimeException("shouldn't happen agpdsagjsa0igsg");
                    }

                } else {
                    //TODO Jarek: change color?
                }
            } else {
                if (liveNeighbours == 3) {
                    int dominantColorOfNeighbours = dominantColorOfNeighbours(cell);
                    liveCells.add(new ColorCell(cell.y, cell.x, dominantColorOfNeighbours));
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

    public int getCellColor(int y, int x) {
        return board[y + 1][x + 1];
    }

    public boolean isCellLive(int y, int x) {
        return board[y + 1][x + 1] != 0;
    }

    public void setDead(int y, int x) {
        board[y + 1][x + 1] = 0;
    }

    public void setLive(int y, int x) {
        board[y + 1][x + 1] = 1;
    }

    public void setLive(int y, int x, int color) {
        board[y + 1][x + 1] = color;
    }


    private void updateBoard(Set<ColorCell> deadCells, Set<ColorCell> liveCells) {
        for (ColorCell cell : deadCells) {
            board[cell.y][cell.x] = cell.color;
        }
        for (ColorCell cell : liveCells) {
            board[cell.y][cell.x] = cell.color;
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

    private int dominantColorOfNeighbours(Cell cell) {
        int color_1 = 0;
        int color_2 = 0;
        //TODO Jarek: support for more colors
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                if (dy == 0 && dx == 0) {
                    continue;
                }
                int x = cell.x + dx;
                int y = cell.y + dy;
                if (cellIsLive(y, x)) {
                    if (board[y][x] == 1) {
                        color_1++;
                    } else if (board[y][x] == 2) {
                        color_2++;
                    }
                }
            }
        }
        return color_1 > color_2 ? 1 : 2;
    }

    private boolean isLive(Cell cell) {
        return cellIsLive(cell.y, cell.x);
    }

    private boolean cellIsLive(int y, int x) {
        return board[y][x] == 1 || board[y][x] == 2;
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

    private static class ColorCell {

        private final int y;
        private final int x;
        private final int color;

        public ColorCell(int y, int x, int color) {
            this.y = y;
            this.x = x;
            this.color = color;
        }

    }

}
