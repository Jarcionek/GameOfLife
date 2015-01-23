package gameoflife;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CancellationException;

import static java.lang.Integer.parseInt;

public class MainFrame extends JFrame {

    private static final int CELL_SIZE = 10;
    private static final int BOARD_WIDTH = 100;
    private static final int BOARD_HEIGHT = 60;

    private final Board board;

    private final JComponent[][] cells;
    private final JLabel generationCountLabel = new JLabel("0");
    private int generationCount = 0;

    private final JCheckBox autoPlayCheckBox = new JCheckBox("autoplay (delays in ms):");
    private final JTextField autoPlayDelayField = new JTextField("100", 10);
    private final JButton nextButton = new JButton("next");

    public MainFrame() {
        super("Conway's Game of Life");

        this.board = initBoard();
        this.cells = new JLabel[BOARD_HEIGHT][BOARD_WIDTH];

        createComponents();
        refreshCells();

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
                            refreshCells();
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
                    swingWorker.execute();
                } else {
                    swingWorker.cancel(false);
                    nextButton.setEnabled(true);
                }
            }
        });

        nextButton.addActionListener(click -> {
            nextGeneration();
            refreshCells();
        });

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(generationCountLabel);
        buttonsPanel.add(nextButton);
        buttonsPanel.add(autoPlayCheckBox);
        buttonsPanel.add(autoPlayDelayField);

        JPanel boardPanel = new JPanel(new GridLayout(BOARD_HEIGHT, BOARD_WIDTH));
        for (int y = 0; y < BOARD_HEIGHT; y++) {
            for (int x = 0; x < BOARD_WIDTH; x++) {
                boardPanel.add(cells[y][x] = new JLabel());
                cells[y][x].setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));
                cells[y][x].setOpaque(true);
                setBorder(y, x);
                final int fy = y;
                final int fx = x;
                cells[y][x].addMouseMotionListener(new MouseAdapter() {
                    @Override
                    public void mouseMoved(MouseEvent e) {
                        if (!nextButton.isEnabled()) { // i.e. if swing worker is running
                            return;
                        }

                        if (e.getModifiers() == InputEvent.SHIFT_MASK) {
                            board.setDead(fy, fx);
                        } else if (e.getModifiers() == InputEvent.CTRL_MASK) {
                            board.setLive(fy, fx, 1);
                        } else if (e.getModifiers() == InputEvent.ALT_MASK) {
                            board.setLive(fy, fx, 2);
                        }
                        setColorOfCell(fy, fx);
                        cells[fy][fx].repaint();
                    }
                });
            }
        }

        JPanel centralPanel = new JPanel(new BorderLayout());
        centralPanel.add(boardPanel, BorderLayout.CENTER);
        centralPanel.add(buttonsPanel, BorderLayout.SOUTH);
        setContentPane(centralPanel);
    }

    private void setBorder(int y, int x) {
        Color color = Color.darkGray;
        if (x == BOARD_WIDTH - 1 && y == BOARD_HEIGHT - 1) {
            cells[y][x].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, color));
        } else if (x == BOARD_WIDTH - 1) {
            cells[y][x].setBorder(BorderFactory.createMatteBorder(1, 1, 0, 1, color));
        } else if (y == BOARD_HEIGHT - 1) {
            cells[y][x].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 0, color));
        } else {
            cells[y][x].setBorder(BorderFactory.createMatteBorder(1, 1, 0, 0, color));
        }
    }

    private void nextGeneration() {
        board.nextGeneration();
        generationCount++;
    }

    private void refreshCells() {
        generationCountLabel.setText("generation " + generationCount);

        for (int y = 0; y < BOARD_HEIGHT; y++) {
            for (int x = 0; x < BOARD_WIDTH; x++) {
                setColorOfCell(y, x);
            }
        }
        this.repaint();
    }

    private void setColorOfCell(int y, int x) {
        switch (board.getCellColor(y, x)) {
            case 2: cells[y][x].setBackground(Color.red); break;
            case 1: cells[y][x].setBackground(Color.blue); break;
            case 0: cells[y][x].setBackground(Color.white); break;
            default: throw new IllegalArgumentException();
        }
    }

    private Board initBoard() {
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

        return new Board(initBoard);
    }

}
