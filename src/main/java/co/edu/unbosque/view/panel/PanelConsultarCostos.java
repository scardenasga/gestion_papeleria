package co.edu.unbosque.view.panel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.time.LocalDate;

import co.edu.unbosque.model.service.Contabilidad;

public class PanelConsultarCostos extends JPanel {
    private JButton botonConsultar; // Botón para consultar
    private JTable tablaCostos; // Tabla para mostrar los costos
    private JScrollPane scrollPane; // Panel de desplazamiento para la tabla
    private Contabilidad contabilidad; // Instancia de la clase Contabilidad

    public PanelConsultarCostos(Contabilidad contabilidad) {
        this.contabilidad = contabilidad; // Inicializa la clase Contabilidad
        
        // Configura el layout del panel
        setLayout(new BorderLayout());

        // Crea el botón "Consultar"
        botonConsultar = new JButton("Consultar");
        add(botonConsultar, BorderLayout.NORTH); // Agrega el botón en la parte superior

        // Configura la tabla para mostrar los costos
        String[] columnNames = {"Costo Total Compras", "Costo Total Ventas"}; // Cambia según tus datos
        Object[][] data = {{BigDecimal.ZERO, BigDecimal.ZERO}}; // Inicializa con valores cero
        tablaCostos = new JTable(data, columnNames);
        scrollPane = new JScrollPane(tablaCostos);
        
        // Agrega la tabla en el centro
        add(scrollPane, BorderLayout.CENTER);

        // Añadir ActionListener al botón
        botonConsultar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                consultarCostos();
            }
        });
    }

    private void consultarCostos() {
        // Definir las fechas para la consulta (puedes modificar esto según sea necesario)
        LocalDate fechaInicio = LocalDate.now().minusMonths(1); // Por ejemplo, el mes pasado
        LocalDate fechaFin = LocalDate.now();

        // Llamar al método consultarCostos de la clase Contabilidad
        contabilidad.consultarCostos(fechaInicio, fechaFin)
            .thenAccept(costos -> {
                // Actualizar la tabla con los costos obtenidos
                BigDecimal costoTotalCompras = costos.getCostoTotalCompras();
                BigDecimal costoTotalVentas = costos.getCostoTotalVentas();
                Object[][] data = {{costoTotalCompras, costoTotalVentas}};
                tablaCostos.setModel(new javax.swing.table.DefaultTableModel(data, new String[]{"Costo Total Compras", "Costo Total Ventas"}));
                revalidate();
                repaint();
            })
            .exceptionally(ex -> {
                // Manejo de errores
                JOptionPane.showMessageDialog(this, "Error al consultar los costos: " + ex.getMessage());
                return null;
            });
    }
}
