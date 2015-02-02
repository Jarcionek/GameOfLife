package gameoflife;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CancellationException;

public class MainFrame extends JFrame {

    private static final int CELL_SIZE = 10;
    private static final int BOARD_WIDTH = 100;
    private static final int BOARD_HEIGHT = 60;

    private final Game game;

    private SwingWorker<Object, Object> autoPlaySwingWorker;
    private int generationCount = 0; //TODO Jarek: move to Game

    private final GridOfLabels cells;
    private final JComboBox<CellType> drawingSelectionComboBox;
    private final JLabel generationCountLabel = new PlainFontLabel("0");
    private final JButton nextButton = new JButton("next");
    private final JCheckBox autoPlayCheckBox = new JCheckBox("autoplay");
    private final JSlider autoPlaySlider = new JSlider(1, 201, 100);
    private final StatisticsPanel statisticsPanel = new StatisticsPanel();

    public MainFrame() {
        super("Conway's Game of Life");

        int[][] initBoard = new int[BOARD_HEIGHT][BOARD_WIDTH];
        Random random = new Random();
        for (int y = 0; y < BOARD_HEIGHT; y++) {
            for (int x = 0; x < BOARD_WIDTH; x++) {
                if (random.nextInt(3) > 1) {
                    switch (random.nextInt(2)) {
                        case 0: initBoard[y][x] = CellType.RED.value(); break;
                        case 1: initBoard[y][x] = CellType.BLUE.value(); break;
                    }
                }
            }
        }

        this.drawingSelectionComboBox = new JComboBox<>(CellType.values());

        Matrix matrix = new Matrix(initBoard);
        this.game = new Game(matrix);
        this.cells = new GridOfLabels(CELL_SIZE, matrix, drawingSelectionComboBox);

        autoPlaySlider.setMajorTickSpacing(10);
        autoPlaySlider.setPaintTicks(true);
        Dictionary<Integer, JLabel> dictionary = new Hashtable<>();
        dictionary.put(1, new PlainFontLabel("0ms", 10));
        dictionary.put(101, new PlainFontLabel("100ms", 10));
        dictionary.put(201, new PlainFontLabel("200ms", 10));
        autoPlaySlider.setLabelTable(dictionary);
        autoPlaySlider.setPaintLabels(true);

        createComponents();
        refreshGui();

        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void createComponents() {
        autoPlayCheckBox.setFont(PlainFontLabel.DEFAULT_FONT);

        autoPlayCheckBox.addActionListener(click -> {
            if (autoPlayCheckBox.isSelected()) {
                nextButton.setEnabled(false);
                autoPlaySwingWorker = new AutoPlaySwingWorker();
                cells.setMouseListenerEnabled(false);
                autoPlaySwingWorker.execute();
            } else {
                autoPlaySwingWorker.cancel(false);
                cells.setMouseListenerEnabled(true);
                nextButton.setEnabled(true);
            }
        });

        nextButton.addActionListener(click -> {
            nextGeneration();
            refreshGui();
        });

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(drawingSelectionComboBox);
        buttonsPanel.add(generationCountLabel);
        buttonsPanel.add(nextButton);
        buttonsPanel.add(autoPlayCheckBox);
        buttonsPanel.add(new PlainFontLabel("delay:"));
        buttonsPanel.add(autoPlaySlider);
        buttonsPanel.add(statisticsPanel);

        JPanel centralPanel = new JPanel(new BorderLayout());
        centralPanel.add(cells, BorderLayout.CENTER);
        centralPanel.add(buttonsPanel, BorderLayout.SOUTH);
        setContentPane(centralPanel);

        JMenuBar jMenuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        JMenuItem restartMenuItem = new JMenuItem("Restart");
        restartMenuItem.addActionListener(click -> {
            if (autoPlaySwingWorker != null) {
                autoPlaySwingWorker.cancel(true);
            }
            dispose();
            SwingUtilities.invokeLater(MainFrame::new);
        });
        gameMenu.add(restartMenuItem);
        jMenuBar.add(gameMenu);
        setJMenuBar(jMenuBar);
    }

    private void nextGeneration() {
        game.nextGeneration();
        generationCount++;
    }

    private void refreshGui() {
        generationCountLabel.setText("generation " + generationCount);
        statisticsPanel.update(game.getStatistics());
        cells.refreshCells();
    }

    private class AutoPlaySwingWorker extends SwingWorker<Object, Object> {

        @Override
        protected Object doInBackground() throws Exception {
            while (!isCancelled()) {
                nextGeneration();
                publish(new Object());
                Thread.sleep(autoPlaySlider.getValue());
            }
            return null;
        }

        @Override
        protected void process(List<Object> chunks) {
            refreshGui();
        }

        @Override
        protected void done() {
            try {
                get();
            } catch (CancellationException e) {
                // cancelled by unchecking the box
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

}
