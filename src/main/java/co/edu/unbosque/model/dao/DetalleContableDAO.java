package co.edu.unbosque.model.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


/**
 * The persistent class for the detalle_contable database table.
 * 
 */
@Entity
@Table(name="detalle_contable")
@NamedQuery(name="DetalleContable.findAll", query="SELECT d FROM DetalleContable d")

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleContable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_detalle")
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long idDetalle;

	@Column(name = "debe")
	private BigDecimal debe;

	@Column(name = "haber")
	private BigDecimal haber;

	//bi-directional many-to-one association to AsientoContable
	@ManyToOne
	@JoinColumn(name="id_asiento")
	private AsientoContable asientoContable;

	//bi-directional many-to-one association to CuentaContable
	@ManyToOne
	@JoinColumn(name="id_cuenta")
	private CuentaContable cuentaContable;

}
