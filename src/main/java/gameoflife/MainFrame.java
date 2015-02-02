package gameoflife;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CancellationException;

import static java.lang.Integer.parseInt;

public class MainFrame extends JFrame {

    private static final int CELL_SIZE = 10;
    private static final int BOARD_WIDTH = 100;
    private static final int BOARD_HEIGHT = 60;

    private final Board board;
    private final GridOfLabels cells;

    private final JLabel generationCountLabel = new JLabel("0");
    private int generationCount = 0;

    private final JCheckBox autoPlayCheckBox = new JCheckBox("autoplay (delays in ms):");
    private final JTextField autoPlayDelayField = new JTextField("100", 10);
    private final JButton nextButton = new JButton("next");

    public MainFrame() {
        super("Conway's Game of Life");

        int[][] initBoard = new int[BOARD_HEIGHT][BOARD_WIDTH];
        Random random = new Random();
        for (int y = 0; y < BOARD_HEIGHT; y++) {
            for (int x = 0; x < BOARD_WIDTH; x++) {
                if (random.nextInt(3) > 1) {
                    initBoard[y][x] = 1 + random.nextInt(2);
                } else {
                    initBoard[y][x] = 0;
                }
            }
        }

        Matrix matrix = new Matrix(initBoard);
        this.board = new Board(matrix);
        this.cells = new GridOfLabels(CELL_SIZE, matrix);

        createComponents();
        refreshGui();

        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void createComponents() {
        autoPlayCheckBox.setFont(new Font("Arial", Font.PLAIN, autoPlayCheckBox.getFont().getSize()));
        generationCountLabel.setFont(new Font("Arial", Font.PLAIN, generationCountLabel.getFont().getSize()));

        autoPlayCheckBox.addActionListener(new ActionListener() {

            private SwingWorker<Object, Object> swingWorker;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (autoPlayCheckBox.isSelected()) {
                    nextButton.setEnabled(false);
                    swingWorker = new SwingWorker<Object, Object>() {
                        @Override
                        protected Object doInBackground() throws Exception {
                            while (!isCancelled()) {
                                nextGeneration();
                                publish(new Object());
                                Thread.sleep(parseInt(autoPlayDelayField.getText()));
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
                    };
                    cells.setMouseListenerEnabled(false);
                    swingWorker.execute();
                } else {
                    swingWorker.cancel(false);
                    cells.setMouseListenerEnabled(true);
                    nextButton.setEnabled(true);
                }
            }
        });

        nextButton.addActionListener(click -> {
            nextGeneration();
            refreshGui();
        });

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(generationCountLabel);
        buttonsPanel.add(nextButton);
        buttonsPanel.add(autoPlayCheckBox);
        buttonsPanel.add(autoPlayDelayField);

        JPanel centralPanel = new JPanel(new BorderLayout());
        centralPanel.add(cells, BorderLayout.CENTER);
        centralPanel.add(buttonsPanel, BorderLayout.SOUTH);
        setContentPane(centralPanel);
    }

    private void nextGeneration() {
        board.nextGeneration();
        generationCount++;
    }

    private void refreshGui() {
        generationCountLabel.setText("generation " + generationCount);
        cells.refreshCells();
    }

}
