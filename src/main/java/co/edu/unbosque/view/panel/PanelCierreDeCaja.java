package co.edu.unbosque.view.panel;

import co.edu.unbosque.model.entity.Venta;
import co.edu.unbosque.model.service.Contabilidad;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class PanelCierreDeCaja extends JPanel {
    private JTable tablaVentas; // Tabla para mostrar las últimas ventas
    private JScrollPane scrollPane; // Panel de desplazamiento para la tabla
    private JButton botonConfirmar; // Botón para confirmar el cierre de caja
    private Contabilidad contabilidad;

    public PanelCierreDeCaja(Contabilidad contabilidad) {
        // Configura el layout del panel
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Eliminar el JSpinner para la selección de fecha
        // Agregar un JLabel informativo en su lugar
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(new JLabel("Cierre de Caja para la fecha actual:"), gbc);
        
        // Inicializar la tabla para mostrar las últimas ventas
        String[] columnNames = {"ID Venta", "ID Persona", "Total Venta", "Fecha Venta", "ID Asiento"};
        tablaVentas = new JTable(new Object[0][5], columnNames); // Inicializar con cero filas
        scrollPane = new JScrollPane(tablaVentas);
        
        // Agregar la tabla en la segunda fila
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2; // Ocupa ambas columnas
        gbc.weighty = 1.0; // Permitir que la tabla use el espacio restante
        gbc.fill = GridBagConstraints.BOTH; // Llenar el espacio
        add(scrollPane, gbc);

        // Crea y agrega el botón de confirmar cierre de caja
        botonConfirmar = new JButton("Confirmar Cierre de Caja");
        
        // Configura las restricciones para el botón
        gbc.gridx = 0; // Regresar a la primera columna
        gbc.gridy = 2; // siguiente fila
        gbc.gridwidth = 2; // Ocupa ambas columnas
        gbc.anchor = GridBagConstraints.EAST; // alineado a la derecha
        gbc.weighty = 0; // sin peso vertical
        add(botonConfirmar, gbc);

        // Acción del botón de confirmar cierre de caja
        botonConfirmar.addActionListener(e -> {
            realizarCierreDeCaja(contabilidad); // Llama al método sin pasar una fecha
        });
    }

    private void realizarCierreDeCaja(Contabilidad contabilidad) {
        contabilidad.realizarCierreCaja()
            .thenAccept(result -> {
                // Mostrar mensaje de éxito
                JOptionPane.showMessageDialog(this, "Cierre de caja realizado para la fecha: " + LocalDate.now());
                // Verificar registros en la tabla venta
                verificarRegistrosEnVenta();
            })
            .exceptionally(ex -> {
                // Manejo de errores
                JOptionPane.showMessageDialog(this, "Error al realizar el cierre de caja: " + ex.getMessage());
                return null;
            });
    }
    
    private void verificarRegistrosEnVenta() {
        // Obtener la fecha actual
        LocalDate fechaActual = LocalDate.now();
    
        // Consultar las ventas para la fecha actual
        contabilidad.consultarVentasPorFecha(fechaActual)
            .thenAccept(ventas -> {
                // Crear los datos para la tabla
                Object[][] data = new Object[ventas.size()][5];
    
                for (int i = 0; i < ventas.size(); i++) {
                    Venta venta = ventas.get(i);
                    data[i][0] = venta.getIdVenta(); // ID Venta
                    data[i][1] = venta.getPersona() != null ? venta.getPersona().getIdentificacion() : null; // ID Persona (usando 'identificacion')
                    data[i][2] = venta.getTotalVenta(); // Total Venta
                    data[i][3] = venta.getFechaVenta(); // Fecha Venta
                    data[i][4] = venta.getAsientoContable() != null ? venta.getAsientoContable().getIdAsiento() : null; // ID Asiento
                }
    
                // Actualizar la tabla existente con los nuevos datos
                tablaVentas.setModel(new javax.swing.table.DefaultTableModel(data, new String[]{"ID Venta", "ID Persona", "Total Venta", "Fecha Venta", "ID Asiento"}));
                revalidate();
                repaint();
            })
            .exceptionally(ex -> {
                // Manejo de errores
                JOptionPane.showMessageDialog(this, "Error al consultar las ventas: " + ex.getMessage());
                return null;
            });
    }
}
