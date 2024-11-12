package co.edu.unbosque;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.hibernate.mapping.Component;

import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import co.edu.unbosque.model.EstablecerSecion;
import co.edu.unbosque.model.dao.CompraDAO;
import co.edu.unbosque.model.dao.ProductoDAO;
import co.edu.unbosque.model.dao.UsuarioDAO;
import co.edu.unbosque.view.Ventana;
import co.edu.unbosque.view.panel.inventario.PanelBorrarInventario;
import co.edu.unbosque.view.panel.inventario.PanelDatosInventario;
import co.edu.unbosque.view.panel.pedido.PanelBorrarPedido;
import co.edu.unbosque.view.panel.pedido.PanelDatosPedido;
import co.edu.unbosque.view.panel.persona.PanelBorrarPersona;
import co.edu.unbosque.view.panel.persona.PanelDatosPersona;
import co.edu.unbosque.view.util.menu.CheckBoxEditor;
import co.edu.unbosque.view.util.menu.CheckBoxRenderer;
import co.edu.unbosque.view.util.table.CustomTableModel;
import net.miginfocom.swing.MigLayout;
import raven.modal.ModalDialog;
import raven.modal.component.SimpleModalBorder;

public class Controller implements ActionListener {

    static final String[] cabeceraPedidos = new String[] { "-", "Id", "Id Proveedor", "Total Compra", "Fecha Compra" };
    static final String[] cabeceraPersonas = new String[] { "-", "Identificación", "Nombre Completo",
            "Fecha Nacimiento", "edad", "Tipo Usuario" };
    static final String[] cabeceraProductos = new String[] { "-", "Codigo", "Nombre Producto", "Valor Unitario", "Cantidad" };

    private Ventana ven;
    private EstablecerSecion establecerSecion;

    private CustomTableModel tableModelPedidos;
    private CustomTableModel tableModelInventario;
    private CustomTableModel tableModelPersonas;

