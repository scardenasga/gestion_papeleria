package co.edu.unbosque.model.dao;

import co.edu.unbosque.model.entity.Usuario;

public class UsuarioDAO extends GenericDAO<String, Usuario> {

    public UsuarioDAO() {
        super(Usuario.class);
    }
}
