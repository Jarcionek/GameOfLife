package gameoflife;

import org.junit.Test;

import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.hamcrest.CoreMatchers.is;


public class BoardTest {

    @Test
    public void returnsStringRepresentationOfTheBoard() {
        Board board = new Board(new int[][] {
                {1, 0},
                {0, 1},
        });

        assertThat(board.asString(), is(sameBeanAs("10\n01\n")));
    }

    @Test
    public void liveCellWithFewerThanTwoLiveNeighboursDies() {
        Board board = new Board(new int[][] {
                {0, 0, 0, 0, 0, 0},
                {0, 1, 0, 0, 0, 0},
                {0, 1, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 0},
        });

        board.nextGeneration();

        assertThat(board.asString(), is(sameBeanAs(new Board(new int[][]{
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
        }).asString())));
    }

    @Test
    public void liveCellWithMoreThanThreeLiveNeighboursDies() {
        Board board = new Board(new int[][] {
                {0, 0, 0, 0, 0},
                {0, 1, 1, 1, 0},
                {0, 1, 1, 1, 0},
                {0, 0, 0, 0, 0},
        });

        board.nextGeneration();

        assertThat(board.asString(), is(sameBeanAs(new Board(new int[][]{
                {0, 0, 0, 0, 0},
                {0, 1, 0, 1, 0},
                {0, 1, 0, 1, 0},
                {0, 0, 0, 0, 0},
        }).asString())));
    }

}