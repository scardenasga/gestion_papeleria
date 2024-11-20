package co.edu.unbosque.view.panel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import co.edu.unbosque.model.service.Contabilidad;

public class PanelContabilidad extends JPanel {

    private PanelCierreDeCaja panelCierreDeCaja;
    private PanelConsultarCostos panelConsultarCostos;
    private PanelConsultarVentas panelConsultarVentas;
    private PanelRegistrarCostosYGastos panelRegistrarCostosYGastos;
    private PanelGenerarFacturacionElectronica panelGenerarFacturacionElectronica;

    private Contabilidad contabilidad; // Instancia de la clase Contabilidad
    private JPanel mainPanel; // Panel principal que contendrá los diferentes paneles

    public PanelContabilidad() {
        contabilidad = new Contabilidad(); // Inicializa la clase Contabilidad
        
        // Inicializar los paneles
        mainPanel = new JPanel();
        panelCierreDeCaja = new PanelCierreDeCaja(contabilidad);
        panelConsultarCostos = new PanelConsultarCostos(contabilidad);
        panelConsultarVentas = new PanelConsultarVentas();
        panelRegistrarCostosYGastos = new PanelRegistrarCostosYGastos();
        panelGenerarFacturacionElectronica = new PanelGenerarFacturacionElectronica();

        // Configuración del panel
        setLayout(new BorderLayout()); // Cambiar a BorderLayout

        // Crear un panel para los botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 1)); // 6 filas, 1 columna

        // Crear botones
        JButton btnCierreCaja = new JButton("Realizar Cierre de Caja");
        JButton btnConsultarCostoTotalVentas = new JButton("Consultar Costos");
        JButton btnConsultarVentasPorFecha = new JButton("Consultar Ventas por Fecha");
        JButton btnRegistrarCostoYGatstos = new JButton("Registrar Costo y Gastos");
        JButton btnGenerarFacturacionElectronica = new JButton("Generar Facturación Electrónica");

        // Añadir ActionListeners a los botones
        btnCierreCaja.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPanel(panelCierreDeCaja);
            }
        });

        btnConsultarCostoTotalVentas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPanel(panelConsultarCostos);
            }
        });

        btnConsultarVentasPorFecha.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPanel(panelConsultarVentas);
            }
        });

        btnRegistrarCostoYGatstos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPanel(panelRegistrarCostosYGastos);
            }
        });

        btnGenerarFacturacionElectronica.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPanel(panelGenerarFacturacionElectronica);
            }
        });

        // Añadir botones al panel de botones
        buttonPanel.add(btnCierreCaja);
        buttonPanel.add(btnConsultarCostoTotalVentas);
        buttonPanel.add(btnConsultarVentasPorFecha);
        buttonPanel.add(btnRegistrarCostoYGatstos);
        buttonPanel.add(btnGenerarFacturacionElectronica);

        // Añadir el panel de botones y el mainPanel al PanelContabilidad
        add(buttonPanel, BorderLayout.WEST); // Añadir el panel de botones a la izquierda
        add(mainPanel, BorderLayout.CENTER); // Añadir el mainPanel al centro

        // Inicialmente, podemos mostrar un panel vacío o un mensaje
        mainPanel.add(new JLabel("Seleccione una opción")); // Mensaje inicial
    }

    private void showPanel(JPanel panel) {
        mainPanel.removeAll(); // Limpiar el panel principal
        mainPanel.add(panel); // Añadir el nuevo panel
        mainPanel.revalidate(); // Revalidar el panel
        mainPanel.repaint(); // Repintar el panel
    }
}
