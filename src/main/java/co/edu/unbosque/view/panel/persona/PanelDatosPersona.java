package co.edu.unbosque.view.panel.persona;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import co.edu.unbosque.model.entity.TipoUsuario;
import lombok.Getter;
import lombok.Setter;
import net.miginfocom.swing.MigLayout;
import raven.datetime.component.date.DatePicker;
import raven.modal.component.ModalBorderAction;
import raven.modal.component.SimpleModalBorder;

@Getter
@Setter
public class PanelDatosPersona extends JPanel {
    
    private JTextField identificacion;
    private JTextField primerNombre;
    private JTextField segundoNombre;
    private JTextField primerApellido;
    private JTextField segundoApellido;
    private JTextField telefono;
    private DatePicker datePicker;
    private JComboBox<TipoUsuario> tipoUsuario;
    private JTextField usuarioField;
    private JPasswordField contrasenaField;
    
    public PanelDatosPersona() {
        init();
    }

    private void init() {

        setLayout(new MigLayout("wrap 2,fillx,insets n 35 n 35", "[fill,200]"));

        JLabel lbContactDetail = new JLabel("Detalle Persona");
        lbContactDetail.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold +2;");
        add(lbContactDetail, "gapy 10 10,span 2");

        add(new JLabel("Identificación"), "span 2");
        identificacion = new JTextField();
        identificacion.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "1234567890");
        add(identificacion, "gapy n 5,span 2");

        add(new JLabel("Primer Nombre"));
        add(new JLabel("Segundo Nombre"));
        primerNombre = new JTextField();
        segundoNombre = new JTextField();
        primerNombre.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Primer Nombre");
        segundoNombre.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Segundo Nombre");
        add(primerNombre);
        add(segundoNombre);

        add(new JLabel("Primer Apellido"));
        add(new JLabel("Segundo Apellido"));
        primerApellido = new JTextField();
        segundoApellido = new JTextField();
        primerApellido.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Primer Apellido");
        segundoApellido.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Segundo Apellido");
        add(primerApellido);
        add(segundoApellido);


        JLabel lbRequestDetail = new JLabel("Request Details");
        lbRequestDetail.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold +2;");
        add(lbRequestDetail, "gapy 10 10,span 2");

        add(new JLabel("Telefono"));
        add(new JLabel("Fecha de Nacimiento"));

        
        
        telefono = new JTextField();
        telefono.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "3105558765");


        JFormattedTextField dateEditor = new JFormattedTextField();
        datePicker = new DatePicker();
        datePicker.setEditor(dateEditor);
        
        add(telefono);
        add(dateEditor);

        add(new JLabel("Tipo de Usuario"), "gapy 5,span 2");
        tipoUsuario = new JComboBox();
        tipoUsuario.addItem(TipoUsuario.PROVEEDOR);
        tipoUsuario.addItem(TipoUsuario.EMPLEADO);
        tipoUsuario.addItem(TipoUsuario.ADMINISTRADOR);
        tipoUsuario.addItem(TipoUsuario.CLIENTE);

        add(tipoUsuario, "Span 2");

        usuarioField = new JTextField();
        usuarioField.setEnabled(false);  // Inhabilitamos el campo inicialmente
        usuarioField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nombre de Usuario");
        contrasenaField = new JPasswordField();
        contrasenaField.setEnabled(false);  // Inhabilitamos el campo inicialmente
        contrasenaField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Contraseña");

        add(new JLabel("Nombre de Usuario"), "span 2");
        add(usuarioField, "span 2");

        add(new JLabel("Contraseña"), "span 2");
        add(contrasenaField, "span 2");

        tipoUsuario.addItemListener(e -> {
            if (tipoUsuario.getSelectedItem() == TipoUsuario.ADMINISTRADOR) {
                // Mostrar los campos de usuario y contraseña si es administrador
                usuarioField.setEnabled(true);
                contrasenaField.setEnabled(true);
            } else {
                // Ocultar los campos de usuario y contraseña si no es administrador
                usuarioField.setEnabled(false);
                contrasenaField.setEnabled(false);
            }
        });

    




        JTextArea textArea = new JTextArea();
        textArea.setEnabled(false);
        textArea.setText("Define el Tipo de acceso que tendra esta persona al sistema.");
        textArea.putClientProperty(FlatClientProperties.STYLE, "" +
                "border:0,0,0,0;" +
                "font:-1;" +
                "background:null;");
        add(textArea, "gapy 5 5,span 2");


        // action button

        JButton cmdCancel = new JButton("Cancelar");
        JButton cmdPayment = new JButton("Confirmar") {
            @Override
            public boolean isDefaultButton() {
                return true;
            }
        };
        cmdCancel.addActionListener(actionEvent -> {
            ModalBorderAction.getModalBorderAction(this).doAction(SimpleModalBorder.CANCEL_OPTION);
        });

        cmdPayment.addActionListener(actionEvent -> {
            ModalBorderAction.getModalBorderAction(this).doAction(SimpleModalBorder.OK_OPTION);
        });

        add(cmdCancel, "grow 0");
        add(cmdPayment, "grow 0, al trailing");
    }
}
