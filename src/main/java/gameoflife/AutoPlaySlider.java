package gameoflife;

import javax.swing.JLabel;
import javax.swing.JSlider;
import java.util.Dictionary;
import java.util.Hashtable;

public class AutoPlaySlider extends JSlider {

    public AutoPlaySlider() {
        super(1, 201, 100);

        Dictionary<Integer, JLabel> dictionary = new Hashtable<>();
        dictionary.put(1, new PlainFontLabel("1ms", 10));
        dictionary.put(100, new PlainFontLabel("100ms", 10));
        dictionary.put(200, new PlainFontLabel("200ms", 10));

        setMajorTickSpacing(10);
        setPaintTicks(true);
        setLabelTable(dictionary);
        setPaintLabels(true);
    }

}
