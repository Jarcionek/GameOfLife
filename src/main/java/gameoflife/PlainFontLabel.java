package gameoflife;

import javax.swing.JLabel;
import java.awt.Font;

public class PlainFontLabel extends JLabel {

    public static final Font DEFAULT_FONT = new JLabel().getFont().deriveFont(Font.PLAIN);

    public PlainFontLabel(String text) {
        super(text);
        setFont(DEFAULT_FONT);
    }

    public PlainFontLabel(String text, int size) {
        super(text);
        setFont(new Font(DEFAULT_FONT.getName(), DEFAULT_FONT.getStyle(), size));
    }

}
