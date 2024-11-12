package co.edu.unbosque.view.panel.pedido;

import java.sql.Timestamp;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.hibernate.type.descriptor.jdbc.LocalDateJdbcType;

import com.formdev.flatlaf.FlatClientProperties;

import co.edu.unbosque.model.entity.TipoUsuario;
import net.miginfocom.swing.MigLayout;
import raven.datetime.component.date.DatePicker;
import raven.modal.component.ModalBorderAction;
import raven.modal.component.SimpleModalBorder;

public class PanelDatosPedido extends JPanel{
    
    private JTextField identificacion;
    private JTextField totalPedido;
    private DatePicker datePicker;
    private JComboBox<TipoUsuario> tipoUsuario;
    private JTextField usuarioField;
    private JPasswordField contrasenaField;
    
    public PanelDatosPedido() {
        init();
    }

    private void init() {

        setLayout(new MigLayout("wrap 2,fillx,insets n 35 n 35", "[fill,200]"));

        JLabel lbContactDetail = new JLabel("Detalle Pedido");
        lbContactDetail.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold +2;");
        add(lbContactDetail, "gapy 10 10,span 2");

        add(new JLabel("Identificación Proveedor"), "span 2");
        identificacion = new JTextField();
        identificacion.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "1234567890");
        add(identificacion, "gapy n 5,span 2");



        add(new JLabel("Valor del Pedido"));
        add(new JLabel("Fecha de Pedido"));

        
        
        totalPedido = new JTextField();
        totalPedido.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "3105558765");

        totalPedido.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "0.00");
        JLabel lbDollar = new JLabel("$");
        lbDollar.putClientProperty(FlatClientProperties.STYLE, "" +
                "border:0,8,0,0;");
        totalPedido.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_COMPONENT, lbDollar);
        add(totalPedido);


        JFormattedTextField dateEditor = new JFormattedTextField();
        datePicker = new DatePicker();
        datePicker.setEditor(dateEditor);
        datePicker.now();
        
        add(totalPedido);
        add(dateEditor);

        JTextArea textArea = new JTextArea();
        textArea.setEnabled(false);
        textArea.setText("Pedidos Realizados a proveedores.");
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
