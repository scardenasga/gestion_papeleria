package co.edu.unbosque.view.util.menu;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class CheckBoxRenderer extends DefaultTableCellRenderer {
    
    private JCheckBox checkBox;

    public CheckBoxRenderer() {
        checkBox = new JCheckBox();
        checkBox.setHorizontalAlignment(SwingConstants.CENTER);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        checkBox.setSelected((Boolean)value); // Establecer el estado del JCheckBox
        return checkBox;
    }
}
