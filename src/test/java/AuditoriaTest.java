import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import co.edu.unbosque.model.entity.Auditoria;
import co.edu.unbosque.model.entity.Usuario; // Asegúrate de que la clase Usuario esté definida
import java.sql.Timestamp;

public class AuditoriaTest {

    @Test
    public void testEntityCreation() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Usuario usuario = new Usuario();  // Asegúrate de que la clase Usuario esté definida

        Auditoria auditoria = Auditoria.builder()
                .idAuditoria(1L)
                .accion("CREATE")
                .fecha(timestamp)
                .registroId("12345")
                .tabla("some_table")
                .usuario(usuario)
                .build();

        assertNotNull(auditoria);
        assertEquals(1L, auditoria.getIdAuditoria());
        assertEquals("CREATE", auditoria.getAccion());
        assertEquals(timestamp, auditoria.getFecha());
        assertEquals("12345", auditoria.getRegistroId());
        assertEquals("some_table", auditoria.getTabla());
        assertEquals(usuario, auditoria.getUsuario());
    }

    @Test
    public void testFechaSetting() {
        Timestamp initialTimestamp = new Timestamp(System.currentTimeMillis());
        Auditoria auditoria = Auditoria.builder()
                .fecha(initialTimestamp)
                .build();

        assertEquals(initialTimestamp, auditoria.getFecha());

        Timestamp newTimestamp = new Timestamp(System.currentTimeMillis() + 1000);
        auditoria.setFecha(newTimestamp);
        assertEquals(newTimestamp, auditoria.getFecha());
    }

    @Test
    public void testSetUsuario() {
        Auditoria auditoria = new Auditoria();
        Usuario usuario = new Usuario(); // Asegúrate de que la clase Usuario esté definida

        auditoria.setUsuario(usuario);
        assertEquals(usuario, auditoria.getUsuario());
    }

    @Test
    public void testSetAccion() {
        Auditoria auditoria = new Auditoria();
        auditoria.setAccion("DELETE");

        assertEquals("DELETE", auditoria.getAccion());
    }
}
