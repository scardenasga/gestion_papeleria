package co.edu.unbosque.controller;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import co.edu.unbosque.model.EstablecerSecion;
import co.edu.unbosque.model.dao.CompraDAO;
import co.edu.unbosque.model.dao.ProductoDAO;
import co.edu.unbosque.model.dao.UsuarioDAO;
import co.edu.unbosque.model.entity.Compra;
import co.edu.unbosque.model.entity.Persona;
import co.edu.unbosque.model.entity.Producto;
import co.edu.unbosque.model.entity.TipoEstado;
import co.edu.unbosque.model.entity.TipoUsuario;
import co.edu.unbosque.model.entity.Usuario;
import co.edu.unbosque.model.service.CompraService;
import co.edu.unbosque.model.service.InventarioService;
import co.edu.unbosque.model.service.PersonaService;
import co.edu.unbosque.model.service.VentaService;
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

    static final String sqlPedidos = "SELECT id_compra, id_persona, total_compra, fecha_compra\n" + //
                "FROM compra WHERE estado != 'INACTIVO';";




    static final String[] cabeceraPersonas = new String[] { "-", "Identificación", "Nombre Completo", "Telefono",
            "Fecha Nacimiento", "edad", "Tipo Usuario", "Estado" };

    static final String sqlpersonas = "SELECT \n" + //
            "    identificacion, \n" + //
            "    primer_nombre || ' ' || COALESCE(segundo_nombre, '') || ' ' || primer_apellido || ' ' || COALESCE(segundo_apellido, '') AS nombre_completo,\n"
            + "telefono,\n"+
            "    TO_CHAR(fecha_nacimiento, 'YYYY-MM-DD') AS fecha_nacimiento,\n" + //
            "    EXTRACT(YEAR FROM AGE(fecha_nacimiento)) AS edad,\n" + //
            "    tipo_usuario,\n" + //
            "    estado\n" +
            "FROM \n" + //
            "    persona WHERE estado != 'INACTIVO';";

    static final String[] cabeceraProductos = new String[] { "-", "Codigo", "Nombre Producto", "Valor Unitario",
            "Cantidad" };
    
    static final String sqlProductos = "SELECT id_producto, nombre, precio, inventario FROM producto where estado = 'ACTIVO';";

    private Ventana ven;
    private EstablecerSecion establecerSecion;

    private PersonaService personaService;
    private InventarioService inventarioService;
    private CompraService compraService;

    private CustomTableModel tableModelPedidos;
    private CustomTableModel tableModelInventario;
    private CustomTableModel tableModelPersonas;

    private JTable table; 
    private DefaultTableModel model; 
    private String codigo_del_producto; 
    private String idCliente; 

    public Controller() {
        FlatRobotoFont.install();
        FlatMacDarkLaf.setup();
        UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13));

        this.establecerSecion = new EstablecerSecion();
        this.personaService = new PersonaService();
        this.inventarioService = new InventarioService();
        this.compraService = new CompraService();
        this.ven = new Ventana();
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

        List<Object[]> usuarios = udao.executeCustomQuery(sqlpersonas);
        for (Object[] i : usuarios) {
            System.out.println(Arrays.toString(i));
        }
        tableModelPersonas = new CustomTableModel(cabeceraPersonas, usuarios);
        personaTableConfig();

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
                personaTableConfig();
            }

        });

    }

    public void personaTableConfig() {
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

    }

    public void pedidosConfig() {
        CompraDAO compra = new CompraDAO();
        

        List<Object[]> compras = compra.executeCustomQuery(sqlPedidos);
        for (Object[] i : compras) {
            System.out.println("pedido:" + Arrays.toString(i));
        }
        System.out.println("Se imprimieron los pedidos");
        tableModelPedidos = new CustomTableModel(cabeceraPedidos, compras);
        pedidosTableConfig();

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
                pedidosTableConfig();
              
            }

        });

    }

    public void pedidosTableConfig(){
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
    public void inventarioConfig() {
        ProductoDAO producto = new ProductoDAO();
        

        List<Object[]> inventario = producto.executeCustomQuery(sqlProductos);
        for (Object[] i : inventario) {
            System.out.println("pedido:" + Arrays.toString(i));
        }
        System.out.println("Se imprimieron los productos");
        tableModelInventario = new CustomTableModel(cabeceraProductos, inventario);
        inventarioTableConfig();
        

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
                    tableModelInventario = new CustomTableModel(cabeceraProductos,
                            tableModelInventario.searchList(txt));
                }
                inventarioTableConfig();
            }

        });

    }


    public void inventarioTableConfig(){
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

        this.table = this.ven.getPanelVenta().getProductTable(); 
        this.model = (DefaultTableModel) table.getModel(); 
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
    
            // Venta
            this.ven.getPanelVenta().getConfirmarVenta().setActionCommand("venta-agregar"); 
            this.ven.getPanelVenta().getConfirmarVenta().addActionListener(this); // Asigna el ActionListener 
    
            this.ven.getPanelVenta().getAgregarProducto().setActionCommand("producto-agregar"); 
            this.ven.getPanelVenta().getAgregarProducto().addActionListener(this); // Asigna el ActionListener 
            
            this.ven.getPanelVenta().getCancelarVenta().setActionCommand("venta-cancelar"); 
            this.ven.getPanelVenta().getCancelarVenta().addActionListener(this); // Asigna el ActionListener 
    
            this.ven.getPanelVenta().getCrearCliente().setActionCommand("personas-crear"); 
            this.ven.getPanelVenta().getCrearCliente().addActionListener(this); // Asigna el ActionListener 
    
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "login-button":

                UsuarioDAO us=new UsuarioDAO();
            boolean entrar=false;

            try{
            List <Usuario> usuarios = us.findAll().get(); 
        
            for (Usuario usuario : usuarios) {
//System.out.println(usuario.getUsername());
//System.out.println(this.ven.getLogin().getUsername().getText());
//System.out.println(usuario.getContrasena());
//System.out.println(this.ven.getLogin().getPassword().getText());

                if(usuario.getUsername().equals(this.ven.getLogin().getUsername().getText())){

                    if(usuario.getContrasena().equals(this.ven.getLogin().getPassword().getText())){

                        entrar=true;

                    break;
                    } 
                }
            }
        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }
        if(entrar){

    establecerSecion.setUsuarioActual("ADMIN");
                    this.ven.getBg().setVisible(false);
                    this.ven.getPanelHeader().setVisible(true);
                    this.ven.getPanelVenta().setVisible(true);
                    
            }else{
                        JOptionPane.showMessageDialog(null, "Usuario o contraseña no encontrado", "Error de autenticación", JOptionPane.ERROR_MESSAGE);
            }

                break;

                case "producto-agregar":


            // Recuperar los datos de los componentes
            idCliente = this.ven.getPanelVenta().getIdCliente().getText();
            codigo_del_producto = this.ven.getPanelVenta().getCodigo().getText();
            int cantidad_del_producto = (int) this.ven.getPanelVenta().getSpinner().getValue(); // Spinner puede devolver un Object

            if (codigo_del_producto.length() <1 || cantidad_del_producto<1){
            
                JOptionPane.showMessageDialog(null, "por favor inserte código y cantidad mayor a 0");

          
            } else{
                      // Agregar los datos como una nueva fila en el modelo de la tabla
            model.addRow(new Object[]{codigo_del_producto, cantidad_del_producto});
            

            // Opcional: Verificar si los datos se agregaron correctamente
            System.out.println("Datos agregados a la tabla:");
            System.out.println("ID Cliente: " + idCliente);
            System.out.println("Código: " + codigo_del_producto);
            System.out.println("Cantidad: " + cantidad_del_producto);
            }
            break;
            
            case "venta-agregar":

            try{
            // Obtener el número de filas y columnas
                int rowCount = model.getRowCount();
                int columnCount = model.getColumnCount();
            if (idCliente==null){
                JOptionPane.showMessageDialog(null, "rellene todos los campos");
            }

                // Recorrer las filas y columnas para imprimir los valores
                System.out.println("Valores de la tabla:");
                for (int i = 0; i < rowCount; i++) {
                    for (int j = 0; j < columnCount; j++) {
                        // Obtener el valor de la celda
                        Object value = table.getValueAt(i, j);
                        System.out.print(value + "\t"); // Imprimir valores en la misma línea, separados por tabulaciones
                    
                    }
                    System.out.println(); // Nueva línea al terminar cada fila
                }

                    // Crear un ArrayList de dos dimensiones para almacenar los valores de la tabla
                    ArrayList<ArrayList<Object>> detalles = new ArrayList<>();

                    // Recorrer las filas y columnas para obtener los valores
                    for (int i = 0; i < rowCount; i++) {
                        ArrayList<Object> fila = new ArrayList<>(); // Lista para almacenar la fila actual
                        for (int j = 0; j < columnCount; j++) {
                            // Obtener el valor de la celda
                            Object value = table.getValueAt(i, j);
                            fila.add(value); // Añadir el valor a la fila
                        }
                        detalles.add(fila); // Añadir la fila completa al ArrayList principal
                    }
                

                // agregar venta
                String msgg=(new VentaService()).createVenta(null, table, idCliente);

                
                JOptionPane.showMessageDialog(null, msgg);
                }catch(Exception j){
                    JOptionPane.showMessageDialog(null, "error: revise los datos");
                    j.getStackTrace();
                }
                

            break;
            case "venta-cancelar":
            //this.ven.getPanelVenta().setProductTable(new JTable());; 
            //this.table = this.ven.getPanelVenta().getProductTable(); 
            //this.model = (DefaultTableModel) table.getModel();
            break;

            case "pedido-crear":
                PanelDatosPedido crearPedido = new PanelDatosPedido();
                    ModalDialog.showModal(this.ven,
                        new SimpleModalBorder(crearPedido, "Agregar Pedido", SimpleModalBorder.DEFAULT_OPTION,
                                (controller, action) -> {
                                    if (action == SimpleModalBorder.OK_OPTION) {
                                        System.out.println("se acepto");

                                        String identificacion = crearPedido.getIdentificacion().getText().trim();
                                        LocalDate fecha = crearPedido.getDatePicker().getSelectedDate();

                                        String valor = crearPedido.getTotalPedido().getText().trim().replaceAll(",", ".");
                                        BigDecimal precio = new BigDecimal(valor);

                                        try {
                                            Persona p = personaService.getPersona(identificacion);
                                            
                                            if(p.getTipoUsuario() != TipoUsuario.PROVEEDOR){
                                                throw new Exception();
                                            }
                                                
                                            Compra compra = Compra.builder()
                                                .persona(p)
                                                .totalCompra(precio)
                                                .fechaCompra(Timestamp.valueOf(fecha.atStartOfDay()))
                                                .estado(TipoEstado.ACTIVO)
                                                .build();
                                                
                                                
                                                establecerSecion.showUsuarioActual();
                                                System.out.println(compra);
                                                compraService.agregarCompra(compra);
                                                // inventarioService.crearProducto(product);
                                                
                                                List<Object[]> pedidos = compraService.query(sqlPedidos);
                                                tableModelPedidos = new CustomTableModel(cabeceraPedidos, pedidos);
                                                pedidosTableConfig();
                                        
                                        } catch (Exception exeption) {
                                            ModalDialog.showModal(this.ven, new SimpleModalBorder(new JLabel("No existe Proveedor con id:"+identificacion), "Advertencia"));
                                        }

                                    }
                                    if (action == SimpleModalBorder.CANCEL_OPTION) {
                                        System.out.println("Cancelar");
                                    }

                                }));
            

                break;
            case "pedido-eliminar":
                PanelBorrarPedido borrarPedido = new PanelBorrarPedido();
                List<Object[]> pedidosEliminar = this.tableModelPedidos.getSelectedRowsForMe();
                if (pedidosEliminar.size() == 0) {
                    ModalDialog.showModal(this.ven, new SimpleModalBorder(new JLabel(""),
                            "<html><b>Por favor:</b><br>Seleccione al menos una opción</html>"));
                } else {

                    borrarPedido.getPedidos().setModel(new CustomTableModel(cabeceraPedidos, pedidosEliminar, true));
                    borrarPedido.getPedidos().getColumnModel().getColumn(0).setMaxWidth(10);
                    ModalDialog.showModal(this.ven,
                            new SimpleModalBorder(borrarPedido, "Eliminar pedidos", SimpleModalBorder.DEFAULT_OPTION,
                                    (controller, action) -> {

                                        establecerSecion.showUsuarioActual();
                                        if (action == SimpleModalBorder.OK_OPTION) {
                                            System.out.println("Confirmar Eliminacion");
                                            StringBuilder sb = new StringBuilder();
                                            for (Object[] i : pedidosEliminar) {
                                                System.out.println(Arrays.toString(i));
                                            }
                                            for (Object[] i : pedidosEliminar) {
                                                sb.append("'" + i[1] + "', ");
                                            }
                                            sb.setLength(sb.length() - 2);
                                            System.out.println(sb.toString());
                                            compraService.eliminarPedido(sb.toString());

                                            List<Object[]> pedidos = compraService.query(sqlPedidos);
                                            tableModelPedidos = new CustomTableModel(cabeceraProductos, pedidos);
                                            pedidosTableConfig();

                                        }
                                        if (action == SimpleModalBorder.CANCEL_OPTION) {
                                            System.out.println("Cancelar eliminacion");
                                        }

                                    }));
                }
               

                break;
            case "pedido-actualizar":
                PanelDatosPedido actualizarPedido = new PanelDatosPedido();
                List<Object[]> pedidoActualizar = this.tableModelPedidos.getSelectedRowsForMe();
                if (pedidoActualizar.size() != 1) {
                    ModalDialog.showModal(this.ven, new SimpleModalBorder(new JLabel(""),
                            "<html><b>Por favor:</b><br>Seleccione solo una opción</html>"));
                } else {
                    // En esta parte establecemos el usuario o persona
                    long codigo = (Long)pedidoActualizar.get(0)[1];
                    Compra aux = compraService.getPedido(codigo);
                    actualizarPedido.getIdentificacion().setText(aux.getPersona().getIdentificacion());
                    actualizarPedido.getDatePicker().setSelectedDate(aux.getFechaCompra().toLocalDateTime().toLocalDate());
                    actualizarPedido.getTotalPedido().setText(aux.getTotalCompra().toString());

                    ModalDialog.showModal(this.ven,
                            new SimpleModalBorder(actualizarPedido, "Producto", SimpleModalBorder.DEFAULT_OPTION,
                                    (controller, action) -> {
                                        if (action == SimpleModalBorder.OK_OPTION) {
                                            System.out.println("se acepto");
                                            String identificacion = actualizarPedido.getIdentificacion().getText().trim();
                                        LocalDate fecha = actualizarPedido.getDatePicker().getSelectedDate();

                                        String valor = actualizarPedido.getTotalPedido().getText().trim().replaceAll(",", ".");
                                        BigDecimal precio = new BigDecimal(valor);

                                        try {
                                            Persona p = personaService.getPersona(identificacion);
                                            
                                            if(p.getTipoUsuario() != TipoUsuario.PROVEEDOR){
                                                throw new Exception();
                                            }
                                                
                                           
                                            aux.setPersona(p);
                                            aux.setTotalCompra(precio);
                                            aux.setFechaCompra(Timestamp.valueOf(fecha.atStartOfDay()));
                                                
                                                
                                                establecerSecion.showUsuarioActual();
                                                System.out.println(aux);
                                                compraService.actualizarCompra(codigo, aux);
                                                // inventarioService.crearProducto(product);
                                                
                                                List<Object[]> pedidos = compraService.query(sqlPedidos);
                                                tableModelPedidos = new CustomTableModel(cabeceraPedidos, pedidos);
                                                pedidosTableConfig();
                                        
                                        } catch (Exception exeption) {
                                            ModalDialog.showModal(this.ven, new SimpleModalBorder(new JLabel("No existe Proveedor con id:"+identificacion), "Advertencia"));
                                        }

                                        }
                                        if (action == SimpleModalBorder.CANCEL_OPTION) {
                                            System.out.println("Cancelar actualizacion");
                                        }

                                    }));
                }

                break;
            case "inventario-crear":
                PanelDatosInventario crearProducto = new PanelDatosInventario();
                ModalDialog.showModal(this.ven,
                        new SimpleModalBorder(crearProducto, "Agregar Producto", SimpleModalBorder.DEFAULT_OPTION,
                                (controller, action) -> {
                                    if (action == SimpleModalBorder.OK_OPTION) {
                                        System.out.println("se acepto");
                                        String codigo = crearProducto.getCodigo().getText().trim();
                                        String nombreProducto = crearProducto.getNombreProducto().getText().toLowerCase().trim();
                                        String valor = crearProducto.getTotalPrecio().getText().trim().replaceAll(",", ".");
                                        System.out.println(valor);

                                        BigDecimal precio = new BigDecimal(valor);
                                        int cantidad = (Integer)crearProducto.getSpinner().getValue();

                                        System.out.println(codigo);
                                        System.out.println(nombreProducto);
                                        System.out.println(precio);
                                        System.out.println(cantidad);
                                        Producto product = Producto.builder()
                                                            .idProducto(codigo)
                                                            .nombre(nombreProducto)
                                                            .precio(precio)
                                                            .inventario(cantidad)
                                                            .estado(TipoEstado.ACTIVO)
                                                            .build();

                                        establecerSecion.showUsuarioActual();
                                        System.out.println(product);

                                        inventarioService.crearProducto(product);

                                        List<Object[]> inventario = inventarioService.query(sqlProductos);
                                        tableModelInventario = new CustomTableModel(cabeceraProductos, inventario);
                                        inventarioTableConfig();

                                    }
                                    if (action == SimpleModalBorder.CANCEL_OPTION) {
                                        System.out.println("Cancelar");
                                    }

                                }));
                break;
            case "inventario-eliminar":
                PanelBorrarInventario eliminarProducto = new PanelBorrarInventario();
                List<Object[]> productosEliminar = this.tableModelInventario.getSelectedRowsForMe();
                if (productosEliminar.size() == 0) {
                    ModalDialog.showModal(this.ven, new SimpleModalBorder(new JLabel(""),
                            "<html><b>Por favor:</b><br>Seleccione al menos una opción</html>"));
                } else {

                    eliminarProducto.getProductos().setModel(new CustomTableModel(cabeceraProductos, productosEliminar, true));
                    eliminarProducto.getProductos().getColumnModel().getColumn(0).setMaxWidth(10);
                    ModalDialog.showModal(this.ven,
                            new SimpleModalBorder(eliminarProducto, "Inventario", SimpleModalBorder.DEFAULT_OPTION,
                                    (controller, action) -> {

                                        establecerSecion.showUsuarioActual();
                                        if (action == SimpleModalBorder.OK_OPTION) {
                                            System.out.println("Confirmar Eliminacion");
                                            StringBuilder sb = new StringBuilder();
                                            for (Object[] i : productosEliminar) {
                                                sb.append("'" + (String) i[1] + "', ");
                                            }
                                            sb.setLength(sb.length() - 2);
                                            System.out.println(sb.toString());
                                            inventarioService.eliminarProducto(sb.toString());

                                            List<Object[]> productos = inventarioService.query(sqlProductos);
                                            tableModelInventario = new CustomTableModel(cabeceraProductos, productos);
                                            inventarioTableConfig();

                                        }
                                        if (action == SimpleModalBorder.CANCEL_OPTION) {
                                            System.out.println("Cancelar eliminacion");
                                        }

                                    }));
                }
                break;
            case "inventario-actualizar":
                PanelDatosInventario actualizarProducto = new PanelDatosInventario();
                List<Object[]> productoActualizar = this.tableModelInventario.getSelectedRowsForMe();
                if (productoActualizar.size() != 1) {
                    ModalDialog.showModal(this.ven, new SimpleModalBorder(new JLabel(""),
                            "<html><b>Por favor:</b><br>Seleccione solo una opción</html>"));
                } else {
                    // En esta parte establecemos el usuario o persona
                    String codigo = (String) productoActualizar.get(0)[1];
                    Producto aux = inventarioService.getProducto(codigo);
                    actualizarProducto.getCodigo().setText(aux.getIdProducto());
                    actualizarProducto.getCodigo().setEnabled(false);
                    actualizarProducto.getNombreProducto().setText(aux.getNombre());
                    actualizarProducto.getTotalPrecio().setText(aux.getPrecio().toString());
                    actualizarProducto.getSpinner().setValue((Integer)aux.getInventario());

                    ModalDialog.showModal(this.ven,
                            new SimpleModalBorder(actualizarProducto, "Producto", SimpleModalBorder.DEFAULT_OPTION,
                                    (controller, action) -> {
                                        if (action == SimpleModalBorder.OK_OPTION) {
                                            System.out.println("se acepto");
                                            String cod = actualizarProducto.getCodigo().getText().trim();
                                        String nombreProducto = actualizarProducto.getNombreProducto().getText().toLowerCase().trim();
                                        String valor = actualizarProducto.getTotalPrecio().getText().trim().replaceAll(",", ".");

                                        BigDecimal precio = new BigDecimal(valor);
                                        int cantidad = (Integer)actualizarProducto.getSpinner().getValue();

                                        Producto product = Producto.builder()
                                                            .idProducto(cod)
                                                            .nombre(nombreProducto)
                                                            .precio(precio)
                                                            .inventario(cantidad)
                                                            .estado(TipoEstado.ACTIVO)
                                                            .build();

                                          

                                            establecerSecion.showUsuarioActual();
                                            System.out.println(product);

                                            inventarioService.actualizarProducto(cod, product);
                                            List<Object[]> productos = inventarioService.query(sqlProductos);
                                            tableModelInventario = new CustomTableModel(cabeceraProductos, productos);
                                            inventarioTableConfig();

                                        }
                                        if (action == SimpleModalBorder.CANCEL_OPTION) {
                                            System.out.println("Cancelar actualizacion");
                                        }

                                    }));
                }
          
                break;
            case "personas-crear":
                PanelDatosPersona crearPersona = new PanelDatosPersona();
                ModalDialog.showModal(this.ven,
                        new SimpleModalBorder(crearPersona, "Persona", SimpleModalBorder.DEFAULT_OPTION,
                                (controller, action) -> {
                                    if (action == SimpleModalBorder.OK_OPTION) {

                                        String identificacion = crearPersona.getIdentificacion().getText().trim();
                                        String pnombre = crearPersona.getPrimerNombre().getText().toLowerCase().trim();
                                        String snombre = crearPersona.getSegundoNombre().getText().toLowerCase().trim();
                                        String papellido = crearPersona.getPrimerApellido().getText().toLowerCase()
                                                .trim();
                                        String sapellido = crearPersona.getSegundoApellido().getText().toLowerCase()
                                                .trim();
                                        LocalDate nacimiento = crearPersona.getDatePicker().getSelectedDate();
                                        String telefono = crearPersona.getTelefono().getText().trim();
                                        TipoUsuario user = (TipoUsuario) crearPersona.getTipoUsuario()
                                                .getSelectedItem();

                                                if (esNuloOVacio(identificacion) || esNuloOVacio(pnombre) ||
                                                esNuloOVacio(papellido) || esNuloOVacio(sapellido) || nacimiento == null ||
                                                esNuloOVacio(telefono) || user == null) {
                                                JOptionPane.showMessageDialog(null, "Rellene todos los campos");
                                            }
                                                else if(contieneSoloLetras(pnombre+snombre+papellido+sapellido)){
                                                JOptionPane.showMessageDialog(null, "recuerde que los nombres y apellidos deben tener solamente letras");
                                            }
                                            else if (!esSoloNumeros(identificacion) || !esSoloNumeros(telefono)) {
                                                JOptionPane.showMessageDialog(null, "La cédula y el teléfono sólo pueden contener números");
                                            }

                                            else if (identificacion.length()!=10) {
                                                JOptionPane.showMessageDialog(null, "cédula inválida");
                                            }

                                        else{

                                        Persona p = Persona.builder()
                                                .identificacion(identificacion)
                                                .primerNombre(pnombre)
                                                .segundoNombre(snombre)
                                                .primerApellido(papellido)
                                                .segundoApellido(sapellido)
                                                .fechaNacimiento(nacimiento)
                                                .telefono(telefono)
                                                .tipoUsuario(user)
                                                .estado(TipoEstado.ACTIVO)
                                                .build();

                                        establecerSecion.showUsuarioActual();
                                        if (user == TipoUsuario.ADMINISTRADOR || user == TipoUsuario.EMPLEADO) {
                                            String username = crearPersona.getUsuarioField().getText().trim();
                                            String contraseña = new String(
                                                    crearPersona.getContrasenaField().getPassword());
                                            System.out.println(username + " -- " + contraseña);
                                            Usuario u = Usuario.builder()
                                                    .username(username)
                                                    .contrasena(contraseña)
                                                    .estado(TipoEstado.ACTIVO)
                                                    .persona(p)
                                                    .build();

                                            personaService.crearUsuario(p, u);
                                        } else {
                                            personaService.crearPersona(p);
                                        }

                                        JOptionPane.showMessageDialog(null, "persona creada");

                                        List<Object[]> usuarios = personaService.query(sqlpersonas);
                                        tableModelPersonas = new CustomTableModel(cabeceraPersonas, usuarios);
                                        // this.ven.getPanelPersonas().getTablaPersonas().setModel(tableModelInventario);
                                        personaTableConfig();

                                    }
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

                                        establecerSecion.showUsuarioActual();
                                        if (action == SimpleModalBorder.OK_OPTION) {
                                            System.out.println("Confirmar Eliminacion");
                                            StringBuilder sb = new StringBuilder();
                                            for (Object[] i : personasEliminar) {
                                                sb.append("'" + (String) i[1] + "', ");
                                            }
                                            sb.setLength(sb.length() - 2);
                                            System.out.println(sb.toString());
                                            personaService.eliminarPersona(sb.toString());

                                            List<Object[]> usuarios = personaService.query(sqlpersonas);
                                            tableModelPersonas = new CustomTableModel(cabeceraPersonas, usuarios);
                                            personaTableConfig();

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
                    String id = (String) personasActualizar.get(0)[1];
                    Persona aux = personaService.getPersona(id);
                    actualizarPersona.getIdentificacion().setText(aux.getIdentificacion());
                    actualizarPersona.getIdentificacion().setEnabled(false);
                    actualizarPersona.getPrimerNombre().setText(aux.getPrimerNombre());
                    actualizarPersona.getSegundoNombre().setText(aux.getSegundoNombre());
                    actualizarPersona.getPrimerApellido().setText(aux.getPrimerApellido());
                    actualizarPersona.getSegundoApellido().setText(aux.getSegundoApellido());
                    actualizarPersona.getTelefono().setText(aux.getTelefono());
                    actualizarPersona.getDatePicker().setSelectedDate(aux.getFechaNacimiento());
                    actualizarPersona.getTipoUsuario().setSelectedItem(aux.getTipoUsuario());

                    if (aux.getTipoUsuario() == TipoUsuario.ADMINISTRADOR
                            || aux.getTipoUsuario() == TipoUsuario.EMPLEADO) {
                        Usuario user = personaService.getUsuario(aux.getIdentificacion());
                        actualizarPersona.getUsuarioField().setText(user.getUsername());
                        actualizarPersona.getUsuarioField().setEnabled(false);
                        actualizarPersona.getContrasenaField().setText(user.getContrasena());
                    }

                    ModalDialog.showModal(this.ven,
                            new SimpleModalBorder(actualizarPersona, "Persona", SimpleModalBorder.DEFAULT_OPTION,
                                    (controller, action) -> {
                                        if (action == SimpleModalBorder.OK_OPTION) {
                                            System.out.println("se acepto");
                                            String identificacion = actualizarPersona.getIdentificacion().getText()
                                                    .trim();
                                            String pnombre = actualizarPersona.getPrimerNombre().getText().toLowerCase()
                                                    .trim();
                                            String snombre = actualizarPersona.getSegundoNombre().getText()
                                                    .toLowerCase().trim();
                                            String papellido = actualizarPersona.getPrimerApellido().getText()
                                                    .toLowerCase()
                                                    .trim();
                                            String sapellido = actualizarPersona.getSegundoApellido().getText()
                                                    .toLowerCase()
                                                    .trim();
                                            LocalDate nacimiento = actualizarPersona.getDatePicker().getSelectedDate();
                                            String telefono = actualizarPersona.getTelefono().getText().trim();
                                            TipoUsuario user = (TipoUsuario) actualizarPersona.getTipoUsuario()
                                                    .getSelectedItem();

                                            Persona p = Persona.builder()
                                                    .identificacion(identificacion)
                                                    .primerNombre(pnombre)
                                                    .segundoNombre(snombre)
                                                    .primerApellido(papellido)
                                                    .segundoApellido(sapellido)
                                                    .fechaNacimiento(nacimiento)
                                                    .telefono(telefono)
                                                    .tipoUsuario(user)
                                                    .estado(TipoEstado.ACTIVO)
                                                    .build();

                                            establecerSecion.showUsuarioActual();
                                            
                                            if (user == TipoUsuario.ADMINISTRADOR || user == TipoUsuario.EMPLEADO) {
                                                String username = actualizarPersona.getUsuarioField().getText().trim();
                                                String contraseña = new String(
                                                        actualizarPersona.getContrasenaField().getPassword());
                                                System.out.println(username + " -- " + contraseña);
                                                Usuario u = Usuario.builder()
                                                        .username(username)
                                                        .contrasena(contraseña)
                                                        .estado(TipoEstado.ACTIVO)
                                                        .persona(p)
                                                        .build();

                                                personaService.actualizarUsuario(username, u);
                                                personaService.actualizarPersona(identificacion, p);
                                            } else {
                                                personaService.actualizarPersona(identificacion, p);
                                            }

                                            List<Object[]> usuarios = personaService.query(sqlpersonas);
                                            tableModelPersonas = new CustomTableModel(cabeceraPersonas, usuarios);
                                            personaTableConfig();

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


    private static boolean contieneSoloLetras(String cadena) {
        // Verifica que cada carácter sea una letra
        for (char c : cadena.toCharArray()) {
            if (!Character.isLetter(c)) { // Si encuentra algo que no sea letra
                return true; // Retorna true
            }
        }
        return false; // Retorna false si todos los caracteres son letras
    }

        // Método auxiliar para verificar si una cadena es nula o está vacía
        private static boolean esNuloOVacio(String campo) {
            return campo == null || campo.trim().isEmpty();
        }
        private static boolean esSoloNumeros(String cadena) {
            // Verifica que la cadena no sea nula ni vacía y que contenga solo dígitos
            return cadena != null && cadena.matches("\\d+");
        }



}
