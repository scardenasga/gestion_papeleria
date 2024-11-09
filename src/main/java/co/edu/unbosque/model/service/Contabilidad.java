package co.edu.unbosque.model.service;

import java.math.BigDecimal;
import java.sql.Timestamp;

import co.edu.unbosque.model.dao.AsientoContableDAO;
import co.edu.unbosque.model.dao.CompraDAO;
import co.edu.unbosque.model.dao.CuentaContableDAO;
import co.edu.unbosque.model.dao.VentaDAO;
import co.edu.unbosque.model.entity.AsientoContable;
import co.edu.unbosque.model.entity.CuentaContable;
import co.edu.unbosque.model.entity.DetalleContable;
import co.edu.unbosque.model.entity.Venta;
import co.edu.unbosque.model.dao.DetalleContableDAO;

public class Contabilidad {

    private AsientoContableDAO asientoContableDao;
    private DetalleContableDAO detalleContableDao;
    private CuentaContableDAO cuentaContableDao;
    private VentaDAO ventaDao;
    private CompraDAO compraDao;

    public Contabilidad(){
        this.asientoContableDao = new AsientoContableDAO();
        this.detalleContableDao = new DetalleContableDAO();
        this.cuentaContableDao = new CuentaContableDAO();
        this.ventaDao = new VentaDAO();
        this.compraDao = new CompraDAO();
    }

    public void agregarVenta(Venta ventaRealizada) {

        DetalleContable dc = DetalleContable.builder()
                .debe(BigDecimal.ZERO)
                .haber(ventaRealizada.getTotalVenta())
                .build();

        Object[] indice = ventaDao.executeSingleResultQuery("SELECT id_cuenta FROM cuenta_contable WHERE nombre = \'INGRESO_POR_VENTAS\';");
        
        CuentaContable cuenta = cuentaContableDao.findById((Short)indice[0]);
        cuenta.getDetalleContables().add(dc);



        AsientoContable ac = AsientoContable.builder()
                .total(ventaRealizada.getTotalVenta())
                .fechaAsiento(new Timestamp(System.currentTimeMillis()))
                .build();
        // esta parte agrega la venta realizada a la tabla asiento contable
        // esto me permite realizar la conexion con las llaves foraneas
        ac.getVentas().add(ventaRealizada);
        ac.getDetalleContables().add(dc);

        //relacion de la venta con el asiento contable
        ventaRealizada.setAsientoContable(ac);
        

        dc.setCuentaContable(cuenta);
        dc.setAsientoContable(ac);


        //Guardar en el orden correcto:
        //1. Guardar el detalle contable
        cuentaContableDao.update((Short)indice[0], cuenta);
        //2. Guardar el asiento contable
        asientoContableDao.save(ac);
        //3. Guardar Asiento y venta
        detalleContableDao.save(dc);
        ventaDao.save(ventaRealizada);


    }

}
