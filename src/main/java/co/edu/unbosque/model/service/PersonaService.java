package co.edu.unbosque.model.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.model.dao.PersonaDAO;
import co.edu.unbosque.model.dao.UsuarioDAO;
import co.edu.unbosque.model.entity.Persona;
import co.edu.unbosque.model.entity.TipoUsuario;
import co.edu.unbosque.model.entity.Usuario;

public class PersonaService {

    private PersonaDAO personaDAO;
    private UsuarioDAO usuarioDAO;

    public PersonaService() {
        this.personaDAO = new PersonaDAO();
        this.usuarioDAO = new UsuarioDAO();
    }

    public void crearPersona(Persona nuevaPersona) {
        personaDAO.save(nuevaPersona);
    }

    public void crearUsuario(Persona nuevaPersona, Usuario nuevoUsuario){
        nuevoUsuario.setPersona(nuevaPersona);

        crearPersona(nuevaPersona);
        usuarioDAO.save(nuevoUsuario);

    }
    public Persona getPersona(String id){
        return personaDAO.findById(id);
    }
    public Usuario getUsuario(String id){
        Object[] user = usuarioDAO.executeSingleResultQuery("SELECT * FROM usuario WHERE id_persona = '"+id+"';");
        String username = (String)user[0];
        return usuarioDAO.findById(username);
    }
    public void eliminarPersona(String identificaciones){
        personaDAO.executeCustomUpdate("UPDATE persona\n" + //
                        "SET estado = 'INACTIVO'\n" + //
                        "WHERE identificacion IN ("+identificaciones+");");
        usuarioDAO.executeCustomUpdate("UPDATE usuario\n" + //
                        "SET estado = 'INACTIVO'\n" + //
                        "WHERE id_persona IN ("+identificaciones+");");

    }

    public void actualizarPersona(String id, Persona actualizarPersona){
        personaDAO.update(id, actualizarPersona);

    }

    public void actualizarUsuario(String id, Usuario actualizarUsuario){
        usuarioDAO.update(id, actualizarUsuario);
    }
    
    public List<Object[]> query(String queryString){
        return personaDAO.executeCustomQuery(queryString);
    }
}