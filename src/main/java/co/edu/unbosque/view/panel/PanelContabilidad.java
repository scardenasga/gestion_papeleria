package co.edu.unbosque.view.panel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import co.edu.unbosque.model.service.Contabilidad;

// Asegúrate de que estos imports sean correctos y que las clases existan
import co.edu.unbosque.view.panel.PanelCierreDeCaja;
import co.edu.unbosque.view.panel.PanelConsultarCostos;
import co.edu.unbosque.view.panel.PanelConsultarVentas;
import co.edu.unbosque.view.panel.PanelRegistrarCostosYGastos;
import co.edu.unbosque.view.panel.PanelGenerarFacturacionElectronica;

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
        panelCierreDeCaja = new PanelCierreDeCaja();
        panelConsultarCostos = new PanelConsultarCostos();
        panelConsultarVentas = new PanelConsultarVentas();
        panelRegistrarCostosYGastos = new PanelRegistrarCostosYGastos();
        panelGenerarFacturacionElectronica = new PanelGenerarFacturacionElectronica();

        // Configuración del panel
        setLayout(new GridLayout(6, 1)); // 6 filas, 1 columna

        // Crear botones
        JButton btnCierreCaja = new JButton("Realizar Cierre de Caja");
        JButton btnCalcularCostoTotalVentas = new JButton("Calcular Costo Total Ventas");
        JButton btnConsultarVentasPorFecha = new JButton("Consultar Ventas por Fecha");
        JButton btnRegistrarCosto = new JButton("Registrar Costo");
        JButton btnRegistrarGasto = new JButton("Registrar Gasto");
        JButton btnGenerarFacturacionElectronica = new JButton("Generar Facturación Electrónica");

        // Añadir ActionListeners a los botones
        btnCierreCaja.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll(); // Limpiar el panel principal
                mainPanel.add(panelCierreDeCaja); // Añadir el nuevo panel
                mainPanel.revalidate(); // Revalidar el panel
                mainPanel.repaint(); // Repintar el panel
            }
        });

        btnCalcularCostoTotalVentas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                mainPanel.add(panelConsultarCostos); // Usa la variable
                mainPanel.revalidate();
                mainPanel.repaint();
            }
        });

        btnConsultarVentasPorFecha.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                mainPanel.add(panelConsultarVentas); // Usa la variable
                mainPanel.revalidate();
                mainPanel.repaint();
            }
        });

        btnRegistrarCosto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                mainPanel.add(panelRegistrarCostosYGastos); // Usa la variable
                mainPanel.revalidate();
                mainPanel.repaint();
            }
        });

        btnRegistrarGasto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                mainPanel.add(panelRegistrarCostosYGastos); // Usa la variable
                mainPanel.revalidate();
                mainPanel.repaint();
            }
        });

        btnGenerarFacturacionElectronica.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                mainPanel.add(panelGenerarFacturacionElectronica); // Usa la variable
                mainPanel.revalidate();
                mainPanel.repaint();
            }
        });

        // Añadir botones al panel
        add(btnCierreCaja);
        add(btnCalcularCostoTotalVentas);
        add(btnConsultarVentasPorFecha);
        add(btnRegistrarCosto);
        add(btnRegistrarGasto);
        add(btnGenerarFacturacionElectronica);
    }
}