package gameoflife.frontend;

import javax.swing.JComboBox;

public class TypeSafeJComboBox<E> extends JComboBox<E> {

    public TypeSafeJComboBox(E[] items) {
        super(items);
    }

    @SuppressWarnings("unchecked")
    @Override
    public E getSelectedItem() {
        return (E) super.getSelectedItem();
    }

}
