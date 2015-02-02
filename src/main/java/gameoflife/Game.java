package gameoflife;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Game {

    private final Matrix matrix;

    public Game(Matrix matrix) {
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
                        deadCells.add(new Cell(cell, CellType.BLUE_TRAIL.value()));
                    } else if (matrix.get(cell.y(), cell.x()) == CellType.RED) {
                        deadCells.add(new Cell(cell, CellType.RED_TRAIL.value()));
                    } else {
                        throw new RuntimeException("shouldn't happen agpdsagjsa0igsg");
                    }

                } else {
                    //TODO Jarek: change color?
                }
            } else {
                if (liveNeighbours == 3) {
                    int dominantValueOfNeighbours = dominantColorOfNeighbours(cell);
                    liveCells.add(new Cell(cell, dominantValueOfNeighbours));
                }
            }
        }

        updateBoard(deadCells, liveCells);
    }

    private void updateBoard(Set<Cell> deadCells, Set<Cell> liveCells) {
        for (Cell cell : deadCells) {
            matrix.set(cell.y(), cell.x(), cell.value());
        }
        for (Cell cell : liveCells) {
            matrix.set(cell.y(), cell.x(), cell.value());
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
        int blueCount = 0;
        int redCount = 0;
        //TODO Jarek: support for more colors
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                if (dy == 0 && dx == 0) {
                    continue;
                }
                int x = cell.x() + dx;
                int y = cell.y() + dy;
                if (isLive(y, x)) {
                    if (matrix.get(y, x) == CellType.BLUE) {
                        blueCount++;
                    } else if (matrix.get(y, x) == CellType.RED) {
                        redCount++;
                    }
                }
            }
        }
        return blueCount > redCount ? CellType.BLUE.value() : CellType.RED.value();
    }

    private boolean isLive(Cell cell) {
        return isLive(cell.y(), cell.x());
    }

    private boolean isLive(int y, int x) {
        return matrix.get(y, x).isLive();
    }

    private Iterable<Cell> allCells() {
        List<Cell> allCells = new ArrayList<>();
        for (int y = 0; y < matrix.getHeight(); y++) {
            for (int x = 0; x < matrix.getWidth(); x++) {
                allCells.add(new Cell(y, x, -1));
            }
        }
        return allCells;
    }

}
