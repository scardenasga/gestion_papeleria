import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;
import co.edu.unbosque.model.entity.CuentaContable;
import co.edu.unbosque.model.entity.DetalleContable; // Asegúrate de que la clase DetalleContable esté definida
import co.edu.unbosque.model.entity.NombreCuenta;
import co.edu.unbosque.model.entity.TipoCuenta;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;

public class CuentaContableTest {

    @Test
    public void testEntityCreation() {
        // Crea instancias de las enumeraciones
        NombreCuenta nombreCuenta = NombreCuenta.ACTIVO;
        TipoCuenta tipoCuenta = TipoCuenta.DEBITO;

        // Crea una lista de DetalleContable (puedes agregar más detalles si es necesario)
        DetalleContable detalleContable = new DetalleContable(); // Asegúrate de que la clase DetalleContable esté definida
        List<DetalleContable> detalles = new ArrayList<>();
        detalles.add(detalleContable);

        // Crea la entidad CuentaContable
        CuentaContable cuentaContable = CuentaContable.builder()
                .idCuenta((short) 1)
                .nombre(nombreCuenta)
                .tipo(tipoCuenta)
                .detalleContables(detalles)
                .build();

        // Verifica que los valores sean correctos
        assertNotNull(cuentaContable);
        assertEquals(1, cuentaContable.getIdCuenta());
        assertEquals(nombreCuenta, cuentaContable.getNombre());
        assertEquals(tipoCuenta, cuentaContable.getTipo());
        assertNotNull(cuentaContable.getDetalleContables());
        assertEquals(1, cuentaContable.getDetalleContables().size());
    }

    @Test
    public void testSetNombreCuenta() {
        CuentaContable cuentaContable = new CuentaContable();
        NombreCuenta nombreCuenta = NombreCuenta.PASIVO;

        cuentaContable.setNombre(nombreCuenta);
        assertEquals(nombreCuenta, cuentaContable.getNombre());
    }

    @Test
    public void testSetTipoCuenta() {
        CuentaContable cuentaContable = new CuentaContable();
        TipoCuenta tipoCuenta = TipoCuenta.CREDITO;

        cuentaContable.setTipo(tipoCuenta);
        assertEquals(tipoCuenta, cuentaContable.getTipo());
    }

    @Test
    public void testAddDetalleContable() {
        CuentaContable cuentaContable = new CuentaContable();
        DetalleContable detalleContable = new DetalleContable(); // Asegúrate de que la clase DetalleContable esté definida

        cuentaContable.getDetalleContables().add(detalleContable);
        assertEquals(1, cuentaContable.getDetalleContables().size());
    }

    @Test
    public void testRemoveDetalleContable() {
        CuentaContable cuentaContable = new CuentaContable();
        DetalleContable detalleContable = new DetalleContable(); // Asegúrate de que la clase DetalleContable esté definida

        cuentaContable.getDetalleContables().add(detalleContable);
        cuentaContable.getDetalleContables().remove(detalleContable);

        assertEquals(0, cuentaContable.getDetalleContables().size());
    }
}
