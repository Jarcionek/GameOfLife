package gameoflife;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GridOfLabels extends JPanel {

    private final int height;
    private final int width;
    private final CellLabel[][] cells;
    private final Matrix matrix;

    private final JComboBox<CellType> drawingSelectionComboBox;

    private boolean mouseListenerEnabled = true;

    public GridOfLabels(int cellSize, Matrix matrix, JComboBox<CellType> drawingSelectionComboBox) {
        super(new GridLayout(matrix.getHeight(), matrix.getWidth()));
        this.drawingSelectionComboBox = drawingSelectionComboBox;
        this.height = matrix.getHeight();
        this.width = matrix.getWidth();
        this.cells = new CellLabel[height][width];
        this.matrix = matrix;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                add(cells[y][x] = new CellLabel(y, x));
                cells[y][x].setPreferredSize(new Dimension(cellSize, cellSize));
                cells[y][x].setOpaque(true);
                setCellBorder(y, x);
            }
        }

        CellDrawingMouseAdapter listener = new CellDrawingMouseAdapter();
        this.addMouseMotionListener(listener);
        this.addMouseListener(listener);
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

    private class CellLabel extends JLabel {

        private final int y;
        private final int x;

        private CellLabel(int y, int x) {
            this.y = y;
            this.x = x;
        }

    }

    private class CellDrawingMouseAdapter extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            paintCell(e.getPoint());
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            paintCell(e.getPoint());
        }

        private void paintCell(Point point) {
            if (!mouseListenerEnabled) {
                return;
            }

            Component component = GridOfLabels.this.findComponentAt(point);
            if (component instanceof CellLabel) {
                CellLabel cellLabel = (CellLabel) component;
                matrix.set(new Cell(cellLabel.y, cellLabel.x, (CellType) drawingSelectionComboBox.getSelectedItem()));
                refreshCells();
            }
        }

    }

}
