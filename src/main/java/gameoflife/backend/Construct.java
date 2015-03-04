package gameoflife.backend;

public enum Construct {

    CELL(
            "1"
    ),
    BLOCK(
            "11",
            "11"
    ),
    PULSAR(
            "0011100011100",
            "0000000000000",
            "1000010100001",
            "1000010100001",
            "1000010100001",
            "0011100011100",
            "0000000000000",
            "0011100011100",
            "1000010100001",
            "1000010100001",
            "1000010100001",
            "0000000000000",
            "0011100011100"
    ),
    GLIDER(
            "001",
            "101",
            "011"
    ),
    LWSS(
            "10010",
            "00001",
            "10001",
            "01111"
    ),
    GOSPER_GLIDER_GUN(
            "000000000000000000000000100000000000",
            "000000000000000000000010100000000000",
            "000000000000110000001100000000000011",
            "000000000001000100001100000000000011",
            "110000000010000010001100000000000000",
            "110000000010001011000010100000000000",
            "000000000010000010000000100000000000",
            "000000000001000100000000000000000000",
            "000000000000110000000000000000000000"
    )
    ;

    private final ConstructPattern pattern;

    private Construct(String... rows) {
        int[][] pattern = new int[rows.length][rows[0].length()];
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
        this.pattern = new ConstructPattern(pattern);
    }

    public ConstructPattern getPattern() {
        return pattern;
    }

}
