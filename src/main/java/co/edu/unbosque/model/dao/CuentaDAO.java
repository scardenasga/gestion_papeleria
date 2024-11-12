package co.edu.unbosque.model.dao;

import co.edu.unbosque.model.entity.AsientoContable;

public class CuentaDAO extends GenericDAO<Long,AsientoContable>{

    public CuentaDAO(){
        super(AsientoContable.class);
    }
}
