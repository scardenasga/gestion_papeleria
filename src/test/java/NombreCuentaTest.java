import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import co.edu.unbosque.model.entity.NombreCuenta;

public class NombreCuentaTest {

    @Test
    public void testEnumValues() {
        NombreCuenta[] expectedValues = {
            NombreCuenta.CAJA,
            NombreCuenta.CUENTAS_POR_COBRAR,
            NombreCuenta.INVENTARIOS,
            NombreCuenta.CUENTAS_POR_PAGAR,
            NombreCuenta.APORTES_DE_SOCIOS,
            NombreCuenta.UTILIDADES_REVENIDAS,
            NombreCuenta.INGRESO_POR_VENTAS,
            NombreCuenta.OTROS_INGRESOS,
            NombreCuenta.GASTOS_DE_ADMINISTRACION,
            NombreCuenta.GASTOS_DE_VENTAS,
            NombreCuenta.IMPUESTOS
        };

        assertArrayEquals(expectedValues, NombreCuenta.values());
    }

    @Test
    public void testEnumValueOf() {
        assertEquals(NombreCuenta.CAJA, NombreCuenta.valueOf("CAJA"));
        assertEquals(NombreCuenta.CUENTAS_POR_COBRAR, NombreCuenta.valueOf("CUENTAS_POR_COBRAR"));
        assertEquals(NombreCuenta.INVENTARIOS, NombreCuenta.valueOf("INVENTARIOS"));
        assertEquals(NombreCuenta.CUENTAS_POR_PAGAR, NombreCuenta.valueOf("CUENTAS_POR_PAGAR"));
        assertEquals(NombreCuenta.APORTES_DE_SOCIOS, NombreCuenta.valueOf("APORTES_DE_SOCIOS"));
        assertEquals(NombreCuenta.UTILIDADES_REVENIDAS, NombreCuenta.valueOf("UTILIDADES_REVENIDAS"));
        assertEquals(NombreCuenta.INGRESO_POR_VENTAS, NombreCuenta.valueOf("INGRESO_POR_VENTAS"));
        assertEquals(NombreCuenta.OTROS_INGRESOS, NombreCuenta.valueOf("OTROS_INGRESOS"));
        assertEquals(NombreCuenta.GASTOS_DE_ADMINISTRACION, NombreCuenta.valueOf("GASTOS_DE_ADMINISTRACION"));
        assertEquals(NombreCuenta.GASTOS_DE_VENTAS, NombreCuenta.valueOf("GASTOS_DE_VENTAS"));
        assertEquals(NombreCuenta.IMPUESTOS, NombreCuenta.valueOf("IMPUESTOS"));
    }
}
