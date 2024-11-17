package co.edu.unbosque.model.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;


/**
 * The persistent class for the venta database table.
 * 
 */
@Entity
@NamedQuery(name="Venta.findAll", query="SELECT v FROM Venta v")

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Venta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_venta")
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long idVenta;

	@Column(name="fecha_venta")
	private Timestamp fechaVenta;

	@Column(name="total_venta")
	private BigDecimal totalVenta;

	@Column(name = "estado")
	@Enumerated(EnumType.STRING)
	private TipoEstado estado;

	//bi-directional many-to-one association to DetalleVenta
	@OneToMany(mappedBy="venta")
	@Builder.Default
	private List<DetalleVenta> detalleVentas = new ArrayList<>();

	//bi-directional many-to-one association to AsientoContable
	@ManyToOne
	@JoinColumn(name="id_asiento")
	private AsientoContable asientoContable;

	//bi-directional many-to-one association to Persona
	@ManyToOne
	@JoinColumn(name="id_persona")
	private Persona persona;

}