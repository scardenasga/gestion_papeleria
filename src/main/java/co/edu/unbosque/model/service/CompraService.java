package co.edu.unbosque.model.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import co.edu.unbosque.model.dao.AsientoContableDAO;
import co.edu.unbosque.model.dao.CompraDAO;
import co.edu.unbosque.model.dao.CuentaContableDAO;
import co.edu.unbosque.model.dao.DetalleContableDAO;
import co.edu.unbosque.model.entity.AsientoContable;
import co.edu.unbosque.model.entity.Compra;
import co.edu.unbosque.model.entity.CuentaContable;
import co.edu.unbosque.model.entity.DetalleContable;

public class CompraService {

    private CompraDAO compraDAO;
    private AsientoContableDAO aisentoContableDAO;
    private DetalleContableDAO detalleContableDAO;
    private CuentaContableDAO cuentaContableDAO;

    public CompraService() {
        this.compraDAO = new CompraDAO();
        this.aisentoContableDAO = new AsientoContableDAO();
        this.detalleContableDAO = new DetalleContableDAO();
        this.cuentaContableDAO = new CuentaContableDAO();
    }

    public void agregarCompra(Compra nuevaCompra) {

        DetalleContable dc = DetalleContable.builder()
                .debe(nuevaCompra.getTotalCompra())
                .haber(BigDecimal.ZERO)
                .build();

        Object[] indice = cuentaContableDAO.executeSingleResultQuery(
                "SELECT * from cuenta_contable WHERE nombre = 'GASTOS_DE_ADMINISTRACION';");

        CuentaContable cuenta = cuentaContableDAO.findById((Short)indice[0]);
        System.out.println(cuenta);
        cuenta.getDetalleContables().add(dc);

        AsientoContable ac = AsientoContable.builder()
                .total(nuevaCompra.getTotalCompra())
                .fechaAsiento(new Timestamp(System.currentTimeMillis()))
                .build();
        // esta parte agrega la venta realizada a la tabla asiento contable
        // esto me permite realizar la conexion con las llaves foraneas
        ac.getCompras().add(nuevaCompra);
        ac.getDetalleContables().add(dc);

        // relacion de la venta con el asiento contable
        nuevaCompra.setAsientoContable(ac);

        dc.setCuentaContable(cuenta);
        dc.setAsientoContable(ac);

        // Guardar en el orden correcto:
        // 1. Guardar el detalle contable
        cuentaContableDAO.update((Short) indice[0], cuenta);
        // 2. Guardar el asiento contable
        aisentoContableDAO.save(ac);
        // 3. Guardar Asiento y venta
        detalleContableDAO.save(dc);
        compraDAO.save(nuevaCompra);

    }

    public void eliminarPedido(String codigo){
        compraDAO.executeCustomUpdate("UPDATE compra\n" + //
                        "SET estado = 'INACTIVO'\n" + //
                        "WHERE id_compra IN ("+codigo+");");

    }

    public Compra getPedido(Long id){
        return compraDAO.findById(id);
    }

    public void actualizarCompra(Long id, Compra nuevaCompra) {
    
        // 2. Obtener el asiento contable relacionado
        AsientoContable ac = aisentoContableDAO.findById(nuevaCompra.getAsientoContable().getIdAsiento());
        if (ac != null) {
            // Actualizar el total del asiento contable
            ac.setTotal(nuevaCompra.getTotalCompra());
            ac.setFechaAsiento(new Timestamp(System.currentTimeMillis())); // Actualizar la fecha si es necesario
    
            // Guardar los cambios en el asiento contable
            aisentoContableDAO.update(nuevaCompra.getAsientoContable().getIdAsiento(), ac);;
        }
        // 3. Actualizar el detalle contable relacionado
        // Buscar el detalle contable asociado a la cuenta y al asiento
        Object[] obj = detalleContableDAO.executeSingleResultQuery("select * from detalle_contable where id_asiento = "+nuevaCompra.getAsientoContable().getIdAsiento()+";");
        DetalleContable dc = detalleContableDAO.findById((Long)obj[0]);
        if (dc != null) {
            // Actualizar el detalle contable (debe y haber)
            dc.setDebe(nuevaCompra.getTotalCompra()); // Suponiendo que el debe está relacionado con el total de la compra
            dc.setHaber(BigDecimal.ZERO); // Si el haber está en cero (esto puede cambiar según el contexto)
    
            // Actualizar la relación con la cuenta contable
    
            // Guardar los cambios en el detalle contable
            detalleContableDAO.update(dc.getIdDetalle(),dc);
        }
    
        // 4. Guardar los cambios en la compra
        compraDAO.update(id, nuevaCompra);
    }
    
    

    public List<Object[]> query(String queryString){
        return compraDAO.executeCustomQuery(queryString);
    }

}
