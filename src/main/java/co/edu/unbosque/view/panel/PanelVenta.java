package co.edu.unbosque.view.panel;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import com.formdev.flatlaf.FlatClientProperties;

import lombok.Getter;
import lombok.Setter;
import net.miginfocom.swing.MigLayout;
import raven.modal.component.ModalBorderAction;
import raven.modal.component.SimpleModalBorder;

@Getter
@Setter
public class PanelVenta extends JPanel {

    private JButton crearCliente;
    private JTextField idCliente;
    private JTable productTable;
    private JTextField codigo;
    private JSpinner spinner;
    private JButton agregarProducto;
    private JLabel total;
    private JButton cancelarVenta;
    private JButton confirmarVenta;
    private JButton eliminarProducto;

    public PanelVenta() {
        // this.setBackground(Color.CYAN);
        // Configuración del layout

        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        JPanel formulario = new JPanel();
        formulario.setLayout(new MigLayout("wrap 2,fillx,insets n 50 n 0", "[fill,grow,200]"));

        JLabel labelInformacion = new JLabel("Informacion Venta");
        labelInformacion.putClientProperty(FlatClientProperties.STYLE, "font:bold +2;");
        labelInformacion.setFont(new Font("Roboto",Font.PLAIN,50));
        labelInformacion.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // Obtener el ancho disponible
                int width = labelInformacion.getWidth();
        
                // Establecer límites para el tamaño de la fuente
                int minFontSize = 20;
                int maxFontSize = 50;
                
                // Calcular un tamaño de fuente proporcional dentro de los límites
                int newFontSize = Math.max(minFontSize, Math.min(maxFontSize, width / 10));
                labelInformacion.setFont(new Font("Roboto", Font.PLAIN, newFontSize));
            }
        });
        formulario.add(labelInformacion, "gapy 10 10,span 2, width 200:300:400, height 50:60:70");

        
        JLabel labelIdentificacion = new JLabel("Identificación");
        labelIdentificacion.setFont(new Font("Roboto",Font.PLAIN + Font.BOLD,16));
        formulario.add(labelIdentificacion, "gapy n 5");

        crearCliente = new JButton("<html>Crear<br>Cliente</html>");
        formulario.add(crearCliente, "align left,pad 0 10 0 0 ,span 1 2, width 40:100:120, height 30:40:50");
        
        idCliente = new JTextField();
        idCliente.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Ejm: 1234567890");
        idCliente.setFont(new Font("Roboto",Font.PLAIN ,16));
        formulario.add(idCliente, "gapy 10 20, width 100:150:350, height 30:40:50");
        
        JLabel labelCodigo = new JLabel("Codigo");
        formulario.add(labelCodigo,"span 2, align left");
        
        codigo = new JTextField();
        codigo.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Ejm: Abc#123");
        codigo.setFont(new Font("Roboto",Font.PLAIN ,16));
        formulario.add(codigo,"gapy n 5, width 60:150:350,height 30:40:50, span 2");

        JLabel labelCantidad = new JLabel("Cantidad");
        formulario.add(labelCantidad,"span 2, align left");

        SpinnerNumberModel snm = new SpinnerNumberModel(0,0,200,1);
        spinner = new JSpinner(snm);
        spinner.setFont(new Font("Roboto",Font.PLAIN,16));
        formulario.add(spinner,"height 30:40:50");

        agregarProducto = new JButton("Agregar");
        formulario.add(agregarProducto,"pad 0 5 0 0, width 50:60:80, growy");

        total = new JLabel("$0.00");
        total.setFont(new Font("Roboto", Font.PLAIN+Font.ITALIC,40));
        total.setHorizontalAlignment(SwingConstants.RIGHT);
        formulario.add(total,"span 2, pad 0 0 0 20");

        cancelarVenta = new JButton("Cancelar");
        confirmarVenta = new JButton("Confirmar") {
            @Override
            public boolean isDefaultButton() {
                return true;
            }
        };

        formulario.add(cancelarVenta, "grow 0,height 40::");
        formulario.add(confirmarVenta, "grow 0, align left, height 40::");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.insets = new Insets(50, 50, 50, 50);
        gbc.fill = GridBagConstraints.BOTH;
        add(formulario, gbc);

        // Tabla de productos
        productTable = new JTable(new DefaultTableModel(new Object[] { "Producto", "Cantidad" }, 0));
        JScrollPane scrollTable = new JScrollPane(productTable);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.weighty = 1.0;
        add(scrollTable, gbc);

        setVisible(false);

    }
}

// formulario.setBorder(BorderFactory.createTitledBorder("Información de
// Venta"));

// GridBagConstraints gbc = new GridBagConstraints();
// gbc.insets = new Insets(5, 5, 5, 5); // Márgenes menores entre componentes

// // Etiqueta de Identificación
// gbc.gridx = 0;
// gbc.gridy = 0;
// JLabel identificacionLabel = new JLabel("Identificación");
// identificacionLabel.setFont(new Font("Roboto", Font.BOLD, 20)); // Ajuste de
// tamaño de fuente
// identificacionLabel.setHorizontalAlignment(SwingConstants.CENTER);
// formulario.add(identificacionLabel, gbc);

// // Campo de texto idCliente
// idCliente = new JTextField(15);
// idCliente.setFont(new Font("Roboto", Font.PLAIN, 18));
// gbc.gridy = 1;
// gbc.fill = GridBagConstraints.HORIZONTAL;
// formulario.add(idCliente, gbc);

// // Botón Crear Cliente
// crearCliente = new JButton("Crear Cliente");
// crearCliente.setFont(new Font("Roboto", Font.PLAIN, 18));
// gbc.gridx = 1;
// gbc.gridy = 0;
// formulario.add(crearCliente, gbc);

// // Etiqueta Código Producto
// gbc.gridx = 0;
// gbc.gridy = 2;
// JLabel codigoLabel = new JLabel("Código Producto");
// codigoLabel.setFont(new Font("Roboto", Font.BOLD, 18));
// formulario.add(codigoLabel, gbc);

// // Campo de texto Código Producto
// codigo = new JTextField(15);
// codigo.setFont(new Font("Roboto", Font.PLAIN, 18));
// gbc.gridy = 3;
// formulario.add(codigo, gbc);

// // Botón Agregar Producto
// agregarProducto = new JButton("Agregar Producto");
// agregarProducto.setFont(new Font("Roboto", Font.PLAIN, 18));
// gbc.gridx = 0;
// gbc.gridy = 4;
// gbc.gridwidth = 2;
// formulario.add(agregarProducto, gbc);

// // Botones Confirmar y Cancelar Venta
// cancelarVenta = new JButton("Cancelar Venta");
// cancelarVenta.setFont(new Font("Roboto", Font.PLAIN, 18));
// gbc.gridy = 5;
// gbc.gridx = 0;
// gbc.gridwidth = 1;
// formulario.add(cancelarVenta, gbc);

// confirmarVenta = new JButton("Confirmar Venta");
// confirmarVenta.setFont(new Font("Roboto", Font.PLAIN, 18));
// gbc.gridx = 1;
// formulario.add(confirmarVenta, gbc);
