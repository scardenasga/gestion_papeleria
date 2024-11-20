package co.edu.unbosque.view.panel;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import co.edu.unbosque.model.service.Contabilidad;

public class PanelRegistrarCostosYGastos extends JPanel {
    private JTextField campoCostoDescripcion;
    private JTextField campoCostoMonto;
    private JButton botonRegistrarCosto;

    private JTextField campoGastoDescripcion;
    private JTextField campoGastoMonto;
    private JButton botonRegistrarGasto;

    private Contabilidad contabilidad; // Instancia de la clase Contabilidad

    public PanelRegistrarCostosYGastos() {
        // Inicializa la clase Contabilidad
        this.contabilidad = new Contabilidad();

        // Configura el layout del panel
        setLayout(new GridLayout(2, 1)); // Dos filas, una para cada formulario

        // Panel para registrar costos
        JPanel panelCostos = new JPanel(new GridBagLayout());
        panelCostos.setBorder(BorderFactory.createTitledBorder("Registrar Costos"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelCostos.add(new JLabel("Descripción Costo:"), gbc);

        gbc.gridx = 1;
        campoCostoDescripcion = new JTextField(20);
        panelCostos.add(campoCostoDescripcion, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelCostos.add(new JLabel("Monto Costo:"), gbc);

        gbc.gridx = 1;
        campoCostoMonto = new JTextField(20);
        panelCostos.add(campoCostoMonto, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        botonRegistrarCosto = new JButton("Registrar Costo");
        panelCostos.add(botonRegistrarCosto, gbc);
        
        // Acción del botón de registrar costo
        botonRegistrarCosto.addActionListener(e -> {
            String descripcion = campoCostoDescripcion.getText();
            String montoStr = campoCostoMonto.getText();
            try {
                BigDecimal monto = new BigDecimal(montoStr);
                contabilidad.registrarCosto(descripcion, monto)
                    .thenRun(() -> JOptionPane.showMessageDialog(this, "Costo registrado exitosamente."));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El monto debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(panelCostos); // Agrega el panel de costos al panel principal

        // Panel para registrar gastos
        JPanel panelGastos = new JPanel(new GridBagLayout());
        panelGastos.setBorder(BorderFactory.createTitledBorder("Registrar Gastos"));

        gbc = new GridBagConstraints(); // Reiniciar las restricciones
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelGastos.add(new JLabel("Descripción Gasto:"), gbc);

        gbc.gridx = 1;
        campoGastoDescripcion = new JTextField(20);
        panelGastos.add(campoGastoDescripcion, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelGastos.add(new JLabel("Monto Gasto:"), gbc);

        gbc.gridx = 1;
        campoGastoMonto = new JTextField(20);
        panelGastos.add(campoGastoMonto, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        botonRegistrarGasto = new JButton("Registrar Gasto");
        panelGastos.add(botonRegistrarGasto, gbc);
        
        // Acción del botón de registrar gasto
        botonRegistrarGasto.addActionListener(e -> {
            String descripcion = campoGastoDescripcion.getText();
            String montoStr = campoGastoMonto.getText();
            try {
                BigDecimal monto = new BigDecimal(montoStr);
                contabilidad.registrarGasto(descripcion, monto)
                    .thenRun(() -> JOptionPane.showMessageDialog(this, "Gasto registrado exitosamente."));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El monto debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(panelGastos); // Agrega el panel de gastos al panel principal
    }

}


