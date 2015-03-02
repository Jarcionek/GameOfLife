package gameoflife.frontend;

import gameoflife.backend.Cell;
import gameoflife.backend.CellType;
import gameoflife.backend.Construct;
import gameoflife.backend.ConstructPatternOrientation;
import gameoflife.backend.Matrix;
import gameoflife.backend.TwoDimensionalArray;

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
import java.awt.event.MouseWheelEvent;

import static gameoflife.Constants.CELL_SIZE;
import static gameoflife.Constants.HIGHLIGHTING_COLOR;

public class GridOfLabels extends JPanel {

    private final TwoDimensionalArray<CellLabel> cells;
    private final Matrix matrix;

    private final JComboBox<Construct> insertingSelectionComboBox;
    private final JComboBox<CellType> drawingSelectionComboBox;

    private boolean drawingEnabled = true;

    public GridOfLabels(Matrix matrix, JComboBox<Construct> insertingSelectionComboBox, JComboBox<CellType> drawingSelectionComboBox) {
        super(new GridLayout(matrix.height(), matrix.width()));
        this.insertingSelectionComboBox = insertingSelectionComboBox;
        this.drawingSelectionComboBox = drawingSelectionComboBox;
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
        Color color = Color.darkGray;
        if (x == matrix.width() - 1 && y == matrix.height() - 1) {
            cells.get(y, x).setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, color));
        } else if (x == matrix.width() - 1) {
            cells.get(y, x).setBorder(BorderFactory.createMatteBorder(1, 1, 0, 1, color));
        } else if (y == matrix.height() - 1) {
            cells.get(y, x).setBorder(BorderFactory.createMatteBorder(1, 1, 1, 0, color));
        } else {
            cells.get(y, x).setBorder(BorderFactory.createMatteBorder(1, 1, 0, 0, color));
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

    private class CellDrawingMouseAdapter extends MouseAdapter { //TODO Jarek: extract to its own class

        private final ConstructPatternOrientation cpo = new ConstructPatternOrientation(); //TODO Jarek: do dependency injection

        private int orientation = 0;

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            orientation += e.getWheelRotation();
            orientation %= 8;
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
            int[][] pattern = ((Construct) insertingSelectionComboBox.getSelectedItem()).getPattern();
            CellType cellType = (CellType) drawingSelectionComboBox.getSelectedItem();

            pattern = cpo.inOrientation(orientation, pattern);

            for (int y = 0; y < pattern.length; y++) {
                for (int x = 0; x < pattern[0].length; x++) {
                    if (pattern[y][x] == 1) {
                        matrix.set(new Cell(cellLabel.y + y, cellLabel.x + x, cellType));
                    }
                }
            }
        }

        private void highlight(CellLabel cellLabel) {
            int[][] pattern = ((Construct) insertingSelectionComboBox.getSelectedItem()).getPattern();

            pattern = cpo.inOrientation(orientation, pattern);

            for (int y = 0; y < pattern.length; y++) {
                for (int x = 0; x < pattern[0].length; x++) {
                    if (pattern[y][x] == 1) {
                        cells.get(cellLabel.y + y, cellLabel.x + x).setBackground(HIGHLIGHTING_COLOR);
                    }
                }
            }
        }

    }

}
