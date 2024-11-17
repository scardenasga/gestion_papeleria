package co.edu.unbosque.view.panel.inventario;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import com.formdev.flatlaf.FlatClientProperties;

import co.edu.unbosque.model.entity.TipoUsuario;
import lombok.Getter;
import lombok.Setter;
import net.miginfocom.swing.MigLayout;
import raven.datetime.component.date.DatePicker;
import raven.modal.component.ModalBorderAction;
import raven.modal.component.SimpleModalBorder;

@Getter
@Setter
public class PanelDatosInventario extends JPanel{

    private JTextField codigo;
    private JTextField nombreProducto;
    private JTextField totalPrecio;
    private JSpinner spinner;
    
    public PanelDatosInventario() {
        init();
    }

    private void init() {

        setLayout(new MigLayout("wrap 2,fillx,insets n 35 n 35", "[fill,200]"));

        JLabel lbContactDetail = new JLabel("Detalle Producto");
        lbContactDetail.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold +2;");
        add(lbContactDetail, "gapy 10 10,span 2");

        add(new JLabel("Codigo del Producto"), "span 2");
        codigo = new JTextField();
        codigo.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "1234567890");
        add(codigo, "gapy n 5,span 2");



        add(new JLabel("Nombre Producto"));
        add(new JLabel("Precio Producto"));

        nombreProducto = new JTextField();
        nombreProducto.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Producto");
        add(nombreProducto);
        
        
        totalPrecio = new JTextField();
        totalPrecio.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "3105558765");

        totalPrecio.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "0.00");
        JLabel lbDollar = new JLabel("$");
        lbDollar.putClientProperty(FlatClientProperties.STYLE, "" +
                "border:0,8,0,0;");
                totalPrecio.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_COMPONENT, lbDollar);
        add(totalPrecio);


        add(new JLabel("Cantidad"));
        add(new JLabel(""));
        SpinnerNumberModel snm = new SpinnerNumberModel(0, 0, 200, 1);
        spinner = new JSpinner(snm);
        add(spinner);
        add(new JLabel());





        JTextArea textArea = new JTextArea();
        textArea.setEnabled(false);
        textArea.setText("Plantilla basica para un producto.");
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
