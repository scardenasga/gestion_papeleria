package co.edu.unbosque.view.panel;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import co.edu.unbosque.model.dao.VentaDAO;
import co.edu.unbosque.model.entity.Venta;

public class PanelConsultarVentas extends JPanel {
    private JButton botonConsultar; // Botón para consultar
    private JTable tablaVentas; // Tabla para mostrar las ventas
    private JScrollPane scrollPane; // Panel de desplazamiento para la tabla
    private JSpinner fechaSpinner; // Spinner para seleccionar la fecha
    private VentaDAO ventaDAO; // DAO para consultar ventas

    public PanelConsultarVentas() {
        // Inicializa el DAO
        this.ventaDAO = new VentaDAO();

        // Configura el layout del panel
        setLayout(new BorderLayout());

        // Crea el botón "Consultar"
        botonConsultar = new JButton("Consultar");
        add(botonConsultar, BorderLayout.CENTER); // Agrega el botón en el centro

        // Configura el JSpinner para la selección de fecha
        fechaSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(fechaSpinner, "yyyy/MM/dd");
        fechaSpinner.setEditor(dateEditor);
        fechaSpinner.setValue(new Date()); // Establece la fecha actual como valor inicial

        // Crea un panel para el campo de fecha y el botón
        JPanel panelSuperior = new JPanel();
        panelSuperior.add(new JLabel("Seleccionar Fecha:"));
        panelSuperior.add(fechaSpinner);
        panelSuperior.add(botonConsultar);

        // Agrega el panel superior al panel principal
        add(panelSuperior, BorderLayout.NORTH);

        // Configura la tabla para mostrar las ventas
        String[] columnNames = {"ID", "Descripción", "Total Venta", "Fecha Venta"};
        Object[][] data = {}; // Inicialmente vacío
        tablaVentas = new JTable(data, columnNames);
        scrollPane = new JScrollPane(tablaVentas);
        
        // Agrega la tabla en la parte inferior
        add(scrollPane, BorderLayout.CENTER);

        // Agrega el ActionListener al botón
        // Agrega el ActionListener al botón
        botonConsultar.addActionListener(e -> {
            Date fechaSeleccionada = (Date) fechaSpinner.getValue();
            LocalDate localDate = fechaSeleccionada.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(); // Convertir a LocalDate
            
            // Consultar ventas en un hilo separado
            ventaDAO.findByAtribute("fechaVenta", localDate)
                .thenAccept(this::updateTable); // Actualiza la tabla con los resultados
        });
    }

    private void updateTable(List<Venta> ventas) {
        // Limpiar la tabla existente
        String[] columnNames = {"ID", "Descripción", "Total Venta", "Fecha Venta"};
        Object[][] data = new Object[ventas.size()][4]; // 4 columnas según los datos que deseas mostrar

        for (int i = 0; i < ventas.size(); i++) {
            Venta venta = ventas.get(i);
            data[i][0] = venta.getIdVenta(); // Usar getIdVenta() para obtener el ID
            //data[i][1] = venta.getDescripcion(); // Usar getDescripcion() para obtener la descripción
            data[i][2] = venta.getTotalVenta();
            data[i][3] = venta.getFechaVenta();
        }

        // Actualizar el modelo de la tabla
        tablaVentas.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
    }
}
