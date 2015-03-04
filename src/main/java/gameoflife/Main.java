package gameoflife;

import static gameoflife.frontend.MainFrame.newFrameWithRandomMatrix;

public class Main {

    public static void main(String[] args) {
        enableWorkAroundSwingBugs();

        newFrameWithRandomMatrix();
    }

    private static void enableWorkAroundSwingBugs() {
        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
    }

    //TODO Jarek: show trail when placing constructs (e.g. in what direction a gun will be shooting)
    //TODO Jarek: add scroll pane for the grid to allow larger grids
    //TODO Jarek: add more constructs

}
