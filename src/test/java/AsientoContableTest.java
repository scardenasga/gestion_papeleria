import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import co.edu.unbosque.model.entity.AsientoContable;
import co.edu.unbosque.model.entity.Compra; 
import java.math.BigDecimal;
import java.sql.Timestamp;

public class AsientoContableTest {

    @Test
    public void testEntityCreation() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        BigDecimal total = new BigDecimal("1000.00");
        
        AsientoContable asiento = AsientoContable.builder()
                .idAsiento(1L)
                .fechaAsiento(timestamp)
                .total(total)
                .build();

        assertNotNull(asiento);
        assertEquals(1L, asiento.getIdAsiento());
        assertEquals(timestamp, asiento.getFechaAsiento());
        assertEquals(total, asiento.getTotal());
    }

    @Test
    public void testInitialListsAreEmpty() {
        AsientoContable asiento = AsientoContable.builder().build();

        assertNotNull(asiento.getCompras());
        assertTrue(asiento.getCompras().isEmpty());

        assertNotNull(asiento.getDetalleContables());
        assertTrue(asiento.getDetalleContables().isEmpty());

        assertNotNull(asiento.getVentas());
        assertTrue(asiento.getVentas().isEmpty());
    }

    @Test
    public void testAddToComprasList() {
        AsientoContable asiento = AsientoContable.builder().build();

        Compra compra = new Compra(); 
        asiento.getCompras().add(compra);

        assertEquals(1, asiento.getCompras().size());
        assertEquals(compra, asiento.getCompras().get(0));
    }

    @Test
    public void testTotalWithEdgeValues() {
        AsientoContable asiento = AsientoContable.builder().build();

        asiento.setTotal(BigDecimal.ZERO);
        assertEquals(BigDecimal.ZERO, asiento.getTotal());

        asiento.setTotal(new BigDecimal("-100.00"));
        assertEquals(new BigDecimal("-100.00"), asiento.getTotal());
    }

    @Test
    public void testFechaAsiento() {
        Timestamp initialTimestamp = new Timestamp(System.currentTimeMillis());
        AsientoContable asiento = AsientoContable.builder()
                .fechaAsiento(initialTimestamp)
                .build();

        assertEquals(initialTimestamp, asiento.getFechaAsiento());

        Timestamp newTimestamp = new Timestamp(System.currentTimeMillis() + 1000);
        asiento.setFechaAsiento(newTimestamp);
        assertEquals(newTimestamp, asiento.getFechaAsiento());
    }
}
