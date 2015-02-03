package gameoflife;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Game {

    private final Matrix matrix;

    private int generationCount = 0;

    public Game(Matrix matrix) {
        this.matrix = matrix;
    }

    public void nextGeneration() {
        Set<Cell> deadCells = new HashSet<>();
        Set<Cell> liveCells = new HashSet<>();

        for (Cell cell : allCells()) {
            if (isFixed(cell)) {
                continue;
            }
            int liveNeighbours = liveNeighboursOf(cell);
            if (isLive(cell)) {
                if (liveNeighbours < 2 || 3 < liveNeighbours) {

                    if (matrix.get(cell.y(), cell.x()) == CellType.BLUE) {
                        deadCells.add(new Cell(cell, CellType.BLUE_TRAIL));
                    } else if (matrix.get(cell.y(), cell.x()) == CellType.RED) {
                        deadCells.add(new Cell(cell, CellType.RED_TRAIL));
                    } else {
                        throw new RuntimeException("shouldn't happen agpdsagjsa0igsg");
                    }

                } else {
                    //TODO Jarek: change color?
                }
            } else {
                if (liveNeighbours == 3) {
                    CellType dominantValueOfNeighbours = dominantCellTypeOfNeighbours(cell);
                    liveCells.add(new Cell(cell, dominantValueOfNeighbours));
                }
            }
        }

        updateBoard(deadCells, liveCells);
        generationCount++;
    }

    public Map<CellType, Integer> getStatistics() {
        Map<CellType, Integer> map = new EnumMap<>(CellType.class);

        for (CellType cellType : CellType.values()) {
            map.put(cellType, 0);
        }

        for (Cell cell : allCells()) {
            map.put(cell.type(), map.get(cell.type()) + 1);
        }

        return map;
    }

    public int getGenerationCount() {
        return generationCount;
    }


    private void updateBoard(Set<Cell> deadCells, Set<Cell> liveCells) {
        for (Cell cell : deadCells) {
            matrix.set(cell);
        }
        for (Cell cell : liveCells) {
            matrix.set(cell);
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

    private CellType dominantCellTypeOfNeighbours(Cell cell) {
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
        return blueCount > redCount ? CellType.BLUE : CellType.RED;
    }

    private boolean isLive(Cell cell) {
        return isLive(cell.y(), cell.x());
    }

    private boolean isLive(int y, int x) {
        return matrix.get(y, x).isLive();
    }

    private boolean isFixed(Cell cell) {
        return matrix.get(cell.y(), cell.x()).isFixed();
    }

    private Iterable<Cell> allCells() {
        List<Cell> allCells = new ArrayList<>();
        for (int y = 0; y < matrix.getHeight(); y++) {
            for (int x = 0; x < matrix.getWidth(); x++) {
                allCells.add(new Cell(y, x, matrix.get(y, x)));
            }
        }
        return allCells;
    }

}
