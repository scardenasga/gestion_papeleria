package co.edu.unbosque.model.dao;

import co.edu.unbosque.model.entity.DetalleContable;

public class DetalleContableDAO extends GenericDAO<Long, DetalleContable> {
 

    public DetalleContableDAO(){
        super(DetalleContable.class); 
    }
    
}
