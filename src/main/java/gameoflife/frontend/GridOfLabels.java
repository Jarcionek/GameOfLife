package gameoflife.frontend;

import gameoflife.backend.CellType;
import gameoflife.backend.Construct;
import gameoflife.backend.Matrix;
import gameoflife.backend.TwoDimensionalArray;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.function.Supplier;

import static gameoflife.Constants.CELL_BORDER_COLOR;
import static gameoflife.Constants.CELL_SIZE;
import static gameoflife.Constants.HIGHLIGHTING_COLOR;

public class GridOfLabels extends JPanel {

    private final TwoDimensionalArray<CellLabel> cells;
    private final Matrix matrix;

    private final Supplier<Construct> constructSupplier;
    private final Supplier<CellType> cellTypeSupplier;

    private boolean drawingEnabled = true;

    public GridOfLabels(Matrix matrix, Supplier<Construct> constructSupplier, Supplier<CellType> cellTypeSupplier) {
        super(new GridLayout(matrix.height(), matrix.width()));
        this.constructSupplier = constructSupplier;
        this.cellTypeSupplier = cellTypeSupplier;
        this.cells = new TwoDimensionalArray<>(matrix.height(), matrix.width(), new CellLabel(-1, -1));
        this.matrix = matrix;

        for (int y = 0; y < cells.height(); y++) {
            for (int x = 0; x < cells.width(); x++) {
                CellLabel label = new CellLabel(y, x);
                label.setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));
                label.setOpaque(true);
                cells.set(y, x, label);
                add(label);
                setCellBorder(y, x);
            }
        }

        CellDrawingMouseAdapter cellDrawingMouseAdapter = new CellDrawingMouseAdapter();
        this.addMouseListener(cellDrawingMouseAdapter);
        this.addMouseMotionListener(cellDrawingMouseAdapter);
        this.addMouseWheelListener(cellDrawingMouseAdapter);
    }

    public void refreshCells() {
        for (int y = 0; y < matrix.height(); y++) {
            for (int x = 0; x < matrix.width(); x++) {
                cells.get(y, x).setBackground(matrix.get(y, x).color());
            }
        }
        this.repaint();
    }

    private void setCellBorder(int y, int x) {
        if (x == matrix.width() - 1 && y == matrix.height() - 1) {
            cells.get(y, x).setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, CELL_BORDER_COLOR));
        } else if (x == matrix.width() - 1) {
            cells.get(y, x).setBorder(BorderFactory.createMatteBorder(1, 1, 0, 1, CELL_BORDER_COLOR));
        } else if (y == matrix.height() - 1) {
            cells.get(y, x).setBorder(BorderFactory.createMatteBorder(1, 1, 1, 0, CELL_BORDER_COLOR));
        } else {
            cells.get(y, x).setBorder(BorderFactory.createMatteBorder(1, 1, 0, 0, CELL_BORDER_COLOR));
        }
    }

    public void setDrawingEnabled(boolean drawingEnabled) {
        this.drawingEnabled = drawingEnabled;
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

        private int orientation = 0;

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            orientation = (orientation + e.getWheelRotation() + 8) % 8;
            refreshCells();
            Component component = GridOfLabels.this.findComponentAt(e.getPoint());
            if (drawingEnabled && component instanceof CellLabel) {
                highlight((CellLabel) component);
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            mousePressedOrDragged(e.getPoint());
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            mousePressedOrDragged(e.getPoint());
        }

        private void mousePressedOrDragged(Point point) {
            Component component = GridOfLabels.this.findComponentAt(point);
            if (drawingEnabled && component instanceof CellLabel) {
                insertObject((CellLabel) component);
                refreshCells();
                highlight((CellLabel) component);
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            Component component = GridOfLabels.this.findComponentAt(e.getPoint());
            if (drawingEnabled && component instanceof CellLabel) {
                refreshCells();
                highlight((CellLabel) component);
            }
        }

        private void insertObject(CellLabel cellLabel) {
            int[][] pattern = pattern();
            CellType cellType = cellTypeSupplier.get();

            for (int y = 0; y < pattern.length; y++) {
                for (int x = 0; x < pattern[0].length; x++) {
                    if (pattern[y][x] == 1) {
                        matrix.set(cellLabel.y + y, cellLabel.x + x, cellType);
                    }
                }
            }
        }

        private void highlight(CellLabel cellLabel) {
            int[][] pattern = pattern();

            for (int y = 0; y < pattern.length; y++) {
                for (int x = 0; x < pattern[0].length; x++) {
                    if (pattern[y][x] == 1) {
                        cells.get(cellLabel.y + y, cellLabel.x + x).setBackground(HIGHLIGHTING_COLOR);
                    }
                }
            }
        }

        private int[][] pattern() {
            return constructSupplier.get().getPattern().inOrientation(orientation);
        }

    }

}
