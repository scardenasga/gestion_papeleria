package co.edu.unbosque.model.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import co.edu.unbosque.model.dao.AsientoContableDAO;
import co.edu.unbosque.model.dao.CompraDAO;
import co.edu.unbosque.model.dao.CuentaContableDAO;
import co.edu.unbosque.model.dao.CuentaDAO;
import co.edu.unbosque.model.dao.VentaDAO;
import co.edu.unbosque.model.entity.AsientoContable;
import co.edu.unbosque.model.entity.Compra;
import co.edu.unbosque.model.entity.CuentaContable;
import co.edu.unbosque.model.entity.DetalleContable;
import co.edu.unbosque.model.entity.Venta;
import co.edu.unbosque.model.dao.DetalleContableDAO;

public class Contabilidad {

    private AsientoContableDAO asientoContableDao;
    private DetalleContableDAO detalleContableDao;
    private CuentaContableDAO cuentaContableDao;
    private VentaDAO ventaDAO;
    private CompraDAO compraDAO;
    private CuentaDAO cuentaDAO;

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
    public CompletableFuture<Void> registrarCierreCaja(java.sql.Date fecha, BigDecimal efectivoFinal) {
        AsientoContable asiento = AsientoContable.builder()
            .fechaAsiento(new Timestamp(fecha.getTime())) // Usa java.sql.Date para crear Timestamp
            .total(efectivoFinal)
            .build();
    
        return CompletableFuture.runAsync(() -> cuentaDAO.save(asiento));
    }

    
    /**
     * Realiza el cierre de caja para la fecha actual.
     *
     * @return Un CompletableFuture que indica la finalización de la operación.
     */
    public CompletableFuture<Void> realizarCierreCaja() {
        // Obtener la fecha actual
        LocalDate fechaActual = LocalDate.now();
        
        // Convertir LocalDate a java.sql.Date
        java.sql.Date sqlDate = java.sql.Date.valueOf(fechaActual);
        
        return calcularEfectivoFinal(fechaActual) // Mantener LocalDate para el cálculo
            .thenCompose(efectivoFinal -> registrarCierreCaja(sqlDate, efectivoFinal)); // Llama a registrarCierreCaja con java.sql.Date
    }


    // Clase interna para almacenar los resultados de costos
    public class CostosTotales {
        private BigDecimal costoTotalCompras;
        private BigDecimal costoTotalVentas;

        public CostosTotales(BigDecimal costoTotalCompras, BigDecimal costoTotalVentas) {
            this.costoTotalCompras = costoTotalCompras;
            this.costoTotalVentas = costoTotalVentas;
        }

        public BigDecimal getCostoTotalCompras() {
            return costoTotalCompras;
        }

        public BigDecimal getCostoTotalVentas() {
            return costoTotalVentas;
        }
    }

    public CompletableFuture<CostosTotales> consultarCostos(LocalDate fechaInicio, LocalDate fechaFin) {
        return CompletableFuture.supplyAsync(() -> {
            BigDecimal costoTotalCompras = BigDecimal.ZERO;
            BigDecimal costoTotalVentas = BigDecimal.ZERO;
    
            // Consulta para obtener el costo total de compras
            String sqlCompras = "SELECT SUM(total_compra) AS costo_total_compras FROM compra";
            Object[] resultCompras = compraDAO.executeSingleResultQuery(sqlCompras);
            if (resultCompras != null && resultCompras[0] != null) {
                costoTotalCompras = (BigDecimal) resultCompras[0];
            }
    
            // Consulta para obtener el costo total de ventas
            String sqlVentas = "SELECT SUM(total_venta) AS costo_total_ventas FROM venta";
            Object[] resultVentas = ventaDAO.executeSingleResultQuery(sqlVentas);
            if (resultVentas != null && resultVentas[0] != null) {
                costoTotalVentas = (BigDecimal) resultVentas[0];
            }
    
            // Si se proporcionan fechas, calcular costos en el rango
            if (fechaInicio != null && fechaFin != null) {
                String sqlComprasRango = "SELECT SUM(total_compra) AS costo_total_compras FROM compra WHERE fecha_compra BETWEEN ? AND ?";
                Object[] resultComprasRango = compraDAO.executeSingleResultQuery(sqlComprasRango, fechaInicio, fechaFin);
                if (resultComprasRango != null && resultComprasRango[0] != null) {
                    costoTotalCompras = (BigDecimal) resultComprasRango[0];
                }
    
                String sqlVentasRango = "SELECT SUM(total_venta) AS costo_total_ventas FROM venta WHERE fecha_venta BETWEEN ? AND ?";
                Object[] resultVentasRango = ventaDAO.executeSingleResultQuery(sqlVentasRango, fechaInicio, fechaFin);
                if (resultVentasRango != null && resultVentasRango[0] != null) {
                    costoTotalVentas = (BigDecimal) resultVentasRango[0];
                }
            }
    
            return new CostosTotales(costoTotalCompras, costoTotalVentas);
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