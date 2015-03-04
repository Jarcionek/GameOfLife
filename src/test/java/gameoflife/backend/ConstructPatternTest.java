package gameoflife.backend;

import org.junit.Test;

import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.hamcrest.CoreMatchers.is;

public class ConstructPatternTest {

    private final int[][] PATTERN = new int[][] {
            {0, 0},
            {0, 1},
            {0, 1},
    };

    private final ConstructPattern constructPattern = new ConstructPattern(PATTERN);

    @Test
    public void orientation_0_IsDefault() {
        assertThat(constructPattern.inOrientation(0), is(sameBeanAs(new int[][] {
                {0, 0},
                {0, 1},
                {0, 1},
        })));
    }

    @Test
    public void orientation_1_IsFlippedVertically() {
        assertThat(constructPattern.inOrientation(1), is(sameBeanAs(new int[][] {
                {0, 0},
                {1, 0},
                {1, 0},
        })));
    }
    
    @Test
    public void orientation_2_IsRotatedClockwise() {
        assertThat(constructPattern.inOrientation(2), is(sameBeanAs(new int[][] {
                {0, 0, 0},
                {1, 1, 0},
        })));
    }

    @Test
    public void orientation_3_IsRotatedClockwiseAndFlippedHorizontally() {
        assertThat(constructPattern.inOrientation(3), is(sameBeanAs(new int[][] {
                {1, 1, 0},
                {0, 0, 0},
        })));
    }

    @Test
    public void orientation_4_IsRotatedAround() {
        assertThat(constructPattern.inOrientation(4), is(sameBeanAs(new int[][] {
                {1, 0},
                {1, 0},
                {0, 0},
        })));
    }

    @Test
    public void orientation_5_IsRotatedAroundAndFlippedVertically() {
        assertThat(constructPattern.inOrientation(5), is(sameBeanAs(new int[][] {
                {0, 1},
                {0, 1},
                {0, 0},
        })));
    }

    @Test
    public void orientation_6_IsRotatedAntiClockwise() {
        assertThat(constructPattern.inOrientation(6), is(sameBeanAs(new int[][] {
                {0, 1, 1},
                {0, 0, 0},
        })));
    }

    @Test
    public void orientation_7_IsRotatedAntiClockwiseAndFlippedHorizontally() {
        assertThat(constructPattern.inOrientation(7), is(sameBeanAs(new int[][] {
                {0, 0, 0},
                {0, 1, 1},
        })));
    }

}
