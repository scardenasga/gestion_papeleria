package co.edu.unbosque.view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import co.edu.unbosque.view.util.menu.MenuDesplegable;
import raven.drawer.Drawer;
import raven.popup.GlassPanePopup;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class MenuDesplegablePrueba extends JFrame {

    MenuDesplegable mdp;
    private JPanel mainPanel;

    public MenuDesplegablePrueba(){
        this.setTitle("menu desplegable");
        this.setSize(700, 700);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        
        JButton bn = new JButton();
        bn.setBounds(30, 30, 100, 50);


        this.add(bn);
        bn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Drawer.getInstance().showDrawer();
            }
            
        });
        

        GlassPanePopup.install(this);
         mdp = new MenuDesplegable();
         mdp.setPanelUpdater(this::showSelectedPanel); 


  
        Drawer.getInstance().setDrawerBuilder(mdp);


        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

        
    }

    private void showSelectedPanel(String option) {
        switch (option) {
            case "00":
                System.out.println("Opción seleccionada: Ventas");
                break;
            case "10":
                System.out.println("Opción seleccionada: Pedidos");
                break;
            case "20":
                System.out.println("Opción seleccionada: Inventario");
                break;
            case "30":
                System.out.println("Opción seleccionada: Empleados");
                break;
            case "40":
                System.out.println("Opción seleccionada: Proveedores");
                break;
            case "51":
                System.out.println("Opción seleccionada: Contabilidad");
                break;
            case "52":
                System.out.println("Opción seleccionada: Costos");
                break;
            case "53":
                System.out.println("Opción seleccionada: Cierre de Caja");
                break;
            case "60":
                System.out.println("Opción seleccionada: Cerrar Secion");
                System.exit(1);
                break;
            default:
                System.out.println("Opción seleccionada no definida");
                break;
        }
    }

}
