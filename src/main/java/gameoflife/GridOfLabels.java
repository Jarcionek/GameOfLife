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

    private final TwoDimensionalArray<CellLabel> cells;
    private final Matrix matrix;

    private final JComboBox<Construct> insertingSelectionComboBox;
    private final JComboBox<CellType> drawingSelectionComboBox;

    private MouseListenerMode mouseListenerMode = MouseListenerMode.DISABLED;

    public GridOfLabels(Matrix matrix, JComboBox<Construct> insertingSelectionComboBox, JComboBox<CellType> drawingSelectionComboBox) {
        super(new GridLayout(matrix.height(), matrix.width()));
        this.insertingSelectionComboBox = insertingSelectionComboBox;
        this.drawingSelectionComboBox = drawingSelectionComboBox;
        this.cells = new TwoDimensionalArray<>(matrix.height(), matrix.width(), new CellLabel(-1, -1));
        this.matrix = matrix;

        for (int y = 0; y < cells.height(); y++) {
            for (int x = 0; x < cells.width(); x++) {
                CellLabel label = new CellLabel(y, x);
                label.setPreferredSize(new Dimension(Main.CELL_SIZE, Main.CELL_SIZE));
                label.setOpaque(true);
                cells.set(y, x, label);
                add(label);
                setCellBorder(y, x);
            }
        }

        CellDrawingMouseAdapter cellDrawingMouseAdapter = new CellDrawingMouseAdapter();
        this.addMouseListener(cellDrawingMouseAdapter);
        this.addMouseMotionListener(cellDrawingMouseAdapter);

        ConstructInsertingMouseAdapter constructInsertingMouseAdapter = new ConstructInsertingMouseAdapter();
        this.addMouseListener(constructInsertingMouseAdapter);
        this.addMouseMotionListener(constructInsertingMouseAdapter);
    }

    public void refreshCells() {
        for (int y = 0; y < matrix.height(); y++) {
            for (int x = 0; x < matrix.width(); x++) {
                cells.get(y, x).setBackground(matrix.get(y, x).color());
            }
        }
        this.repaint();
    }

    public void setMouseListenerMode(MouseListenerMode mouseListenerMode) {
        this.mouseListenerMode = mouseListenerMode;
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
            if (mouseListenerMode != MouseListenerMode.DRAWING) {
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

    private class ConstructInsertingMouseAdapter extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            if (mouseListenerMode != MouseListenerMode.INSERTING) {
                return;
            }

            Component component = GridOfLabels.this.findComponentAt(e.getPoint());
            if (!(component instanceof CellLabel)) {
                return;
            }
            final int fy = ((CellLabel) component).y;
            final int fx = ((CellLabel) component).x;

            int[][] pattern = ((Construct) insertingSelectionComboBox.getSelectedItem()).getPattern();
            CellType cellType = (CellType) drawingSelectionComboBox.getSelectedItem();

            for (int y = 0; y < pattern.length; y++) {
                for (int x = 0; x < pattern[0].length; x++) {
                    if (pattern[y][x] == 1) {
                        matrix.set(new Cell(fy + y, fx + x, cellType));
                    }
                }
            }

            refreshCells();
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            if (mouseListenerMode != MouseListenerMode.INSERTING) {
                return;
            }

            Component component = GridOfLabels.this.findComponentAt(e.getPoint());
            if (!(component instanceof CellLabel)) {
                return;
            }
            refreshCells();
            final int fy = ((CellLabel) component).y;
            final int fx = ((CellLabel) component).x;

            int[][] pattern = ((Construct) insertingSelectionComboBox.getSelectedItem()).getPattern();

            for (int y = 0; y < pattern.length; y++) {
                for (int x = 0; x < pattern[0].length; x++) {
                    if (pattern[y][x] == 1) {
                        cells.get(fy + y, fx + x).setBackground(Color.yellow);
                    }
                }
            }
        }

    }

}
