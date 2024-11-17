package co.edu.unbosque.model.entity;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


import java.util.ArrayList;


/**
 * The persistent class for the persona database table.
 * 
 */
@Entity
@NamedQuery(name="Persona.findAll", query="SELECT p FROM Persona p")

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Persona implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "identificacion")
	private String identificacion;

	@Column(name="fecha_nacimiento")
	private LocalDate fechaNacimiento;

	@Column(name="primer_apellido")
	private String primerApellido;

	@Column(name="primer_nombre")
	private String primerNombre;

	@Column(name="segundo_apellido")
	private String segundoApellido;

	@Column(name="segundo_nombre")
	private String segundoNombre;

	@Column(name = "telefono")
	private String telefono;

	@Column(name="tipo_usuario")
	@Enumerated(EnumType.STRING)
	private TipoUsuario tipoUsuario;

	@Column(name = "estado")
	@Enumerated(EnumType.STRING)
	private TipoEstado estado;

	//bi-directional many-to-one association to Compra
	@OneToMany(mappedBy="persona")
	@Builder.Default
	private List<Compra> compras = new ArrayList<>();

	//bi-directional many-to-one association to Usuario
	@OneToMany(mappedBy="persona")
	@Builder.Default
	private List<Usuario> usuarios = new ArrayList<>();

	//bi-directional many-to-one association to Venta
	@OneToMany(mappedBy="persona")
	@Builder.Default
	private List<Venta> ventas = new ArrayList<>();

	@Override
	public String toString() {
		return "Persona [identificacion=" + identificacion + ", fechaNacimiento=" + fechaNacimiento
				+ ", primerApellido=" + primerApellido + ", primerNombre=" + primerNombre + ", segundoApellido="
				+ segundoApellido + ", segundoNombre=" + segundoNombre + ", telefono=" + telefono + ", tipoUsuario="
				+ tipoUsuario + "]";
	}

	
}