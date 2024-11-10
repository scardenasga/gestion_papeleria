package co.edu.unbosque;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import co.edu.unbosque.model.EstablecerSecion;
import co.edu.unbosque.model.dao.UsuarioDAO;
import co.edu.unbosque.view.Ventana;
import co.edu.unbosque.view.panel.persona.PanelDatosPersona;
import co.edu.unbosque.view.util.table.CustomTableModel;
import raven.modal.ModalDialog;
import raven.modal.component.SimpleModalBorder;

public class Controller implements ActionListener {

    private Ventana ven;
    private EstablecerSecion establecerSecion;

    public Controller() {
        FlatRobotoFont.install();
        FlatMacDarkLaf.setup();
        UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13));
        // try {
        //     UIManager.setLookAndFeel(new FlatDarkLaf());
        // } catch (Exception ex) {
        //     System.err.println("Failed to initialize LaF");
        // }

        // this.vp = new VentanaPrincipal ();
        // this.pb = new Prueba();
        // this.mdp = new MenuDesplegablePrueba();

        this.ven = new Ventana();
        this.establecerSecion = new EstablecerSecion();
    }

    public void run() {
        // ModalDialog.getDefaultOption()
        //         .getBorderOption()
        //         .setBorderWidth(0.5f);

        addActionListeners();
        viewConnection();

    }

    public void viewConnection() {
        String[] cabeceras = new String[]{"identificacion","nombre","segundo apellido","p1","p2", "edad", "telefono","usuario","hola?"};

        UsuarioDAO udao = new UsuarioDAO();
        String sql = "SELECT * FROM persona;";
        List<Object[]> usuarios = udao.executeCustomQuery(sql);

        this.ven.getPanelPersonas().getTablaPersonas().setModel(new CustomTableModel(cabeceras, usuarios));
    }

    public void addActionListeners() {
        this.ven.getLogin().getLoginButton().setActionCommand("login-button");
        this.ven.getLogin().getLoginButton().addActionListener(this); // Asigna el ActionListener

        this.ven.getPanelPersonas().getBusqueda().setActionCommand("personas-buscar");
        this.ven.getPanelPersonas().getBusqueda().addActionListener(this); // Asigna el ActionListener

        this.ven.getPanelPersonas().getCrear().setActionCommand("personas-crear");
        this.ven.getPanelPersonas().getCrear().addActionListener(this); // Asigna el ActionListener

        this.ven.getPanelPersonas().getEliminar().setActionCommand("personas-eliminar");
        this.ven.getPanelPersonas().getEliminar().addActionListener(this); // Asigna el ActionListener

        this.ven.getPanelPersonas().getActualizar().setActionCommand("personas-actualizar");
        this.ven.getPanelPersonas().getActualizar().addActionListener(this); // Asigna el ActionListener
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "login-button":

            // en esta parte se realiza el inicio de seción
            establecerSecion.setUsuarioActual("ADMIN");

                this.ven.getBg().setVisible(false);
                this.ven.getPanelHeader().setVisible(true);
                this.ven.getPanelVenta().setVisible(true);
                break;
            case "personas-crear":
            PanelDatosPersona crearPersona = new PanelDatosPersona();
            crearPersona.getIdentificacion().setText("12345");
                ModalDialog.showModal(this.ven, new SimpleModalBorder(crearPersona, "Persona",SimpleModalBorder.DEFAULT_OPTION,
                        (controller,action ) ->{
                            if(action == SimpleModalBorder.OK_OPTION){
                                System.out.println("se acepto");
                                System.out.println(crearPersona.getIdentificacion().getText());
                                System.out.println(crearPersona.getPrimerNombre().getText()+" "+crearPersona.getSegundoApellido().getText()+" "+crearPersona.getPrimerApellido().getText()+" "+crearPersona.getSegundoApellido().getText());
                                System.out.println(crearPersona.getTelefono().getText());
                                System.out.println(crearPersona.getDatePicker().getSelectedDate());
                                System.out.println(crearPersona.getTipoUsuario().getSelectedItem());
                                
                            }
                            if(action == SimpleModalBorder.CANCEL_OPTION){
                                System.out.println("Cancelar");
                            }

                        }));
                break;
            default:
                break;
        }
    }


}
