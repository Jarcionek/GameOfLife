package gameoflife.frontend;

import gameoflife.backend.CellType;
import gameoflife.backend.Construct;
import gameoflife.backend.Game;

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
import java.util.List;
import java.util.concurrent.CancellationException;

import static gameoflife.Constants.BOARD_HEIGHT;
import static gameoflife.Constants.BOARD_WIDTH;
import static gameoflife.backend.Matrix.emptyMatrix;
import static gameoflife.backend.Matrix.randomMatrix;

public class MainFrame extends JFrame {

    private final Game game;

    private SwingWorker<Object, Object> autoPlaySwingWorker;

    private final GridOfLabels cells;
    private final JComboBox<Construct> insertingSelectionComboBox = new JComboBox<>(Construct.values());
    private final JComboBox<CellType> drawingSelectionComboBox = new JComboBox<>(CellType.values());
    private final JLabel generationCountLabel = new PlainFontLabel("0");
    private final JButton nextButton = new JButton("next");
    private final JCheckBox autoPlayCheckBox = new JCheckBox("autoplay");
    private final JSlider autoPlaySlider = new AutoPlaySlider();
    private final StatisticsPanel statisticsPanel = new StatisticsPanel();

    public MainFrame(Game game) {
        super("Conway's Game of Life");

        this.game = game;
        this.cells = new GridOfLabels(game.getMatrix(), insertingSelectionComboBox, drawingSelectionComboBox);

        customizeComponentsAndAddListeners();
        createLayout();
        createMenuBar();
        refreshGui();

        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void customizeComponentsAndAddListeners() {
        autoPlayCheckBox.setFont(PlainFontLabel.DEFAULT_FONT);
        autoPlayCheckBox.addActionListener(click -> {
            if (autoPlayCheckBox.isSelected()) {
                nextButton.setEnabled(false);
                cells.setDrawingEnabled(false);
                autoPlaySwingWorker = new AutoPlaySwingWorker();
                autoPlaySwingWorker.execute();
            } else {
                cells.setDrawingEnabled(true);
                autoPlaySwingWorker.cancel(false);
                nextButton.setEnabled(true);
            }
        });

        nextButton.addActionListener(click -> {
            game.nextGeneration();
            refreshGui();
        });
    }

    private void createLayout() {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(insertingSelectionComboBox);
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
    }

    private void createMenuBar() {
        JMenuBar jMenuBar = new JMenuBar();
        JMenu newGameMenu = new JMenu("New Game");
        JMenuItem blankGridMenuItem = new JMenuItem("Blank Grid");
        blankGridMenuItem.addActionListener(click -> {
            if (autoPlaySwingWorker != null) {
                autoPlaySwingWorker.cancel(true);
            }
            dispose();
            SwingUtilities.invokeLater(() -> new MainFrame(new Game(emptyMatrix(BOARD_HEIGHT, BOARD_WIDTH))));
        });
        newGameMenu.add(blankGridMenuItem);
        JMenuItem randomGridMenuItem = new JMenuItem("Random Grid");
        randomGridMenuItem.addActionListener(click -> {
            if (autoPlaySwingWorker != null) {
                autoPlaySwingWorker.cancel(true);
            }
            dispose();
            SwingUtilities.invokeLater(() -> new MainFrame(new Game(randomMatrix(BOARD_HEIGHT, BOARD_WIDTH))));
        });
        newGameMenu.add(randomGridMenuItem);
        jMenuBar.add(newGameMenu);
        setJMenuBar(jMenuBar);
    }

    private void refreshGui() {
        generationCountLabel.setText("generation " + game.getGenerationCount());
        statisticsPanel.update(game.getStatistics());
        cells.refreshCells();
    }

    private class AutoPlaySwingWorker extends SwingWorker<Object, Object> {

        @Override
        protected Object doInBackground() throws Exception {
            while (!isCancelled()) {
                game.nextGeneration();
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
