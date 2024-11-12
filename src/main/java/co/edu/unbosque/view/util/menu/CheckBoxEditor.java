package co.edu.unbosque.view.util.menu;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellEditor;


public class CheckBoxEditor extends AbstractCellEditor implements TableCellEditor{
    
     private JCheckBox checkBox;

    public CheckBoxEditor() {
        checkBox = new JCheckBox();
        checkBox.setHorizontalAlignment(SwingConstants.CENTER);
        checkBox.setPreferredSize(new Dimension(20,20));
        checkBox.setMaximumSize(new Dimension(40,40));
        checkBox.addItemListener(new ItemListener()
         {
            @Override
            public void itemStateChanged(ItemEvent e) {
               
                fireEditingStopped(); // Notificar a la tabla que la edición ha terminado
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        checkBox.setSelected((Boolean)value); // Establecer el valor del JCheckBox
        return checkBox;
    }

    @Override
    public Object getCellEditorValue() {
        return checkBox.isSelected(); // Retornar el estado del JCheckBox
    }
}
