package co.edu.unbosque.model.dao;

import co.edu.unbosque.model.entity.Venta;

public class VentaDAO extends GenericDAO<Long, Venta>{
    
    public VentaDAO(){
        super(Venta.class);
    }
}
