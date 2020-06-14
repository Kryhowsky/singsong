package Files;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;

public class SongGenreCellEditor extends AbstractCellEditor implements TableCellEditor {

    private JComboBox jcb;

    public SongGenreCellEditor() {
        this.jcb = new JComboBox();
        jcb.addItem(SongGenre.DISCO);
        jcb.addItem(SongGenre.ROCK);
        jcb.addItem(SongGenre.POP);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return jcb;
    }

    @Override
    public Object getCellEditorValue() {
        return jcb.getSelectedIndex();
    }
}