    public Controller() {
        FlatRobotoFont.install();
        FlatMacDarkLaf.setup();
        UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13));

        this.ven = new Ventana();
        this.establecerSecion = new EstablecerSecion();
    }

    public void run() {
        ModalDialog.getDefaultOption()
                .getBorderOption()
                .setBorderWidth(0.5f);

        addActionListeners();
        personasConfig();
        pedidosConfig();
        inventarioConfig();

    }

    public void personasConfig() {

        UsuarioDAO udao = new UsuarioDAO();
        String sql = "SELECT \n" + //
                "    identificacion, \n" + //
                "    primer_nombre || ' ' || COALESCE(segundo_nombre, '') || ' ' || primer_apellido || ' ' || COALESCE(segundo_apellido, '') AS nombre_completo,\n"
                + //
                "    TO_CHAR(fecha_nacimiento, 'YYYY-MM-DD') AS fecha_nacimiento,\n" + //
                "    EXTRACT(YEAR FROM AGE(fecha_nacimiento)) AS edad,\n" + //
                "    tipo_usuario\n" + //
                "FROM \n" + //
                "    persona;";

        List<Object[]> usuarios = udao.executeCustomQuery(sql);
        for (Object[] i : usuarios) {
            System.out.println(Arrays.toString(i));
        }
        tableModelPersonas = new CustomTableModel(cabeceraPersonas, usuarios);
        tableModelPersonas.setEditable(true);
        this.ven.getPanelPersonas().getTablaPersonas().setModel(tableModelPersonas);
        this.ven.getPanelPersonas().getTablaPersonas().setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        this.ven.getPanelPersonas().getTablaPersonas().setFocusable(false);
        this.ven.getPanelPersonas().getTablaPersonas().getColumnModel().getColumn(0).setMaxWidth(60);
        this.ven.getPanelPersonas().getTablaPersonas().getColumnModel().getColumn(0).setPreferredWidth(30);
        this.ven.getPanelPersonas().getTablaPersonas().getColumnModel().getColumn(0)
                .setCellEditor(new CheckBoxEditor());
        this.ven.getPanelPersonas().getTablaPersonas().getColumnModel().getColumn(0)
                .setCellRenderer(new CheckBoxRenderer());

        this.ven.getPanelPersonas().getBusqueda().getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void changedUpdate(DocumentEvent arg0) {

            }

            @Override
            public void insertUpdate(DocumentEvent arg0) {
                updateData();
            }

            @Override
            public void removeUpdate(DocumentEvent arg0) {
                updateData();
            }

            private void updateData() {
                String txt = ven.getPanelPersonas().getBusqueda().getText().trim();
                if (txt.equals("")) {
                    tableModelPersonas = new CustomTableModel(cabeceraPersonas, usuarios);
                } else {

                    tableModelPersonas = new CustomTableModel(cabeceraPersonas, usuarios);
                    tableModelPersonas = new CustomTableModel(cabeceraPersonas, tableModelPersonas.searchList(txt));
                }
                tableModelPersonas.setEditable(true);
                ven.getPanelPersonas().getTablaPersonas().setModel(tableModelPersonas);
                ven.getPanelPersonas().getTablaPersonas().setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                ven.getPanelPersonas().getTablaPersonas().setFocusable(false);
                ven.getPanelPersonas().getTablaPersonas().getColumnModel().getColumn(0).setMaxWidth(60);
                ven.getPanelPersonas().getTablaPersonas().getColumnModel().getColumn(0).setPreferredWidth(30);
                ven.getPanelPersonas().getTablaPersonas().getColumnModel().getColumn(0)
                        .setCellEditor(new CheckBoxEditor());
                ven.getPanelPersonas().getTablaPersonas().getColumnModel().getColumn(0)
                        .setCellRenderer(new CheckBoxRenderer());
            }

        });

    }

    public void pedidosConfig() {
        CompraDAO compra = new CompraDAO();
        String sql = "SELECT id_compra, id_persona, total_compra, fecha_compra\n" + //
                "FROM compra;";

        List<Object[]> compras = compra.executeCustomQuery(sql);
        for (Object[] i : compras) {
            System.out.println("pedido:" + Arrays.toString(i));
        }
        System.out.println("Se imprimieron los pedidos");
        tableModelPedidos = new CustomTableModel(cabeceraPedidos, compras);
        tableModelPedidos.setEditable(true);
        this.ven.getPanelPedidos().getTablaPedidos().setModel(tableModelPedidos);
        this.ven.getPanelPedidos().getTablaPedidos().setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        this.ven.getPanelPedidos().getTablaPedidos().setFocusable(false);
        this.ven.getPanelPedidos().getTablaPedidos().getColumnModel().getColumn(0).setMaxWidth(60);
        this.ven.getPanelPedidos().getTablaPedidos().getColumnModel().getColumn(0).setPreferredWidth(30);
        this.ven.getPanelPedidos().getTablaPedidos().getColumnModel().getColumn(0)
                .setCellEditor(new CheckBoxEditor());
        this.ven.getPanelPedidos().getTablaPedidos().getColumnModel().getColumn(0)
                .setCellRenderer(new CheckBoxRenderer());

        this.ven.getPanelPedidos().getBusqueda().getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void changedUpdate(DocumentEvent arg0) {

            }

            @Override
            public void insertUpdate(DocumentEvent arg0) {
                updateData();
            }

            @Override
            public void removeUpdate(DocumentEvent arg0) {
                updateData();
            }

            private void updateData() {
                String txt = ven.getPanelPedidos().getBusqueda().getText().trim();
                if (txt.equals("")) {
                    tableModelPedidos = new CustomTableModel(cabeceraPedidos, compras);
                } else {

                    tableModelPedidos = new CustomTableModel(cabeceraPedidos, compras);
                    tableModelPedidos = new CustomTableModel(cabeceraPedidos, tableModelPedidos.searchList(txt));
                }
                tableModelPedidos.setEditable(true);
                ven.getPanelPedidos().getTablaPedidos().setModel(tableModelPedidos);
                ven.getPanelPedidos().getTablaPedidos().setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                ven.getPanelPedidos().getTablaPedidos().setFocusable(false);
                ven.getPanelPedidos().getTablaPedidos().getColumnModel().getColumn(0).setMaxWidth(60);
                ven.getPanelPedidos().getTablaPedidos().getColumnModel().getColumn(0).setPreferredWidth(30);
                ven.getPanelPedidos().getTablaPedidos().getColumnModel().getColumn(0)
                        .setCellEditor(new CheckBoxEditor());
                ven.getPanelPedidos().getTablaPedidos().getColumnModel().getColumn(0)
                        .setCellRenderer(new CheckBoxRenderer());
            }

        });

    }

    public void inventarioConfig(){
        ProductoDAO producto = new ProductoDAO();
        String sql = "SELECT id_producto, nombre, precio, inventario FROM producto;";

        List<Object[]> inventario = producto.executeCustomQuery(sql);
        for (Object[] i : inventario) {
            System.out.println("pedido:" + Arrays.toString(i));
        }
        System.out.println("Se imprimieron los productos");
        tableModelInventario = new CustomTableModel(cabeceraProductos, inventario);
        tableModelInventario.setEditable(true);
        this.ven.getPanelInventario().getTablaInventario().setModel(tableModelInventario);
        this.ven.getPanelInventario().getTablaInventario().setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        this.ven.getPanelInventario().getTablaInventario().setFocusable(false);
        this.ven.getPanelInventario().getTablaInventario().getColumnModel().getColumn(0).setMaxWidth(60);
        this.ven.getPanelInventario().getTablaInventario().getColumnModel().getColumn(0).setPreferredWidth(30);
        this.ven.getPanelInventario().getTablaInventario().getColumnModel().getColumn(0)
                .setCellEditor(new CheckBoxEditor());
        this.ven.getPanelInventario().getTablaInventario().getColumnModel().getColumn(0)
                .setCellRenderer(new CheckBoxRenderer());

        this.ven.getPanelInventario().getBusqueda().getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void changedUpdate(DocumentEvent arg0) {

            }

            @Override
            public void insertUpdate(DocumentEvent arg0) {
                updateData();
            }

            @Override
            public void removeUpdate(DocumentEvent arg0) {
                updateData();
            }

            private void updateData() {
                String txt = ven.getPanelInventario().getBusqueda().getText().trim();
                if (txt.equals("")) {
                    tableModelInventario = new CustomTableModel(cabeceraProductos, inventario);
                } else {

                    tableModelInventario = new CustomTableModel(cabeceraProductos, inventario);
                    tableModelInventario = new CustomTableModel(cabeceraProductos, tableModelInventario.searchList(txt));
                }
                tableModelInventario.setEditable(true);
                ven.getPanelInventario().getTablaInventario().setModel(tableModelInventario);
                ven.getPanelInventario().getTablaInventario().setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                ven.getPanelInventario().getTablaInventario().setFocusable(false);
                ven.getPanelInventario().getTablaInventario().getColumnModel().getColumn(0).setMaxWidth(60);
                ven.getPanelInventario().getTablaInventario().getColumnModel().getColumn(0).setPreferredWidth(30);
                ven.getPanelInventario().getTablaInventario().getColumnModel().getColumn(0)
                        .setCellEditor(new CheckBoxEditor());
                ven.getPanelInventario().getTablaInventario().getColumnModel().getColumn(0)
                        .setCellRenderer(new CheckBoxRenderer());
            }

        });

    }

    public void addActionListeners() {
        this.ven.getLogin().getLoginButton().setActionCommand("login-button");
        this.ven.getLogin().getLoginButton().addActionListener(this); // Asigna el ActionListener

        // Tabla Pedidos

        this.ven.getPanelPedidos().getCrear().setActionCommand("pedido-crear");
        this.ven.getPanelPedidos().getCrear().addActionListener(this); // Asigna el ActionListener

        this.ven.getPanelPedidos().getEliminar().setActionCommand("pedido-eliminar");
        this.ven.getPanelPedidos().getEliminar().addActionListener(this); // Asigna el ActionListener

        this.ven.getPanelPedidos().getActualizar().setActionCommand("pedido-actualizar");
        this.ven.getPanelPedidos().getActualizar().addActionListener(this); // Asigna el ActionListener

        // Tabla Inventario

        this.ven.getPanelInventario().getCrear().setActionCommand("inventario-crear");
        this.ven.getPanelInventario().getCrear().addActionListener(this); // Asigna el ActionListener

        this.ven.getPanelInventario().getEliminar().setActionCommand("inventario-eliminar");
        this.ven.getPanelInventario().getEliminar().addActionListener(this); // Asigna el ActionListener

        this.ven.getPanelInventario().getActualizar().setActionCommand("inventario-actualizar");
        this.ven.getPanelInventario().getActualizar().addActionListener(this); // Asigna el ActionListener
        

        // Tabla Personas

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
            case "pedido-crear":
                PanelDatosPedido crearPedido = new PanelDatosPedido();
                ModalDialog.showModal(this.ven,
                        new SimpleModalBorder(crearPedido, "Crear Pedido", SimpleModalBorder.DEFAULT_OPTION,
                                (controller, action) -> {
                                    if (action == SimpleModalBorder.OK_OPTION) {
                                        System.out.println("pedido Creado");

                                    }
                                    if (action == SimpleModalBorder.CANCEL_OPTION) {
                                        System.out.println("Cancelar");
                                    }

                                }));

                break;
            case "pedido-eliminar":
                PanelBorrarPedido borrarPedido = new PanelBorrarPedido();
                ModalDialog.showModal(this.ven,
                        new SimpleModalBorder(borrarPedido, "Borrar Pedido", SimpleModalBorder.DEFAULT_OPTION,
                                (controller, action) -> {

                                    if (action == SimpleModalBorder.OK_OPTION) {
                                        System.out.println("Confirmar Eliminacion");

                                    }
                                    if (action == SimpleModalBorder.CANCEL_OPTION) {
                                        System.out.println("Cancelar eliminacion");
                                    }

                                }));

                break;
            case "pedido-actualizar":
                PanelDatosPedido actualizarPedido = new PanelDatosPedido();
                ModalDialog.showModal(this.ven,
                        new SimpleModalBorder(actualizarPedido, "Actualizar Pedido", SimpleModalBorder.DEFAULT_OPTION,
                                (controller, action) -> {
                                    if (action == SimpleModalBorder.OK_OPTION) {
                                        System.out.println("se acepto");
                                     
                                    }
                                    if (action == SimpleModalBorder.CANCEL_OPTION) {
                                        System.out.println("Cancelar actualizacion");
                                    }

                                }));

                break;
            case "inventario-crear":
                PanelDatosInventario crearProducto = new PanelDatosInventario();
                ModalDialog.showModal(this.ven,
                        new SimpleModalBorder(crearProducto, "Agregar Producto", SimpleModalBorder.DEFAULT_OPTION,
                        (controller, action) -> {
                            if (action == SimpleModalBorder.OK_OPTION) {
                                System.out.println("se acepto");

                            }
                            if (action == SimpleModalBorder.CANCEL_OPTION) {
                                System.out.println("Cancelar");
                            }

                        }));
                break;
            case "inventario-eliminar":
                PanelBorrarInventario eliminarProducto = new PanelBorrarInventario();
                ModalDialog.showModal(this.ven,
                        new SimpleModalBorder(eliminarProducto, "Eliminar Producto", SimpleModalBorder.DEFAULT_OPTION,
                        (controller, action) -> {

                            if (action == SimpleModalBorder.OK_OPTION) {
                                System.out.println("Confirmar Eliminacion");

                            }
                            if (action == SimpleModalBorder.CANCEL_OPTION) {
                                System.out.println("Cancelar eliminacion");
                            }

                        }));
                break;
            case "inventario-actualizar":
                PanelDatosInventario actualizarProducto = new PanelDatosInventario();
                ModalDialog.showModal(this.ven,
                        new SimpleModalBorder(actualizarProducto, "Actualizar Producto", SimpleModalBorder.DEFAULT_OPTION,
                        (controller, action) -> {
                            if (action == SimpleModalBorder.OK_OPTION) {
                                System.out.println("se acepto");

                            }
                            if (action == SimpleModalBorder.CANCEL_OPTION) {
                                System.out.println("Cancelar actualizacion");
                            }

                        }));
                break;
            case "personas-crear":
                PanelDatosPersona crearPersona = new PanelDatosPersona();
                ModalDialog.showModal(this.ven,
                        new SimpleModalBorder(crearPersona, "Persona", SimpleModalBorder.DEFAULT_OPTION,
                                (controller, action) -> {
                                    if (action == SimpleModalBorder.OK_OPTION) {
                                        System.out.println("se acepto");
                                        System.out.println(crearPersona.getIdentificacion().getText());
                                        System.out.println(crearPersona.getPrimerNombre().getText() + " "
                                                + crearPersona.getSegundoApellido().getText() + " "
                                                + crearPersona.getPrimerApellido().getText() + " "
                                                + crearPersona.getSegundoApellido().getText());
                                        System.out.println(crearPersona.getTelefono().getText());
                                        System.out.println(crearPersona.getDatePicker().getSelectedDate());
                                        System.out.println(crearPersona.getTipoUsuario().getSelectedItem());

                                    }
                                    if (action == SimpleModalBorder.CANCEL_OPTION) {
                                        System.out.println("Cancelar");
                                    }

                                }));
                break;
            case "personas-eliminar":
                PanelBorrarPersona borrarPersona = new PanelBorrarPersona();
                List<Object[]> personasEliminar = this.tableModelPersonas.getSelectedRowsForMe();
                if (personasEliminar.size() == 0) {
                    ModalDialog.showModal(this.ven, new SimpleModalBorder(new JLabel(""),
                            "<html><b>Por favor:</b><br>Seleccione al menos una opción</html>"));
                } else {

                    borrarPersona.getPersonas()
                            .setModel(new CustomTableModel(cabeceraPersonas, personasEliminar, true));
                    borrarPersona.getPersonas().getColumnModel().getColumn(0).setMaxWidth(10);
                    ModalDialog.showModal(this.ven,
                            new SimpleModalBorder(borrarPersona, "Persona", SimpleModalBorder.DEFAULT_OPTION,
                                    (controller, action) -> {

                                        if (action == SimpleModalBorder.OK_OPTION) {
                                            System.out.println("Confirmar Eliminacion");

                                        }
                                        if (action == SimpleModalBorder.CANCEL_OPTION) {
                                            System.out.println("Cancelar eliminacion");
                                        }

                                    }));
                }
                break;
            case "personas-actualizar":
                PanelDatosPersona actualizarPersona = new PanelDatosPersona();
                List<Object[]> personasActualizar = this.tableModelPersonas.getSelectedRowsForMe();
                if (personasActualizar.size() != 1) {
                    ModalDialog.showModal(this.ven, new SimpleModalBorder(new JLabel(""),
                            "<html><b>Por favor:</b><br>Seleccione solo una opción</html>"));
                } else {
                    // En esta parte establecemos el usuario o persona

                    ModalDialog.showModal(this.ven,
                            new SimpleModalBorder(actualizarPersona, "Persona", SimpleModalBorder.DEFAULT_OPTION,
                                    (controller, action) -> {
                                        if (action == SimpleModalBorder.OK_OPTION) {
                                            System.out.println("se acepto");
                                            // System.out.println(crearPersona.getIdentificacion().getText());
                                            // System.out.println(crearPersona.getPrimerNombre().getText() + " "
                                            // + crearPersona.getSegundoApellido().getText() + " "
                                            // + crearPersona.getPrimerApellido().getText() + " "
                                            // + crearPersona.getSegundoApellido().getText());
                                            // System.out.println(crearPersona.getTelefono().getText());
                                            // System.out.println(crearPersona.getDatePicker().getSelectedDate());
                                            // System.out.println(crearPersona.getTipoUsuario().getSelectedItem());

                                        }
                                        if (action == SimpleModalBorder.CANCEL_OPTION) {
                                            System.out.println("Cancelar actualizacion");
                                        }

                                    }));
                }

                break;
            default:
                break;
        }
    }

}
