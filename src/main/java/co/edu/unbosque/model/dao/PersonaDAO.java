package co.edu.unbosque.model.dao;

import co.edu.unbosque.model.entity.Persona;

public class PersonaDAO extends GenericDAO<String, Persona>{
    
    public PersonaDAO(){
        super(Persona.class);
    }
}
