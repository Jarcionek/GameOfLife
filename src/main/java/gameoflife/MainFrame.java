package gameoflife;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Random;

public class MainFrame extends JFrame {

    private static final int CELL_SIZE = 10;
    private static final int BOARD_WIDTH = 100;
    private static final int BOARD_HEIGHT = 60;

    private final Board board;

    private final JComponent[][] cells;

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
        JButton nextButton = new JButton("next");
        nextButton.addActionListener(click -> nextGeneration());

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(nextButton);

        JPanel boardPanel = new JPanel(new GridLayout(BOARD_HEIGHT, BOARD_WIDTH));
        for (int y = 0; y < BOARD_HEIGHT; y++) {
            for (int x = 0; x < BOARD_WIDTH; x++) {
                boardPanel.add(cells[y][x] = new JLabel());
                cells[y][x].setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));
                cells[y][x].setOpaque(true);
                setBorder(y, x);
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
        refreshCells();
    }

    private void refreshCells() {
        for (int y = 0; y < BOARD_HEIGHT; y++) {
            for (int x = 0; x < BOARD_WIDTH; x++) {
                if (board.isCellLive(y, x)) {
                    cells[y][x].setBackground(Color.black);
                } else {
                    cells[y][x].setBackground(Color.white);
                }
            }
        }
        this.repaint();
    }

    private Board initBoard() {
        int[][] initBoard = new int[BOARD_HEIGHT][BOARD_WIDTH];

        Random random = new Random();
        for (int y = 0; y < BOARD_HEIGHT; y++) {
            for (int x = 0; x < BOARD_WIDTH; x++) {
                initBoard[y][x] = random.nextInt(2);
            }
        }

        return new Board(initBoard);
    }

}
