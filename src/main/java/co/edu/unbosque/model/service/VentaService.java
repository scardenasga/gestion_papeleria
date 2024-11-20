package co.edu.unbosque.model.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JTable;

import co.edu.unbosque.model.dao.CompraDAO;
import co.edu.unbosque.model.dao.DetalleVentaDAO;
import co.edu.unbosque.model.dao.GenericDAO;
import co.edu.unbosque.model.dao.PersonaDAO;
import co.edu.unbosque.model.dao.ProductoDAO;
import co.edu.unbosque.model.dao.VentaDAO;
import co.edu.unbosque.model.entity.AsientoContable;
import co.edu.unbosque.model.entity.Compra;
import co.edu.unbosque.model.entity.DetalleVenta;
import co.edu.unbosque.model.entity.Persona;
import co.edu.unbosque.model.entity.Producto;
import co.edu.unbosque.model.entity.TipoUsuario;
import co.edu.unbosque.model.entity.Venta;

public class VentaService {
	private Contabilidad contabilidad;
	private PersonaDAO personadao;
	private CompraDAO compradao;

	public VentaService() {
		contabilidad = new Contabilidad();
		personadao = new PersonaDAO();
		compradao = new CompraDAO();

	}
/* 
//para cuando la persona no está registrada
	 public String createVenta(Long id, BigDecimal totalVenta, JTable detalles,


	 		String identificacion, LocalDate fechaNacimiento, String primerApellido, String primerNombre,
	 		String segundoApellido, String segundoNombre, String telefono

	 ) {

	 	try {

	 		Timestamp fecha = new Timestamp(new Date().getTime());


	 		Compra compra=new Compra(id, fecha, totalVenta, null, null, null);

			//compra service?

	 		//createPersona(identificacion, fechaNacimiento, primerApellido, primerNombre, segundoApellido, segundoNombre, telefono, compra);
			

	 		//createVenta(id, totalVenta, detalles, null, identificacion);

	 		return "la venta se ha creado con exito";
	 	} catch (Exception e) {
	 		return "se ha producido un error creando la venta";
	 	}
	 }

	 */
	 //para cuando la persona ya está registrada
	 public String createVenta(Long id, JTable detalles, String identificacion) {
	 	//try {
	 		Timestamp fecha = new Timestamp(new Date().getTime());
			 System.out.println("id "+identificacion);


	 		Persona persona = personadao.findById(identificacion);
			System.out.println(persona);
			System.out.println(identificacion);

			 if (persona==null || identificacion==null){
				return "no se ha encontrado la persona";
			 }

	 		Venta venta = new Venta(id, fecha, null, null, null, persona);

			// Obtener el número de filas y columnas directamente desde la tabla
			int rowCount = detalles.getRowCount();
			List <DetalleVenta> detalleVen=new ArrayList<DetalleVenta>();


			// Inicializar la variable para acumular el total general
			BigDecimal totalGeneral = BigDecimal.ZERO;

			 if (rowCount<1){
			return "por favor agregue productos";
			 }

			for (int i = 0; i < rowCount; i++) {
			// Obtener el valor de la primera columna como String
			String codigo = (String) detalles.getValueAt(i, 0);

			// Obtener el valor de la segunda columna como int
			int cantidad = (int) detalles.getValueAt(i, 1);

			// Buscar el producto por su código
			Producto pro = (new ProductoDAO()).findById(codigo);
			if (pro==null){
				return "el producto " + " no existe";
			}

			// Obtener el precio usando el método findById de ProductoDAO
			BigDecimal precio = pro.getPrecio();

			// Calcular el total (precio * cantidad)
			BigDecimal total = precio.multiply(BigDecimal.valueOf(cantidad));

			// Acumular el total en la variable totalGeneral
			totalGeneral = totalGeneral.add(total);

			// Agregar el detalle de la venta
			DetalleVenta detalle=new DetalleVenta(null, cantidad, total, pro, venta);
			detalleVen.add(detalle);
			(new DetalleVentaDAO()).save(detalle);

		}

			// Mostrar el total general al final
			System.out.println("Total general: " + totalGeneral);



			Compra compra=new Compra(id, fecha, totalGeneral, null, null, persona); //al final actualizar los asientos contables
	 			

	 			 //actualizar las compras
	 			List<Compra> compras = new ArrayList<>();
	 			compras.add(compra);

	 			persona.setCompras(compras);
	 			personadao.update(identificacion, persona);




			venta.setDetalleVentas(detalleVen);


			venta.setTotalVenta(totalGeneral);



			contabilidad.agregarVenta(venta);
			compra.setAsientoContable((new VentaDAO()).findById(venta.getIdVenta()).getAsientoContable());
			compradao.save(compra);

	 		return "la venta se ha creado con exito";
	 	//} catch (Exception e) {
	 		//return "se ha producido un error creando la venta";
	 	//}
	 }

	// public String createPersona(String identificacion, LocalDate fechaNacimiento, String primerApellido,
	// 		String primerNombre, String segundoApellido, String segundoNombre, String telefono,

	// 		Compra compra) {
	// 	Persona persona = new Persona(identificacion, fechaNacimiento, primerApellido, primerNombre, segundoApellido,
	// 			segundoNombre, telefono, TipoUsuario.CLIENTE, null, null, null);

	// 	// agregar las compras
	// 	List<Compra> compras = new ArrayList<>();

	// 	compras.add(compra);

	// 	persona.setCompras(compras);

	// 	//guardar en la base de datos
	// 	compradao.save(compra);

	// 	personadao.save(persona);

	// 	return "persona creada";
	// }

}
