package co.edu.unbosque.view.util.table;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class CustomCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
        Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

        // Personalizar colores
        if (col == 2) { // Cambia el fondo si está en la columna "Edad"
            int edad = (int) value;
            cell.setBackground(edad >= 30 ? Color.RED : Color.GREEN);
        } else {
            cell.setBackground(Color.WHITE);
        }

        if (isSelected) {
            cell.setBackground(Color.CYAN);
        }
        
        return cell;
    }
}

