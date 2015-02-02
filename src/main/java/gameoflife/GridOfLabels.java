package gameoflife;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GridOfLabels extends JPanel{

    private final int height;
    private final int width;
    private final JComponent[][] cells;
    private final Matrix matrix;

    private boolean mouseListenerEnabled = true;

    public GridOfLabels(int cellSize, Matrix matrix) {
        super(new GridLayout(matrix.getHeight(), matrix.getWidth()));
        this.height = matrix.getHeight();
        this.width = matrix.getWidth();
        this.cells = new JLabel[height][width];
        this.matrix = matrix;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                add(cells[y][x] = new JLabel());
                cells[y][x].setPreferredSize(new Dimension(cellSize, cellSize));
                cells[y][x].setOpaque(true);
                setCellBorder(y, x);
                final int fy = y;
                final int fx = x;
                cells[y][x].addMouseMotionListener(new MouseAdapter() {
                    @Override
                    public void mouseMoved(MouseEvent e) {
                        if (!mouseListenerEnabled) {
                            return;
                        }

                        if (e.getModifiers() == InputEvent.SHIFT_MASK) {
                            matrix.set(fy, fx, CellType.DEAD.value());
                        } else if (e.getModifiers() == InputEvent.CTRL_MASK) {
                            matrix.set(fy, fx, CellType.BLUE.value());
                        } else if (e.getModifiers() == InputEvent.ALT_MASK) {
                            matrix.set(fy, fx, CellType.RED.value());
                        }
                        refreshCells();
                    }
                });
            }
        }
    }

    public void refreshCells() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                cells[y][x].setBackground(matrix.get(y, x).color());
            }
        }
        this.repaint();
    }

    public void setMouseListenerEnabled(boolean mouseListenerEnabled) {
        this.mouseListenerEnabled = mouseListenerEnabled;
    }

    private void setCellBorder(int y, int x) {
        Color color = Color.darkGray;
        if (x == width - 1 && y == height - 1) {
            cells[y][x].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, color));
        } else if (x == width - 1) {
            cells[y][x].setBorder(BorderFactory.createMatteBorder(1, 1, 0, 1, color));
        } else if (y == height - 1) {
            cells[y][x].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 0, color));
        } else {
            cells[y][x].setBorder(BorderFactory.createMatteBorder(1, 1, 0, 0, color));
        }
    }

}
