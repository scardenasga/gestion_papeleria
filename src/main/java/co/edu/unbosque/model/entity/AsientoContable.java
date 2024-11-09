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
 * The persistent class for the asiento_contable database table.
 * 
 */
@Entity
@Table(name="asiento_contable")
@NamedQuery(name="AsientoContable.findAll", query="SELECT a FROM AsientoContable a")

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AsientoContable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_asiento")
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long idAsiento;

	@Column(name="fecha_asiento")
	private Timestamp fechaAsiento;

	@Column(name="total")
	private BigDecimal total;

	//bi-directional many-to-one association to Compra
	@OneToMany(mappedBy="asientoContable")
	@Builder.Default
	private List<Compra> compras = new ArrayList<>();

	//bi-directional many-to-one association to DetalleContable
	@OneToMany(mappedBy="asientoContable")
	@Builder.Default
	private List<DetalleContable> detalleContables = new ArrayList<>();

	//bi-directional many-to-one association to Venta
	@OneToMany(mappedBy="asientoContable")
	@Builder.Default
	private List<Venta> ventas = new ArrayList<>();
}