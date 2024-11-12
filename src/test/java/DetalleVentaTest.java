import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import co.edu.unbosque.model.entity.DetalleVenta;
import co.edu.unbosque.model.entity.Producto; // Asegúrate de que esta clase esté definida
import co.edu.unbosque.model.entity.Venta;    // Asegúrate de que esta clase esté definida
import java.math.BigDecimal;

public class DetalleVentaTest {

    @Test
    public void testEntityCreation() {
        Integer cantidad = 5;
        BigDecimal subtotal = new BigDecimal("150.00");
        
        Producto producto = new Producto(); // Crear instancia ficticia
        Venta venta = new Venta();          // Crear instancia ficticia

        DetalleVenta detalleVenta = DetalleVenta.builder()
                .idDetalle(1L)
                .cantidad(cantidad)
                .subtotal(subtotal)
                .producto(producto)
                .venta(venta)
                .build();

        assertNotNull(detalleVenta);
        assertEquals(1L, detalleVenta.getIdDetalle());
        assertEquals(cantidad, detalleVenta.getCantidad());
        assertEquals(subtotal, detalleVenta.getSubtotal());
        assertEquals(producto, detalleVenta.getProducto());
        assertEquals(venta, detalleVenta.getVenta());
    }

    @Test
    public void testSetCantidad() {
        DetalleVenta detalleVenta = new DetalleVenta();
        Integer nuevaCantidad = 10;

        detalleVenta.setCantidad(nuevaCantidad);
        assertEquals(nuevaCantidad, detalleVenta.getCantidad());
    }

    @Test
    public void testSetSubtotal() {
        DetalleVenta detalleVenta = new DetalleVenta();
        BigDecimal nuevoSubtotal = new BigDecimal("200.00");

        detalleVenta.setSubtotal(nuevoSubtotal);
        assertEquals(nuevoSubtotal, detalleVenta.getSubtotal());
    }

    @Test
    public void testSetProducto() {
        DetalleVenta detalleVenta = new DetalleVenta();
        Producto producto = new Producto(); // Crear instancia ficticia

        detalleVenta.setProducto(producto);
        assertEquals(producto, detalleVenta.getProducto());
    }

    @Test
    public void testSetVenta() {
        DetalleVenta detalleVenta = new DetalleVenta();
        Venta venta = new Venta(); // Crear instancia ficticia

        detalleVenta.setVenta(venta);
        assertEquals(venta, detalleVenta.getVenta());
    }
}
