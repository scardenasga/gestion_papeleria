import co.edu.unbosque.model.entity.Producto;
import co.edu.unbosque.model.entity.DetalleVenta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProductoTest {

    private Producto producto;

    @BeforeEach
    public void setUp() {
        producto = Producto.builder()
                .idProducto("P001")
                .inventario(50)
                .nombre("Producto de Prueba")
                .precio(BigDecimal.valueOf(100.0))
                .build();
    }

    @Test
    public void testProductoCreation() {
        assertEquals("P001", producto.getIdProducto(), "El ID del producto debe ser 'P001'");
        assertEquals("Producto de Prueba", producto.getNombre(), "El nombre del producto debe ser 'Producto de Prueba'");
        assertEquals(50, producto.getInventario(), "El inventario debe ser 50");
        assertEquals(BigDecimal.valueOf(100.0), producto.getPrecio(), "El precio debe ser 100.0");
    }

    @Test
    public void testDetalleVentasAssociation() {
        DetalleVenta detalleVenta1 = new DetalleVenta();
        DetalleVenta detalleVenta2 = new DetalleVenta();
        producto.getDetalleVentas().add(detalleVenta1);
        producto.getDetalleVentas().add(detalleVenta2);

        List<DetalleVenta> detalleVentas = producto.getDetalleVentas();
        assertEquals(2, detalleVentas.size(), "Debe haber 2 detalles de venta asociados");
        assertTrue(detalleVentas.contains(detalleVenta1), "Debe contener detalleVenta1");
        assertTrue(detalleVentas.contains(detalleVenta2), "Debe contener detalleVenta2");
    }

    @Test
    public void testSetAndGetInventario() {
        producto.setInventario(30);
        assertEquals(30, producto.getInventario(), "El inventario debe ser actualizado a 30");
    }

    @Test
    public void testSetAndGetPrecio() {
        producto.setPrecio(BigDecimal.valueOf(200.0));
        assertEquals(BigDecimal.valueOf(200.0), producto.getPrecio(), "El precio debe ser actualizado a 200.0");
    }
}
