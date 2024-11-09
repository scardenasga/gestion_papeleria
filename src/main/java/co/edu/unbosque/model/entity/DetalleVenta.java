package co.edu.unbosque.model.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


/**
 * The persistent class for the detalle_venta database table.
 * 
 */
@Entity
@Table(name="detalle_venta")
@NamedQuery(name="DetalleVenta.findAll", query="SELECT d FROM DetalleVenta d")

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleVenta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_detalle")
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long idDetalle;

	@Column(name = "cantidad")
	private Integer cantidad;

	@Column(name = "subtotal")
	private BigDecimal subtotal;

	//bi-directional many-to-one association to Producto
	@ManyToOne
	@JoinColumn(name="id_producto")
	private Producto producto;

	//bi-directional many-to-one association to Venta
	@ManyToOne
	@JoinColumn(name="id_venta")
	private Venta venta;

}