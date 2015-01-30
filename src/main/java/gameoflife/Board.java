package gameoflife;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Board {

    private final Matrix matrix;

    public Board(Matrix matrix) {
        this.matrix = matrix;
    }

    public void nextGeneration() {
        Set<Cell> deadCells = new HashSet<>();
        Set<Cell> liveCells = new HashSet<>();

        for (Cell cell : allCells()) {
            int liveNeighbours = liveNeighboursOf(cell);
            if (isLive(cell)) {
                if (liveNeighbours < 2 || 3 < liveNeighbours) {

                    if (matrix.get(cell.y(), cell.x()) == CellType.BLUE) {
                        deadCells.add(new Cell(cell, CellType.BLUE_TRAIL.value));
                    } else if (matrix.get(cell.y(), cell.x()) == CellType.RED) {
                        deadCells.add(new Cell(cell, CellType.RED_TRAIL.value));
                    } else {
                        throw new RuntimeException("shouldn't happen agpdsagjsa0igsg");
                    }

                } else {
                    //TODO Jarek: change color?
                }
            } else {
                if (liveNeighbours == 3) {
                    int dominantColorOfNeighbours = dominantColorOfNeighbours(cell);
                    liveCells.add(new Cell(cell, dominantColorOfNeighbours));
                }
            }
        }

        updateBoard(deadCells, liveCells);
    }

    private void updateBoard(Set<Cell> deadCells, Set<Cell> liveCells) {
        for (Cell cell : deadCells) {
            matrix.set(cell.y(), cell.x(), cell.type());
        }
        for (Cell cell : liveCells) {
            matrix.set(cell.y(), cell.x(), cell.type());
        }
    }

    private int liveNeighboursOf(Cell cell) {
        int count = 0;
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                if (dy != 0 || dx != 0) {
                    if (isLive(cell.y() + dy, cell.x() + dx)) {
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
                int x = cell.x() + dx;
                int y = cell.y() + dy;
                if (isLive(y, x)) {
                    if (matrix.get(y, x).value == 1) {
                        color_1++;
                    } else if (matrix.get(y, x).value == 2) {
                        color_2++;
                    }
                }
            }
        }
        return color_1 > color_2 ? 1 : 2;
    }

    private boolean isLive(Cell cell) {
        return isLive(cell.y(), cell.x());
    }

    private boolean isLive(int y, int x) {
        return matrix.get(y, x).isLive();
    }

    private Iterable<Cell> allCells() {
        List<Cell> allCells = new ArrayList<>();
        for (int y = 0; y < matrix.getWidth(); y++) {
            for (int x = 0; x < matrix.getHeight(); x++) {
                allCells.add(new Cell(y, x, -1));
            }
        }
        return allCells;
    }

}
