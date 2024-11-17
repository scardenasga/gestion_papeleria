package co.edu.unbosque.model.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the compra database table.
 * 
 */
@Entity
@NamedQuery(name="Compra.findAll", query="SELECT c FROM Compra c")

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Compra implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_compra")
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long idCompra;

	@Column(name="fecha_compra")
	private Timestamp fechaCompra;

	@Column(name="total_compra")
	private BigDecimal totalCompra;

	@Column(name = "estado")
	@Enumerated(EnumType.STRING)
	private TipoEstado estado;

	//bi-directional many-to-one association to AsientoContable
	@ManyToOne
	@JoinColumn(name="id_asiento")
	private AsientoContable asientoContable;

	//bi-directional many-to-one association to Persona
	@ManyToOne
	@JoinColumn(name="id_persona")
	private Persona persona;




}