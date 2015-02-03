package gameoflife;

public enum Construct {

    BLOCK(
            "11;" +
            "11;"
    );

    private final String template;

    Construct(String template) {
        this.template = template;
    }

}
