package co.edu.unbosque.model.dao;

import co.edu.unbosque.model.entity.Producto;

public class ProductoDAO extends GenericDAO<String, Producto> {

    public ProductoDAO() {
        super(Producto.class);
    }
}
