package co.edu.unbosque.model.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.ArrayList;


/**
 * The persistent class for the cuenta_contable database table.
 * 
 */
@Entity
@Table(name="cuenta_contable")
@NamedQuery(name="CuentaContable.findAll", query="SELECT c FROM CuentaContable c")

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CuentaContable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_cuenta", columnDefinition = "smallserial")
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Short idCuenta;

	@Column(name = "nombre")
	@Enumerated(EnumType.STRING)
	private NombreCuenta nombre;

	@Column(name = "tipo")
	@Enumerated(EnumType.STRING)
	private TipoCuenta tipo;

	@Column(name = "estado")
	@Enumerated(EnumType.STRING)
	private TipoEstado estado;

	//bi-directional many-to-one association to DetalleContable
	@OneToMany( fetch = FetchType.EAGER, mappedBy="cuentaContable")
	@Builder.Default
	@ToString.Exclude
	private List<DetalleContable> detalleContables = new ArrayList<>();

}