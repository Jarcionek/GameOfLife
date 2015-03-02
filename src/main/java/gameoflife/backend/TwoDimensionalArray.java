package gameoflife.backend;

import java.lang.reflect.Array;

/**
 * Setting values outside bounds is ignored, no exception is thrown.
 * Getting values outside bounds returns defaultValue.
 */
public class TwoDimensionalArray<T> {

    private final T defaultValue;
    private final T[][] array;

    @SuppressWarnings("unchecked")
    public TwoDimensionalArray(int height, int width, T defaultValue) {
        this.defaultValue = defaultValue;
        this.array = (T[][]) Array.newInstance(defaultValue.getClass(), height, width);

        for (int y = 0; y < height(); y++) {
            for (int x = 0; x < width(); x++) {
                array[y][x] = defaultValue;
            }
        }
    }

    public void set(int y, int x, T value) {
        if (0 <= y && y < height() && 0 <= x && x < width()) {
            array[y][x] = value;
        }
    }

    public T get(int y, int x) {
        if (0 <= y && y < height() && 0 <= x && x < width()) {
            return array[y][x];
        } else {
            return defaultValue;
        }
    }

    public int height() {
        return array.length;
    }

    public int width() {
        return array[0].length;
    }

}
