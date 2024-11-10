package co.edu.unbosque.view.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import co.edu.unbosque.view.util.login.utility.TextField;
import co.edu.unbosque.view.util.table.CustomTableModel;

public class PanelInventario extends JPanel{

    private TextField busqueda;
    private JButton crear;
    private JButton eliminar;
    private JButton actualizar;
    private JTable tablaInventario;
    private CustomTableModel tableModel;
    private JScrollPane scrollPane;

    public PanelInventario(){
        this.setBackground(Color.MAGENTA);
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.6;
        gbc.weighty = 0.05;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        busqueda = new TextField();
        busqueda.setHint("Busqueda");

        this.add(busqueda,gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        // gbc.fill = GridBagConstraints.NONE;

        crear = new JButton();
        crear.setText("Crear");
        crear.setPreferredSize(new Dimension(100,30));

        this.add(crear,gbc);


        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        eliminar = new JButton();
        eliminar.setText("Eliminar");
        eliminar.setPreferredSize(new Dimension(100,30));

        this.add(eliminar,gbc);


        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        actualizar = new JButton();
        actualizar.setText("Actualizar");
        actualizar.setPreferredSize(new Dimension(100,30));

        this.add(actualizar,gbc);


        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.95;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.BOTH;
        tablaInventario = new JTable(this.tableModel);
        scrollPane = new JScrollPane(tablaInventario);
        this.add(scrollPane,gbc);


        this.setVisible(false);
    }
    
}
