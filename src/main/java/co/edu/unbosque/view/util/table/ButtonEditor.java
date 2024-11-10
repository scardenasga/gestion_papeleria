package co.edu.unbosque.view.util.table;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonEditor extends DefaultCellEditor {
    private JButton button;
    private String label;
    private boolean isPushed;

    public ButtonEditor(JCheckBox checkBox) {
        super(checkBox);
        button = new JButton();
        button.setOpaque(true);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped(); // Para detener la edición
                // Aquí puedes agregar la acción que deseas realizar al presionar el botón
                System.out.println("Botón presionado en fila " + button.getActionCommand());
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        button.setActionCommand(String.valueOf(row)); // Guardar la fila en el botón
        isPushed = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            // Puedes devolver el valor que quieras aquí, o solo null
        }
        isPushed = false;
        return label;
    }
}
