package co.edu.unbosque.model.service;

import java.util.ArrayList;

import co.edu.unbosque.model.dao.PersonaDAO;
import co.edu.unbosque.model.entity.Persona;

public class PersonaService {

    private PersonaDAO personaDAO;

    public PersonaService(PersonaDAO personaDAO) {
        this.personaDAO = personaDAO;
    }

    // public Persona findById(String identificacion) {

    //     pdao.findById(identificacion)
    //             .handle((persona, throwable) -> {
    //                 if (throwable != null) {
    //                     System.err.println("Error al buscar la persona: " + throwable.getMessage());
    //                     return null; // O cualquier valor por defecto
    //                 }
    //                 return persona; // Retorna la persona si no hay excepciones
    //             })
    //             .thenAccept(persona -> {
    //                 if (persona != null) {
    //                     System.out.println("La persona existe");
    //                     System.out.println("La persona" + persona);
    //                 } else {
    //                     System.out.println("La persona no existe");
    //                 }
    //             });
    // }

    // public ArrayList<Persona> findAll() {
    //     ArrayList<Persona> data = new ArrayList<>();

    //     pdao.findAll()
    //             .thenAccept(personas -> {
    //                 System.out.println("Mostrar personas");
    //                 personas.forEach(p -> {
    //                     System.out.println(p);
    //                     data.add(p);
    //                 });

    //             });
    // }

    public void delete(String identificacion){
       personaDAO.delete(identificacion); 
    }

    public void addPerson(Persona persona){
        personaDAO.save(persona);
    }
        
    

}

