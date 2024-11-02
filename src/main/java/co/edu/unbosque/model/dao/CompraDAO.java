package co.edu.unbosque.model.dao;

import co.edu.unbosque.model.entity.Compra;

public class CompraDAO extends GenericDAO<Long, Compra> {

    public CompraDAO() {
        super(Compra.class);
    }
}
