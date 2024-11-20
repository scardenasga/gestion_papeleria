package co.edu.unbosque.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;

import co.edu.unbosque.view.panel.Login;
import co.edu.unbosque.view.panel.PanelContabilidad;
import co.edu.unbosque.view.panel.PanelHeader;
import co.edu.unbosque.view.panel.PanelInventario;
import co.edu.unbosque.view.panel.PanelPedidos;
import co.edu.unbosque.view.panel.PanelPersonas;
import co.edu.unbosque.view.panel.PanelProveedores;

import co.edu.unbosque.view.panel.PanelCierreDeCaja;
import co.edu.unbosque.view.panel.PanelConsultarCostos;
import co.edu.unbosque.view.panel.PanelConsultarVentas;
import co.edu.unbosque.view.panel.PanelRegistrarCostosYGastos;
import co.edu.unbosque.view.panel.PanelGenerarFacturacionElectronica;

import co.edu.unbosque.model.service.Contabilidad;


import co.edu.unbosque.view.panel.PanelVenta;
import co.edu.unbosque.view.util.login.BackGround;
import co.edu.unbosque.view.util.menu.MenuDesplegable;
import lombok.Getter;
import lombok.Setter;
import raven.drawer.Drawer;
import raven.popup.GlassPanePopup;


@Setter
@Getter
public class Ventana extends JFrame {

    private BackGround bg;
    private Login login;
    private MenuDesplegable mdp;
    
    private PanelHeader panelHeader;
    private PanelVenta panelVenta;

    private PanelPedidos panelPedidos;
    private PanelInventario panelInventario;
    private PanelPersonas panelPersonas;
    private PanelProveedores panelProveedores;
    private PanelContabilidad panelContabilidad;

    private PanelCierreDeCaja panelCierreDeCaja;
    private PanelConsultarCostos panelConsultarCostos;
    private PanelConsultarVentas panelConsultarVentas;
    private PanelRegistrarCostosYGastos panelRegistrarCostosyGastos;
    private PanelGenerarFacturacionElectronica panelGenerarFacturacionElectronica;

    private Contabilidad contabilidad;
    

    public Ventana(){
        this.setTitle("Rosita");
        this.setSize(new Dimension(700,550));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setMinimumSize(new Dimension(700,550));
        this.setLayout(new GridBagLayout());
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);


        GlassPanePopup.install(this);
        mdp = new MenuDesplegable();
        mdp.setPanelUpdater(this::showSelectedPanel); 
        Drawer.getInstance().setDrawerBuilder(mdp);


        initComponents();

