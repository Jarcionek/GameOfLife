package gameoflife;

import org.junit.Test;

import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.hamcrest.CoreMatchers.is;

public class ConstructPatternOrientationTest {

    private final ConstructPatternOrientation cpo = new ConstructPatternOrientation();

    private final int[][] defaultPattern = new int[][] {
            {0, 0},
            {0, 1},
            {0, 1},
    };

    @Test
    public void orientation_0_IsDefault() {
        assertThat(cpo.inOrientation(0, defaultPattern), is(sameBeanAs(new int[][] {
                {0, 0},
                {0, 1},
                {0, 1},
        })));
    }

    @Test
    public void orientation_1_IsFlippedVertically() {
        assertThat(cpo.inOrientation(1, defaultPattern), is(sameBeanAs(new int[][] {
                {0, 0},
                {1, 0},
                {1, 0},
        })));
    }
    
    @Test
    public void orientation_2_IsRotatedClockwise() {
        assertThat(cpo.inOrientation(2, defaultPattern), is(sameBeanAs(new int[][] {
                {0, 0, 0},
                {1, 1, 0},
        })));
    }

    @Test
    public void orientation_3_IsRotatedClockwiseAndFlippedHorizontally() {
        assertThat(cpo.inOrientation(3, defaultPattern), is(sameBeanAs(new int[][] {
                {1, 1, 0},
                {0, 0, 0},
        })));
    }

    @Test
    public void orientation_4_IsRotatedAround() {
        assertThat(cpo.inOrientation(4, defaultPattern), is(sameBeanAs(new int[][] {
                {1, 0},
                {1, 0},
                {0, 0},
        })));
    }

    @Test
    public void orientation_5_IsRotatedAroundAndFlippedVertically() {
        assertThat(cpo.inOrientation(5, defaultPattern), is(sameBeanAs(new int[][] {
                {0, 1},
                {0, 1},
                {0, 0},
        })));
    }

    @Test
    public void orientation_6_IsRotatedAntiClockwise() {
        assertThat(cpo.inOrientation(6, defaultPattern), is(sameBeanAs(new int[][] {
                {0, 1, 1},
                {0, 0, 0},
        })));
    }

    @Test
    public void orientation_7_IsRotatedAntiClockwiseAndFlippedHorizontally() {
        assertThat(cpo.inOrientation(7, defaultPattern), is(sameBeanAs(new int[][] {
                {0, 0, 0},
                {0, 1, 1},
        })));
    }

}
