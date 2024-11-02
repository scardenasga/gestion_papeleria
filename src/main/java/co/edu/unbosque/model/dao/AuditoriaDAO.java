package co.edu.unbosque.model.dao;

import co.edu.unbosque.model.entity.Auditoria;

public class AuditoriaDAO extends GenericDAO<Long, Auditoria> {

    public AuditoriaDAO() {
        super(Auditoria.class);
    }
}
