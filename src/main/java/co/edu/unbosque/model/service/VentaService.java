package co.edu.unbosque.model.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.edu.unbosque.model.dao.CompraDAO;
import co.edu.unbosque.model.dao.GenericDAO;
import co.edu.unbosque.model.dao.PersonaDAO;
import co.edu.unbosque.model.entity.AsientoContable;
import co.edu.unbosque.model.entity.Compra;
import co.edu.unbosque.model.entity.DetalleVenta;
import co.edu.unbosque.model.entity.Persona;
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

	// para cuando la persona no está registrada
	public String createVenta(Long id, BigDecimal totalVenta, List<DetalleVenta> detalleVenta,

			AsientoContable asientoContable,

			String identificacion, LocalDate fechaNacimiento, String primerApellido, String primerNombre,
			String segundoApellido, String segundoNombre, String telefono

	) {

		try {

			Timestamp fecha = new Timestamp(new Date().getTime());


			createPersona(identificacion, fechaNacimiento, primerApellido, primerNombre, segundoApellido, segundoNombre,
					telefono, 
					new Compra(id, fecha, totalVenta, asientoContable, null));
			

			createVenta(id, totalVenta, detalleVenta, asientoContable, null, identificacion);

			return "la venta se ha creado con exito";
		} catch (Exception e) {
			return "se ha producido un error creando la venta";
		}
	}

	// para cuando la persona ya está registrada
	public String createVenta(Long id, BigDecimal totalVenta, List<DetalleVenta> detalleVenta,
			AsientoContable asientoContable, Compra compra, String identificacion) {
		try {

			Timestamp fecha = new Timestamp(new Date().getTime());

			Persona persona = personadao.findById(identificacion);

			if (compra != null) {
				compradao.save(compra);

				// actualizar las compras
				List<Compra> compras = new ArrayList<>();
				compras.add(compra);
				persona.setCompras(compras);
				personadao.update(identificacion, persona);
			}

			Venta venta = new Venta(id, fecha, totalVenta, detalleVenta, asientoContable, persona);
			contabilidad.agregarVenta(venta);

			return "la venta se ha creado con exito";
		} catch (Exception e) {
			return "se ha producido un error creando la venta";
		}
	}

	public String createPersona(String identificacion, LocalDate fechaNacimiento, String primerApellido,
			String primerNombre, String segundoApellido, String segundoNombre, String telefono,

			Compra compra) {
		Persona persona = new Persona(identificacion, fechaNacimiento, primerApellido, primerNombre, segundoApellido,
				segundoNombre, telefono, TipoUsuario.CLIENTE, null, null, null);

		// agregar las compras
		List<Compra> compras = new ArrayList<>();

		compras.add(compra);

		persona.setCompras(compras);

		//guardar en la base de datos
		compradao.save(compra);

		personadao.save(persona);

		return "persona creada";
	}

}
