package co.edu.unbosque.view.panel;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class PanelGenerarFacturacionElectronica extends JPanel {
    private JLabel labelDescripcion; // JLabel para mostrar la descripción
    private JButton botonGenerarFactura;

    public PanelGenerarFacturacionElectronica() {
        // Configura el layout del panel
        setLayout(new BorderLayout());

        // Crear el JLabel para la descripción
        labelDescripcion = new JLabel("Esta funcionalidad permite generar una factura electrónica la cual se pide por parte de la DIAN para regular las actividades económicas del negocio.");
        labelDescripcion.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(labelDescripcion, BorderLayout.CENTER);

        // Crear el botón para generar la facturación electrónica
        botonGenerarFactura = new JButton("Generar Facturación Electrónica");
        botonGenerarFactura.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(botonGenerarFactura, BorderLayout.SOUTH);

        // Acción del botón de generar factura
        botonGenerarFactura.addActionListener(e -> {
            try {
                generarFacturaElectronica();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al generar la factura: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void generarFacturaElectronica() throws IOException {
        // Obtener la fecha actual
        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy");
        String fechaFormateada = fechaActual.format(formatter);

        // Crear el nombre del archivo
        String nombreArchivo = "PAPELERIA020_" + fechaFormateada + ".txt";

        // Obtener la ruta donde se guardará el archivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Factura Electrónica");
        fileChooser.setSelectedFile(new File(nombreArchivo)); // Nombre por defecto del archivo
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String ruta = fileToSave.getAbsolutePath();

            // Obtener datos de la base de datos
            String datosFactura = obtenerDatosFactura();

            // Guardar los datos en el archivo de texto
            try (FileOutputStream fos = new FileOutputStream(ruta)) {
                fos.write(datosFactura.getBytes());
            }

            JOptionPane.showMessageDialog(this, "Factura generada exitosamente en: " + ruta);
        }
    }

    private String obtenerDatosFactura() {
        StringBuilder datos = new StringBuilder();
        // Conectar a la base de datos y obtener los datos de la venta
        String url = "jdbc:mysql://localhost:3306/tu_base_de_datos"; // Cambia esto a tu URL de base de datos
        String user = "tu_usuario"; // Cambia esto a tu usuario
        String password = "tu_contraseña"; // Cambia esto a tu contraseña

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "SELECT cliente, total FROM ventas"; // Cambia esto a tu consulta SQL
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        datos.append("Cliente: ").append(rs.getString("cliente")).append("\n");
                        datos.append("Total: ").append(rs.getDouble("total")).append("\n");
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al obtener datos de la base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        return datos.toString();
    }
}