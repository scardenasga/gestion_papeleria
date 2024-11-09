package co.edu.unbosque.model.dao;

import co.edu.unbosque.model.entity.CuentaContable;

public class CuentaContableDAO extends GenericDAO<Short, CuentaContable> {
    
    public CuentaContableDAO(){
        super(CuentaContable.class);
    }
}
