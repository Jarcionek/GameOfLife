package gameoflife;

import static gameoflife.Config.createNewFrame;

public class Main {

    public static void main(String[] args) {
        enableWorkAroundSwingBugs();

        createNewFrame();
    }

    private static void enableWorkAroundSwingBugs() {
        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
    }

}
