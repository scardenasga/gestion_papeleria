import co.edu.unbosque.model.dao.GenericDAO;
import co.edu.unbosque.model.entity.Persona;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

public class GenericDAOTest {

    private GenericDAO<String, Persona> personaDAO;

    @BeforeEach
    public void setUp() {
        personaDAO = new GenericDAO<>(Persona.class);
    }

    @Test
    public void testSaveAndFindById() {
        Persona persona = Persona.builder()
                .identificacion("12345")
                .primerNombre("Juan")
                .primerApellido("Pérez")
                .fechaNacimiento(LocalDate.of(1990, 1, 1))
                .build();

        personaDAO.save(persona);
        Persona foundPersona = personaDAO.findById("12345");

        assertNotNull(foundPersona, "La persona debe existir en la base de datos después de guardarse");
        assertEquals("Juan", foundPersona.getPrimerNombre(), "El nombre debe coincidir con el de la entidad guardada");
    }

    @Test
    public void testUpdate() {
        Persona persona = Persona.builder()
                .identificacion("12345")
                .primerNombre("Juan")
                .primerApellido("Pérez")
                .fechaNacimiento(LocalDate.of(1990, 1, 1))
                .build();

        personaDAO.save(persona);

        persona.setPrimerNombre("Carlos");
        personaDAO.update("12345", persona);
        
        Persona updatedPersona = personaDAO.findById("12345");
        assertEquals("Carlos", updatedPersona.getPrimerNombre(), "El nombre debe haberse actualizado a 'Carlos'");
    }

    @Test
    public void testDelete() {
        Persona persona = Persona.builder()
                .identificacion("12345")
                .primerNombre("Juan")
                .primerApellido("Pérez")
                .build();

        personaDAO.save(persona);
        personaDAO.delete("12345");

        Persona deletedPersona = personaDAO.findById("12345");
        assertNull(deletedPersona, "La persona debe haberse eliminado de la base de datos");
    }

    @Test
    public void testFindAll() throws ExecutionException, InterruptedException {
        Persona persona1 = Persona.builder().identificacion("123").primerNombre("Ana").build();
        Persona persona2 = Persona.builder().identificacion("456").primerNombre("Luis").build();
        
        personaDAO.save(persona1);
        personaDAO.save(persona2);

        List<Persona> personas = personaDAO.findAll().get();
        assertTrue(personas.size() >= 2, "Debe haber al menos dos personas en la lista");
    }
}