        this.setVisible(true);
    }

    public void initComponents(){
        bg = new BackGround();
        bg.setLayout(new GridBagLayout());
        
        // Crear y agregar el JPanel Login
        login = new Login();
        login.setPreferredSize(new Dimension(500, 500));
        bg.setBlur(login);

        bg.add(login); // Agregar loginPanel dentro del fondo

        this.bg.setVisible(true);

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 2.0;
        gbc.gridheight = 2;
        
        gbc.fill= GridBagConstraints.BOTH;
        // Añadir fondo al JFrame
        this.add(bg,gbc);


        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.04;
        gbc.gridheight = 1;

        panelHeader = new PanelHeader();

        panelHeader.setPreferredSize(new Dimension(panelHeader.getPreferredSize().width, 60)); // Ajusta la altura según lo que necesites
        panelHeader.setMaximumSize(new Dimension(panelHeader.getPreferredSize().width, 50)); // Ajusta la altura según lo que necesites


        this.add(panelHeader,gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.96;

        panelVenta = new PanelVenta();
        this.add(panelVenta,gbc);

        panelPedidos = new PanelPedidos();
        this.add(panelPedidos,gbc);

        panelInventario = new PanelInventario();
        this.add(panelInventario,gbc);

        panelPersonas = new PanelPersonas();
        this.add(panelPersonas,gbc);

        panelProveedores = new PanelProveedores();
        this.add(panelProveedores,gbc);

        panelContabilidad = new PanelContabilidad();
        panelContabilidad.setVisible(false); // Asegúrate de que esté inicialmente oculto
        this.add(panelContabilidad, gbc);

        panelCierreDeCaja = new PanelCierreDeCaja(contabilidad);
        panelCierreDeCaja.setVisible(false); // Asegúrate de que esté inicial
        this.add(panelCierreDeCaja, gbc);

        panelConsultarCostos = new PanelConsultarCostos(contabilidad);
        panelConsultarCostos.setVisible(false); // Asegúrate de que esté inicialmente oculto
        this.add(panelConsultarCostos, gbc);

        panelConsultarVentas = new PanelConsultarVentas();
        panelConsultarVentas.setVisible(false);
        this.add(panelConsultarVentas, gbc);

        panelRegistrarCostosyGastos = new PanelRegistrarCostosYGastos();
        panelRegistrarCostosyGastos.setVisible(false);
        this.add(panelRegistrarCostosyGastos, gbc);

        panelGenerarFacturacionElectronica = new PanelGenerarFacturacionElectronica();
        panelGenerarFacturacionElectronica.setVisible(false);
        this.add(panelGenerarFacturacionElectronica, gbc);


    }

    private void showSelectedPanel(String option) {
        switch (option) {
            case "00":
                System.out.println("Opción seleccionada: Ventas");
                this.panelHeader.getTitulo().setText("Ventas");

                this.panelVenta.setVisible(true);
                this.panelPedidos.setVisible(false);
                this.panelInventario.setVisible(false);
                this.panelPersonas.setVisible(false);
                this.panelProveedores.setVisible(false);
                this.panelContabilidad.setVisible(false);
                this.panelCierreDeCaja.setVisible(false);
                this.panelConsultarCostos.setVisible(false);
                this.panelConsultarVentas.setVisible(false);
                this.panelRegistrarCostosyGastos.setVisible(false);
                this.panelGenerarFacturacionElectronica.setVisible(false);

                Drawer.getInstance().closeDrawer();
                break;
            case "10":
                System.out.println("Opción seleccionada: Pedidos");
                this.panelHeader.getTitulo().setText("Pedidos");

                this.panelVenta.setVisible(false);
                this.panelPedidos.setVisible(true);
                this.panelInventario.setVisible(false);
                this.panelPersonas.setVisible(false);
                this.panelProveedores.setVisible(false);
                this.panelContabilidad.setVisible(false);
                this.panelCierreDeCaja.setVisible(false);
                this.panelConsultarCostos.setVisible(false);
                this.panelConsultarVentas.setVisible(false);
                this.panelRegistrarCostosyGastos.setVisible(false);
                this.panelGenerarFacturacionElectronica.setVisible(false);

                Drawer.getInstance().closeDrawer();
                break;
            case "20":
                System.out.println("Opción seleccionada: Inventario");
                this.panelHeader.getTitulo().setText("Inventario");

                this.panelVenta.setVisible(false);
                this.panelPedidos.setVisible(false);
                this.panelInventario.setVisible(true);
                this.panelPersonas.setVisible(false);
                this.panelProveedores.setVisible(false);
                this.panelContabilidad.setVisible(false);
                this.panelCierreDeCaja.setVisible(false);
                this.panelConsultarCostos.setVisible(false);
                this.panelConsultarVentas.setVisible(false);
                this.panelRegistrarCostosyGastos.setVisible(false);
                this.panelGenerarFacturacionElectronica.setVisible(false);
                
                Drawer.getInstance().closeDrawer();
                break;
            case "30":
                System.out.println("Opción seleccionada: Empleados");
                this.panelHeader.getTitulo().setText("Empleados");

                this.panelVenta.setVisible(false);
                this.panelPedidos.setVisible(false);
                this.panelInventario.setVisible(false);
                this.panelPersonas.setVisible(true);
                this.panelProveedores.setVisible(false);
                this.panelContabilidad.setVisible(false);
                this.panelCierreDeCaja.setVisible(false);
                this.panelConsultarCostos.setVisible(false);
                this.panelConsultarVentas.setVisible(false);
                this.panelRegistrarCostosyGastos.setVisible(false);
                this.panelGenerarFacturacionElectronica.setVisible(false);

                Drawer.getInstance().closeDrawer();
                break;
            // case "40":
            //     System.out.println("Opción seleccionada: Proveedores");
            //     this.panelHeader.getTitulo().setText("Proveedores");

            //     this.panelVenta.setVisible(false);
            //     this.panelPedidos.setVisible(false);
            //     this.panelInventario.setVisible(false);
            //     this.panelPersonas.setVisible(false);
            //     this.panelProveedores.setVisible(true);
            //     this.panelContabilidad.setVisible(false);

            //     Drawer.getInstance().closeDrawer();
            //     break;
            case "40": // Este caso puede ser para Contabilidad
                System.out.println("Opción seleccionada: Contabilidad");
                this.panelHeader.getTitulo().setText("Contabilidad");

            // Mostrar solo el PanelConsultarCostos
                this.panelVenta.setVisible(false);
                this.panelPedidos.setVisible(false);
                this.panelInventario.setVisible(false);
                this.panelPersonas.setVisible(false);
                this.panelProveedores.setVisible(false);
                this.panelContabilidad.setVisible(true);
                this.panelCierreDeCaja.setVisible(false);
                this.panelConsultarCostos.setVisible(false); // Mostrar el panel de consultar costos
                this.panelConsultarVentas.setVisible(false);
                this.panelRegistrarCostosyGastos.setVisible(false);
                this.panelGenerarFacturacionElectronica.setVisible(false);

                Drawer.getInstance().closeDrawer();
                break;

            case "50":
                System.out.println("Opción seleccionada: Cerrar Secion");
                System.exit(1);
                break;
            default:
                System.out.println("Opción seleccionada no definida");
                break;
        }
    }
}
