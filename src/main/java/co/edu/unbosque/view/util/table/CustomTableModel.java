package co.edu.unbosque.view.util.table;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class CustomTableModel extends AbstractTableModel {

    private String[] columnNames;

    private Object[][] data;

    private boolean editableAll = false;

    public CustomTableModel(String[] columnNames, List<Object[]> datos) {
        this.columnNames = columnNames;
        this.data = new Object[datos.size()][columnNames.length];

        for (int i = 0; i < datos.size(); i++) {
            
            Object[] obj = datos.get(i);

            // Asigna el valor de la columna 0 como "false" para el checkbox (puedes
            // inicializarlo como necesites)
            this.data[i][0] = Boolean.FALSE;

            for (int j = 1; j < columnNames.length; j++) {
                try {
                    this.data[i][j] = obj[j - 1];
                } catch (Exception e) {
                    this.data[i][j] = null;
                }
                // Si los datos en `obj` coinciden con el orden de columnas en `columnNames`,
                // asigna directamente
            }
        }
    }

    public CustomTableModel(String[] columnNames, List<Object[]> datos, boolean opcion){
        this.columnNames = columnNames;
        this.data = new Object[datos.size()][columnNames.length];
        for (int i = 0; i < datos.size(); i++) {
            Object[] obj = datos.get(i);
            this.data[i][0] = "";

            for (int j = 0; j < columnNames.length; j++) {
                try {
                    this.data[i][j] = obj[j];
                } catch (Exception e) {
                    this.data[i][j] = null;
                }
            }
        }
    }

    public void setEditable(boolean option){
        this.editableAll = option;
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
        if(!this.editableAll){
            return false;
        }
        return col == 0; 
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }
    

    public Object[] getRowData(int row){
        Object[] data =new Object[this.columnNames.length];
        for (int i = 0; i < this.getColumnCount(); i++) {
            data[i] = this.getValueAt(row, i);
        }
        return data;
    }
    public Object[] getColumnData(int column){
        Object[] data =new Object[this.getRowCount()];
        for (int i = 0; i < this.getRowCount(); i++) {
            data[i] = this.getValueAt(i, column);
        }
        return data;
    }

    public List<Object[]> getSelectedRowsForMe() {
        List<Object[]> selectedData = new ArrayList<>();
        for (int i = 0; i < getRowCount(); i++) {
            if((Boolean)(this.getValueAt(i, 0)) == Boolean.TRUE ){
                Object[] rowData = this.getRowData(i);// O lo que necesites para obtener los datos de
                selectedData.add(rowData);
            }  
        }
        return selectedData;
    }



    public List<Object[]> searchList(String txt){
        List<Object[]> resultado = new ArrayList<>();
        
        for (int i = 0; i < getRowCount(); i++) {
            Object[] aux = this.getRowData(i);
            if(Arrays.toString(aux).toLowerCase().contains(txt.toLowerCase())){
                Object[] aux2 = Arrays.copyOfRange(aux, 1, aux.length);
                resultado.add(aux2);
            }
        }
        for (Object[] i : resultado) {
            System.out.println(Arrays.toString(i));
        }
        System.out.println("--------------");
        return resultado;
    }
}
