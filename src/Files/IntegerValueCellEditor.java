package Files;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class IntegerValueCellEditor extends AbstractCellEditor implements TableCellEditor {

    private JTextField jtf;

    public IntegerValueCellEditor() {
        this.jtf = new JTextField();
        jtf.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() >= '0' && e.getKeyChar() <= '9' || e.getKeyCode() == 8) {
                    jtf.setEditable(true);
                    jtf.requestFocus();
                } else {
                    jtf.setEditable(false);
                }
            }
        });
    }

    @Override
    public Object getCellEditorValue() {
        return jtf.getText();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        jtf.setText(String.valueOf(value));
        return jtf;
    }
}
