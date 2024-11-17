package co.edu.unbosque.model.service;

import java.util.List;

import co.edu.unbosque.model.dao.ProductoDAO;
import co.edu.unbosque.model.entity.Producto;

public class InventarioService {
    
    private ProductoDAO productoDAO;

    public InventarioService(){
        this.productoDAO = new ProductoDAO();
    }

    public void crearProducto(Producto p){
        productoDAO.save(p);
    }

    public Producto getProducto(String codigo){
        return productoDAO.findById(codigo);
    }

    public void eliminarProducto(String codigo){
        productoDAO.executeCustomUpdate("UPDATE producto\n" + //
                        "SET estado = 'INACTIVO'\n" + //
                        "WHERE id_producto IN ("+codigo+");");

    }

    public void actualizarProducto(String codigo, Producto producto){
        productoDAO.update(codigo, producto);
    }

    public List<Object[]> query(String queryString){
        return productoDAO.executeCustomQuery(queryString);
    }
}
