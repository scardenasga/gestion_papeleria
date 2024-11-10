package co.edu.unbosque.view.util.table;

import java.lang.reflect.Field;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class CustomTableModel extends AbstractTableModel {

    private String[] columnNames;

    private Object[][] data;

    public CustomTableModel(String[] columnNames, ArrayList<Object> datos) {
        this.columnNames = columnNames;
        this.data = new Object[datos.size()][columnNames.length];

        for (int i = 0; i < datos.size(); i++) {
            Object obj = datos.get(i);
            for (int j = 0; j < columnNames.length; j++) {
                try {
                    Field field = obj.getClass().getDeclaredField(columnNames[j]);
                    field.setAccessible(true); // Permitir acceso a campos privados
                    this.data[i][j] = String.valueOf(field.get(obj));
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    // e.printStackTrace();
                    this.data[i][j] = null; // En caso de error, mostrar "N/A"
                }
            }
        }
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return col > 0; // Permite editar solo las celdas de las columnas "Nombre" y "Edad"
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }
}
