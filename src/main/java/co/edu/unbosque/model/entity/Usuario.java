package co.edu.unbosque.model.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.ArrayList;


/**
 * The persistent class for the usuario database table.
 * 
 */
@Entity
@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u")

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "username")
	private String username;

	@Column(name = "contrasena")
	private String contrasena;

	@Column(name = "estado")
	@Enumerated(EnumType.STRING)
	private TipoEstado estado;

	//bi-directional many-to-one association to Auditoria
	@OneToMany(mappedBy="usuario")
	@Builder.Default
	private List<Auditoria> auditorias = new ArrayList<>();

	//bi-directional many-to-one association to Persona
	@ManyToOne
	@JoinColumn(name="id_persona")
	private Persona persona;

	@Override
    public String toString() {
        return "Usuario{" +
                "username='" + username + '\'' +
				"contraseña=" + contrasena + "\'"+
                ", estado=" + estado +
                // Excluye auditorias para evitar el LazyInitializationException
                '}';
    }


}