package gameoflife.frontend;

import gameoflife.backend.CellType;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;

public class StatisticsPanel extends JPanel {

    private static final int ROWS = CellType.values().length;

    private Map<CellType, JLabel> countLabels = new HashMap<>();

    public StatisticsPanel() {
        super(new GridLayout(ROWS, 2));

        for (int i = 0; i < ROWS; i++) {
            JLabel label = new PlainFontLabel(CellType.values()[i].name(), 10);
            label.setBorder(new EmptyBorder(0, 0, 0, 3));
            countLabels.put(CellType.values()[i], new PlainFontLabel("0", 10));
            add(label);
            add(countLabels.get(CellType.values()[i]));
        }
    }

    public void update(Map<CellType, Integer> statistics) {
        for (Map.Entry<CellType, Integer> entry : statistics.entrySet()) {
            countLabels.get(entry.getKey()).setText("" + entry.getValue());
        }
        repaint();
    }

}
