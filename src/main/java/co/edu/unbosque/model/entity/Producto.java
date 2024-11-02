package co.edu.unbosque.model.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;


/**
 * The persistent class for the producto database table.
 * 
 */
@Entity
@NamedQuery(name="Producto.findAll", query="SELECT p FROM Producto p")

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_producto")
	private String idProducto;

	@Column(name = "inventario")
	private Integer inventario;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "precio")
	private BigDecimal precio;

	//bi-directional many-to-one association to DetalleVenta
	@OneToMany(mappedBy="producto")
	@Builder.Default
	private List<DetalleVenta> detalleVentas = new ArrayList<>();

}