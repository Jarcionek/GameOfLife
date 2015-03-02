package gameoflife.backend;

public class ConstructPatternOrientation {

    public int[][] inOrientation(int orientation, int[][] pattern) {
        switch (orientation) {
            case 0: return pattern;
            case 1: return flippedVertically(pattern);
            case 2: return rotatedClockwise(pattern);
            case 3: return flippedHorizontally(rotatedClockwise(pattern));
            case 4: return rotatedAround(pattern);
            case 5: return flippedVertically(rotatedAround(pattern));
            case 6: return rotatedAntiClockwise(pattern);
            case 7: return flippedHorizontally(rotatedAntiClockwise(pattern));
            default: throw new IllegalArgumentException("orientation = " + orientation);
        }
    }

    private int[][] rotatedClockwise(int[][] pattern) {
        return flippedVertically(transposed(pattern));
    }

    private int[][] rotatedAround(int[][] pattern) {
        return rotatedClockwise(rotatedClockwise(pattern));
    }

    private int[][] rotatedAntiClockwise(int[][] pattern) {
        return rotatedClockwise(rotatedClockwise(rotatedClockwise(pattern)));
    }

    private int[][] transposed(int[][] pattern) {
        int height = pattern[0].length;
        int width = pattern.length;
        int[][] result = new int[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                result[y][x] = pattern[x][y];
            }
        }

        return result;
    }

    private int[][] flippedVertically(int[][] pattern) {
        int height = pattern.length;
        int width = pattern[0].length;
        int[][] result = new int[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                result[y][x] = pattern[y][(width - 1) - x];
            }
        }

        return result;
    }

    @SuppressWarnings("ManualArrayCopy")
    private int[][] flippedHorizontally(int[][] pattern) {
        int height = pattern.length;
        int width = pattern[0].length;
        int[][] result = new int[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                result[y][x] = pattern[(height - 1) - y][x];
            }
        }

        return result;
    }

}
