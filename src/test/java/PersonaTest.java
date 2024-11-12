import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import co.edu.unbosque.model.entity.Persona;
import co.edu.unbosque.model.entity.TipoUsuario;
import co.edu.unbosque.model.entity.Compra;
import co.edu.unbosque.model.entity.Usuario;
import co.edu.unbosque.model.entity.Venta;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PersonaTest {

    @Test
    public void testEntityCreation() {
        String identificacion = "123456789";
        LocalDate fechaNacimiento = LocalDate.of(1990, 5, 20);
        String primerApellido = "Gomez";
        String primerNombre = "Juan";
        String segundoApellido = "Perez";
        String segundoNombre = "Carlos";
        String telefono = "555-1234";
        TipoUsuario tipoUsuario = TipoUsuario.CLIENTE;

        Persona persona = Persona.builder()
                .identificacion(identificacion)
                .fechaNacimiento(fechaNacimiento)
                .primerApellido(primerApellido)
                .primerNombre(primerNombre)
                .segundoApellido(segundoApellido)
                .segundoNombre(segundoNombre)
                .telefono(telefono)
                .tipoUsuario(tipoUsuario)
                .build();

        assertNotNull(persona);
        assertEquals(identificacion, persona.getIdentificacion());
        assertEquals(fechaNacimiento, persona.getFechaNacimiento());
        assertEquals(primerApellido, persona.getPrimerApellido());
        assertEquals(primerNombre, persona.getPrimerNombre());
        assertEquals(segundoApellido, persona.getSegundoApellido());
        assertEquals(segundoNombre, persona.getSegundoNombre());
        assertEquals(telefono, persona.getTelefono());
        assertEquals(tipoUsuario, persona.getTipoUsuario());
    }

    @Test
    public void testAddCompra() {
        Persona persona = new Persona();
        Compra compra = new Compra();
        
        persona.getCompras().add(compra);
        
        assertNotNull(persona.getCompras());
        assertEquals(1, persona.getCompras().size());
        assertTrue(persona.getCompras().contains(compra));
    }

    @Test
    public void testAddUsuario() {
        Persona persona = new Persona();
        Usuario usuario = new Usuario();

        persona.getUsuarios().add(usuario);

        assertNotNull(persona.getUsuarios());
        assertEquals(1, persona.getUsuarios().size());
        assertTrue(persona.getUsuarios().contains(usuario));
    }

    @Test
    public void testAddVenta() {
        Persona persona = new Persona();
        Venta venta = new Venta();

        persona.getVentas().add(venta);

        assertNotNull(persona.getVentas());
        assertEquals(1, persona.getVentas().size());
        assertTrue(persona.getVentas().contains(venta));
    }

    @Test
    public void testToString() {
        Persona persona = Persona.builder()
                .identificacion("123456789")
                .fechaNacimiento(LocalDate.of(1990, 5, 20))
                .primerApellido("Gomez")
                .primerNombre("Juan")
                .segundoApellido("Perez")
                .segundoNombre("Carlos")
                .telefono("555-1234")
                .tipoUsuario(TipoUsuario.CLIENTE)
                .build();

        String expectedString = "Persona [identificacion=123456789, fechaNacimiento=1990-05-20, primerApellido=Gomez, primerNombre=Juan, segundoApellido=Perez, segundoNombre=Carlos, telefono=555-1234, tipoUsuario=CLIENTE]";
        assertEquals(expectedString, persona.toString());
    }
}
