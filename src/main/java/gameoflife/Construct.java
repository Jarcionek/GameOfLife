package gameoflife;

public enum Construct {

    BLOCK(
            "11;" +
            "11;"
    );

    private final int[][] pattern;

    Construct(String template) {
        String[] rows = template.split(";");
        pattern = new int[rows.length][rows[0].length()];
        int y = 0;
        for (String row : rows) {
            int x = 0;
            for (char c : row.toCharArray()) {
                if (c == '1') {
                    pattern[y][x] = 1;
                } else if (c == '0') {
                    pattern[y][x] = 0;
                } else {
                    throw new IllegalArgumentException();
                }
                x++;
            }
            y++;
        }
    }

    public int[][] getPattern() {
        return pattern;
    }

}
