package co.edu.unbosque.view.util.table;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

public class ButtonRenderer implements TableCellRenderer {


    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {

                JButton button = new JButton();
                button.setText((value == null) ? "" : value.toString());
        
                // Ajustar el tamaño del botón
                button.setPreferredSize(new Dimension(30, 30));
                
                // Establecer estilos del botón (opcional)
                button.setHorizontalAlignment(SwingConstants.CENTER);
                button.setVerticalAlignment(SwingConstants.CENTER);
        
                // Cambiar el color de fondo del botón si la fila está seleccionada
                if (isSelected) {
                    button.setBackground(table.getSelectionBackground());
                    button.setForeground(table.getSelectionForeground());
                } else {
                    button.setBackground(table.getBackground());
                    button.setForeground(table.getForeground());
                }
        
                return button; // Devolver el botón para que se dibuje en la celda        
    }

}
