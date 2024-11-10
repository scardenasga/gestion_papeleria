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
        this.ventaDAO = new VentaDAO();
        this.compraDAO = new CompraDAO();
        this.cuentaDAO = new CuentaDAO();
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

    public void agregarVenta(Venta ventaRealizada) {

        DetalleContable dc = DetalleContable.builder()
                .debe(BigDecimal.ZERO)
                .haber(ventaRealizada.getTotalVenta())
                .build();

        Object[] indice = ventaDAO.executeSingleResultQuery("SELECT id_cuenta FROM cuenta_contable WHERE nombre = \'INGRESO_POR_VENTAS\';");
        
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
        ventaDAO.save(ventaRealizada);


    }

    /**
     * Calcula el total de ventas para una fecha específica.
     *
     * @param fecha La fecha para la cual se calcularán las ventas totales.
     * @return Un CompletableFuture que contiene el total de ventas para la fecha dada.
     */
    public CompletableFuture<BigDecimal> calcularVentasTotales(LocalDate fecha) {
        return ventaDAO.findByAtribute("fechaVenta", fecha)
            .thenApply(ventas -> ventas.stream()
                .map(Venta::getTotalVenta)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
    }

    /**
     * Calcula el total de compras para una fecha específica.
     *
     * @param fecha La fecha para la cual se calcularán las compras totales.
     * @return Un CompletableFuture que contiene el total de compras para la fecha dada.
     */
    public CompletableFuture<BigDecimal> calcularComprasTotales(LocalDate fecha) {
        return compraDAO.findByAtribute("fechaCompra", fecha)
            .thenApply(compras -> compras.stream()
                .map(Compra::getTotalCompra)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
    }

    /**
     * Calcula el efectivo final para una fecha específica,
     * restando el total de compras del total de ventas.
     *
     * @param fecha La fecha para la cual se calculará el efectivo final.
     * @return Un CompletableFuture que contiene el efectivo final.
     */
    public CompletableFuture<BigDecimal> calcularEfectivoFinal(LocalDate fecha) {
        CompletableFuture<BigDecimal> ventasFuture = calcularVentasTotales(fecha);
        CompletableFuture<BigDecimal> comprasFuture = calcularComprasTotales(fecha);

        return ventasFuture.thenCombine(comprasFuture, (ventas, compras) -> ventas.subtract(compras));
    }

    /**
     * Registra un cierre de caja en la base de datos.
     *
     * @param fecha La fecha del cierre de caja.
     * @param efectivoFinal El efectivo final a registrar.
     * @return Un CompletableFuture que indica la finalización de la operación.
     */
    public CompletableFuture<Void> registrarCierreCaja(LocalDate fecha, BigDecimal efectivoFinal) {
        AsientoContable asiento = AsientoContable.builder()
            .fechaAsiento(java.sql.Timestamp.valueOf(fecha.atStartOfDay()))
            .total(efectivoFinal)
            .build();

        return CompletableFuture.runAsync(() -> cuentaDAO.save(asiento));
    }

    /**
     * Realiza el cierre de caja para una fecha específica.
     *
     * @param fecha La fecha para la cual se realizará el cierre de caja.
     * @return Un CompletableFuture que indica la finalización de la operación.
     */
    public CompletableFuture<Void> realizarCierreCaja(LocalDate fecha) {
        return calcularEfectivoFinal(fecha)
            .thenCompose(efectivoFinal -> registrarCierreCaja(fecha, efectivoFinal));
    }

    /**
     * Calcula el costo total de las ventas en un rango de fechas.
     *
     * @param fechaInicio La fecha de inicio del rango.
     * @param fechaFin La fecha de fin del rango.
     * @return Un CompletableFuture que contiene el costo total de las ventas en el rango de fechas especificado.
     */
    public CompletableFuture<BigDecimal> calcularCostoTotalVentas(LocalDate fechaInicio, LocalDate fechaFin) {
        return ventaDAO.findByDateRange(fechaInicio, fechaFin)
            .thenApply(ventas -> {
                BigDecimal total = BigDecimal.ZERO;
                for (Venta venta : ventas) {
                    total = total.add(venta.getTotalVenta()); // Asumiendo que 'getTotalVenta' retorna el costo de la venta
                }
                return total;
            });
    }

    /**
     * Calcula el total de ventas en un rango de fechas.
     *
     * @param fechaInicio La fecha de inicio del rango.
     * @param fechaFin La fecha de fin del rango.
     * @return Un CompletableFuture que contiene el total de ventas en el rango de fechas especificado.
     */
    public CompletableFuture<BigDecimal> calcularTotalVentas(LocalDate fechaInicio, LocalDate fechaFin) {
        return ventaDAO.findByDateRange(fechaInicio, fechaFin)
            .thenApply(ventas -> ventas.stream()
                .map(Venta::getTotalVenta)
                .reduce(BigDecimal.ZERO, BigDecimal::add))
            .exceptionally(ex -> {
                // Manejo de excepciones
                System.err.println("Error al calcular el total de ventas: " + ex.getMessage());
                return BigDecimal.ZERO; // Retornar un valor por defecto en caso de error
            });
    }
    /**
     * Consulta las ventas realizadas en una fecha específica.
     *
     * @param fecha La fecha para la cual se consultarán las ventas.
     * @return Un CompletableFuture que contiene una lista de ventas para la fecha especificada.
     */
    // Método para consultar ventas por fecha
    public CompletableFuture<List<Venta>> consultarVentasPorFecha(LocalDate fecha) {
        return ventaDAO.findByAtribute("fechaVenta", fecha) // Asumiendo que este método está implementado en VentaDAO
            .thenApply(ventas -> {
                // Aquí puedes agregar lógica adicional si es necesario
                return ventas;
            });
    }
    
    /**
     * Registra un costo en el sistema.
     *
     * @param descripcion La descripción del costo a registrar.
     * @param monto El monto del costo a registrar.
     * @return Un CompletableFuture que indica la finalización de la operación.
     */
    // Método para registrar un costo
    public CompletableFuture<Void> registrarCosto(String descripcion, BigDecimal monto) {
        DetalleContable detalle = DetalleContable.builder()
                .debe(monto) // Asumiendo que el costo se registra en el debe
                .haber(BigDecimal.ZERO) // No hay haber en este caso
                .build();
    
        AsientoContable asiento = AsientoContable.builder()
                .total(monto)
                .fechaAsiento(new Timestamp(System.currentTimeMillis()))
                .build();
    
        return CompletableFuture.runAsync(() -> {
            // Guardar el asiento contable primero
            asientoContableDao.save(asiento);
            detalle.setAsientoContable(asiento); // Relacionar detalle con el asiento
            detalleContableDao.save(detalle); // Guardar el detalle contable
        });
    }

    /**
     * Registra un gasto en el sistema.
     *
     * @param descripcion La descripción del gasto a registrar.
     * @param monto El monto del gasto a registrar.
     * @return Un CompletableFuture que indica la finalización de la operación.
     */
    // Método para registrar un gasto
    public CompletableFuture<Void> registrarGasto(String descripcion, BigDecimal monto) {
        DetalleContable detalle = DetalleContable.builder()
                .debe(BigDecimal.ZERO) // No hay debe en este caso
                .haber(monto) // Asumiendo que el gasto se registra en el haber
                .build();
    
        AsientoContable asiento = AsientoContable.builder()
                .total(monto)
                .fechaAsiento(new Timestamp(System.currentTimeMillis()))
                .build();
    
        return CompletableFuture.runAsync(() -> {
            // Guardar el asiento contable primero
            asientoContableDao.save(asiento);
            detalle.setAsientoContable(asiento); // Relacionar detalle con el asiento
            detalleContableDao.save(detalle); // Guardar el detalle contable
        });
    }

    /**
     * Genera la facturación electrónica para las ventas de una fecha específica.
     *
     * @param fecha La fecha para la cual se generará la facturación electrónica.
     * @return Un CompletableFuture que contiene una lista de objetos FacturaElectronicaDTO con la información de facturación.
     */
    public CompletableFuture<List<FacturaElectronicaDTO>> generarFacturacionElectronica(LocalDate fecha) {
        return ventaDAO.findByAtribute("fechaVenta", fecha)
            .thenApply(ventas -> ventas.stream()
                .map(venta -> {
                    // Obtener los datos necesarios de cada venta
                    String cedula = venta.getPersona().getIdentificacion(); // Asumiendo que la clase Persona tiene el método getIdentificacion
                    Timestamp fechaVentaTimestamp = venta.getFechaVenta(); // Obtener el Timestamp
                    LocalDate fechaVenta = fechaVentaTimestamp.toLocalDateTime().toLocalDate(); // Convertir Timestamp a LocalDate
                    BigDecimal valorTotal = venta.getTotalVenta();
                    BigDecimal valorConIVA = valorTotal.multiply(new BigDecimal("1.19")); // Asumiendo que el IVA es del 19%
    
                    // Crear y retornar el DTO para la facturación electrónica
                    return new FacturaElectronicaDTO(cedula, fechaVenta, valorTotal, valorConIVA);
                })
                .collect(Collectors.toList()));
    }

    // DTO para la facturación electrónica
class FacturaElectronicaDTO {
    private String cedula;
    private LocalDate fechaVenta;
    private BigDecimal valorTotal;
    private BigDecimal valorConIVA;

    public FacturaElectronicaDTO(String cedula, LocalDate fechaVenta, BigDecimal valorTotal, BigDecimal valorConIVA) {
        this.cedula = cedula;
        this.fechaVenta = fechaVenta;
        this.valorTotal = valorTotal;
        this.valorConIVA = valorConIVA;
    }

    // Getters y setters
    public String getCedula() {
        return cedula;
    }

    public LocalDate getFechaVenta() {
        return fechaVenta;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public BigDecimal getValorConIVA() {
        return valorConIVA;
    }

    }

}
