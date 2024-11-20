package co.edu.unbosque.model.dao;

import co.edu.unbosque.model.entity.DetalleVenta;

public class DetalleVentaDAO extends GenericDAO<String, DetalleVenta> { ////////////////////////////////////

    public DetalleVentaDAO() {
        super(DetalleVenta.class);
    }
}
