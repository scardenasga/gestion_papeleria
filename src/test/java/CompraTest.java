import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import co.edu.unbosque.model.entity.Compra;
import co.edu.unbosque.model.entity.AsientoContable;  // Asegúrate de que la clase AsientoContable esté definida
import co.edu.unbosque.model.entity.Persona;  // Asegúrate de que la clase Persona esté definida
import java.math.BigDecimal;
import java.sql.Timestamp;

public class CompraTest {

    @Test
    public void testEntityCreation() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        BigDecimal totalCompra = new BigDecimal("500.00");
        AsientoContable asientoContable = new AsientoContable(); // Asegúrate de que la clase AsientoContable esté definida
        Persona persona = new Persona(); // Asegúrate de que la clase Persona esté definida

        Compra compra = Compra.builder()
                .idCompra(1L)
                .fechaCompra(timestamp)
                .totalCompra(totalCompra)
                .asientoContable(asientoContable)
                .persona(persona)
                .build();

        assertNotNull(compra);
        assertEquals(1L, compra.getIdCompra());
        assertEquals(timestamp, compra.getFechaCompra());
        assertEquals(totalCompra, compra.getTotalCompra());
        assertEquals(asientoContable, compra.getAsientoContable());
        assertEquals(persona, compra.getPersona());
    }

    @Test
    public void testSetFechaCompra() {
        Timestamp initialTimestamp = new Timestamp(System.currentTimeMillis());
        Compra compra = Compra.builder()
                .fechaCompra(initialTimestamp)
                .build();

        assertEquals(initialTimestamp, compra.getFechaCompra());

        Timestamp newTimestamp = new Timestamp(System.currentTimeMillis() + 1000);
        compra.setFechaCompra(newTimestamp);
        assertEquals(newTimestamp, compra.getFechaCompra());
    }

    @Test
    public void testSetTotalCompra() {
        Compra compra = new Compra();
        BigDecimal newTotal = new BigDecimal("1000.00");

        compra.setTotalCompra(newTotal);
        assertEquals(newTotal, compra.getTotalCompra());
    }

    @Test
    public void testSetAsientoContable() {
        Compra compra = new Compra();
        AsientoContable asientoContable = new AsientoContable(); // Asegúrate de que la clase AsientoContable esté definida

        compra.setAsientoContable(asientoContable);
        assertEquals(asientoContable, compra.getAsientoContable());
    }

    @Test
    public void testSetPersona() {
        Compra compra = new Compra();
        Persona persona = new Persona(); // Asegúrate de que la clase Persona esté definida

        compra.setPersona(persona);
        assertEquals(persona, compra.getPersona());
    }
}
