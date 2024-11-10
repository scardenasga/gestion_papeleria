package co.edu.unbosque.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;

import co.edu.unbosque.view.util.table.ButtonEditor;
import co.edu.unbosque.view.util.table.ButtonRenderer;
import co.edu.unbosque.view.util.table.CustomTableModel;


public class Prueba extends JFrame {

    JPanel p1;

    public Prueba() {
        this.setTitle("prueba");
        this.setSize(700, 550);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setMinimumSize(new Dimension(700, 550));

        this.p1 = new JPanel();
        this.p1.setBackground(Color.RED);
        this.p1.setLayout(new GridBagLayout()); // Usar GridBagLayout en el panel principal

        // Crear el JPanel superior con botones
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.LIGHT_GRAY);
        JButton button1 = new JButton("Botón 1");
        JButton button2 = new JButton("Botón 2");
        topPanel.add(button1);
        topPanel.add(button2);

        // Configurar restricciones para el JPanel superior
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        p1.add(topPanel, gbc);

        String[] cabeceras = new String[]{"nombre","apellido", "edad", "CREAR","Eliminar", "Actualizar"};


        // Crear la JTable y envolverla en un JScrollPane
        // JTable table = new JTable(new CustomTableModel(cabeceras, cargarInformacion())); 

        // table.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer());
        // table.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JCheckBox()));
        //    // Usar CheckBoxEditor en la última columna
        //    table.getColumnModel().getColumn(5).setCellEditor(new CheckBoxEditor());
        //    // Usar CheckBoxRenderer para mostrar los CheckBoxes
        //    table.getColumnModel().getColumn(5).setCellRenderer(new CheckBoxRenderer());


        // // table.setDefaultRenderer(Object.class, new CustomCellRenderer()); 
        // JScrollPane scrollPane = new JScrollPane(table);
        // // Configurar restricciones para el JScrollPane con el JTable
        // gbc.gridy = 1;
        // gbc.weighty = 1.0; // Expande verticalmente para ocupar el resto del espacio
        // gbc.fill = GridBagConstraints.BOTH;
        // p1.add(scrollPane, gbc);

        // Agregar el panel principal a la ventana
        this.add(p1);
        this.setVisible(true);
    }

    public static ArrayList<Object> cargarInformacion(){
        ArrayList<Object> datos = new ArrayList<>();
        Persona p = null;
        for (int i =  0; i < 13; i++) {
           p = new Persona("Persona"+i, "apellido"+(i-2), i);
           datos.add(p);
        }
        return datos;
    }
    public static void main(String[] args) {
        new Prueba();
    }
}

class CheckBoxEditor extends AbstractCellEditor implements TableCellEditor {
    private JCheckBox checkBox;
    private boolean selected= false;
    private String label;

    public CheckBoxEditor() {
        checkBox = new JCheckBox();
        checkBox.setHorizontalAlignment(SwingConstants.CENTER);
        checkBox.addItemListener(new ItemListener()
         {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED && !selected) {
                    selected = true;
                    System.out.println("Checkbox seleccionado"+label);
                } else {
                    System.out.println("Checkbox deseleccionado");
                    selected = false;
                }
                fireEditingStopped(); // Notificar a la tabla que la edición ha terminado
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        label = ""+row;
        checkBox.setSelected(value != null && (Boolean) value); // Establecer el valor del JCheckBox
        return checkBox;
    }

    @Override
    public Object getCellEditorValue() {
        return checkBox.isSelected(); // Retornar el estado del JCheckBox
    }
}

class CheckBoxRenderer extends DefaultTableCellRenderer {
    private JCheckBox checkBox;

    public CheckBoxRenderer() {
        checkBox = new JCheckBox();
        checkBox.setHorizontalAlignment(SwingConstants.CENTER);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        checkBox.setSelected(value != null && (Boolean) value); // Establecer el estado del JCheckBox
        return checkBox;
    }
}

