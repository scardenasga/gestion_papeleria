package co.edu.unbosque.view.panel.pedido;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.formdev.flatlaf.FlatClientProperties;

import lombok.Getter;
import lombok.Setter;
import net.miginfocom.swing.MigLayout;
import raven.modal.component.ModalBorderAction;
import raven.modal.component.SimpleModalBorder;

@Getter
@Setter
public class PanelBorrarPedido extends JPanel{
    private JTable pedidos;
    
    public PanelBorrarPedido() {
        init();
    }

    private void init() {

        setLayout(new MigLayout("wrap 2,fillx,insets n 35 n 35", "[fill,200]"));

        JLabel lbContactDetail = new JLabel("Pedidos a Eliminar");
        lbContactDetail.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold +2;");
        add(lbContactDetail, "gapy 10 10,span 2");

        this.pedidos = new JTable();
        JScrollPane scroll = new JScrollPane(this.pedidos);
        // action button
        add(scroll,"span 2");
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
