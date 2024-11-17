package co.edu.unbosque;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import co.edu.unbosque.model.EstablecerSecion;
import co.edu.unbosque.model.HibernateUtil;
import co.edu.unbosque.model.dao.CuentaContableDAO;
import co.edu.unbosque.model.dao.PersonaDAO;
import co.edu.unbosque.model.dao.UsuarioDAO;
import co.edu.unbosque.model.entity.Auditoria;
import co.edu.unbosque.model.entity.CuentaContable;
import co.edu.unbosque.model.entity.Usuario;
import co.edu.unbosque.model.entity.Venta;
import co.edu.unbosque.model.service.Contabilidad;

public class Prueba {

    public static void main(String[] args) {
        EstablecerSecion establecerSecion = new EstablecerSecion();

        // Establecer el usuario actual
        // Future<Void> futureSet = establecerSecion.setUsuarioActual("KAREN");
        // // Esperar a que se complete la tarea
        // try {
        //     futureSet.get(); // Bloquea hasta que la tarea se complete
        //     // Mostrar el usuario actual después de establecerlo
        //     establecerSecion.showUsuarioActual().get(); // Esperar a que se complete también
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }

        establecerSecion.setUsuarioActual("ADMIN");
        establecerSecion.showUsuarioActual();

       UsuarioDAO udao = new UsuarioDAO();
       String sql = "SELECT * FROM persona;";
       List<Object[]> usuarios = udao.executeCustomQuery(sql);
       for (Object[] i : usuarios) {
        System.out.println(Arrays.toString(i));
       }
       Object[] aux = udao.executeSingleResultQuery("SELECT id_auditoria FROM auditoria WHERE id_auditoria = 1;");
       if (aux != null) {
        System.out.println(Arrays.toString((Object[]) aux));
    } else {
        System.out.println("No se encontró el resultado.");
    }

    CuentaContableDAO cuenta = new CuentaContableDAO();
    short a = 8;
    CuentaContable n = cuenta.findById(a);
    System.out.println(n);

    // PersonaDAO pdao = new PersonaDAO();

    // Contabilidad con = new Contabilidad();
    // Venta ven = Venta.builder()
    //     .totalVenta(new BigDecimal(12500L))
    //     .persona(pdao.findById("1031632637"))
    //     .fechaVenta(new Timestamp(System.currentTimeMillis()))
    //     .build();
    // con.agregarVenta(ven);
       
        // System.out.println("final de mostrar usuario");
        // UsuarioDAO udao = new UsuarioDAO();
        //
        // System.out.println("----- Buscar usuario por id");
        //
        // udao.findById("ADMIN")
        //         .thenAccept(usuario -> {
        //             if (usuario != null) {
        //                 System.out.println("El usuario existe:");
        //                 System.out.println("El usuario: " + usuario);
        //             } else {
        //                 System.out.println("El usuario no existe");
        //             }
        //         })
        //         .exceptionally(ex -> {
        //             ex.printStackTrace();
        //             return null;
        //         });
        //
        //
        // System.out.println("------- aqui inicia la insercion ------");
        //
        // PersonaDAO pdao = new PersonaDAO();
        // System.out.println("1). primero busca si la persona existe y si existe la muestra");
        //
        // pdao.findById("123456789")
        //         .handle((persona, throwable) -> {
        //             if (throwable != null) {
        //                 System.err.println("Error al buscar la persona: " + throwable.getMessage());
        //                 return null; // O cualquier valor por defecto
        //             }
        //             return persona; // Retorna la persona si no hay excepciones
        //         })
        //         .thenAccept(persona -> {
        //             if (persona != null) {
        //                 System.out.println("La persona existe");
        //                 System.out.println("La persona" + persona);
        //             } else {
        //                 System.out.println("La persona no existe");
        //             }
        //         });
        // 
        //
        // System.out.println("ahora muestra todas las personas del sistema");
        // pdao.findAll()
        //         .thenAccept(personas -> {
        //             System.out.println("Mostrar personas");
        //             personas.forEach(p -> System.out.println(p));
        //
        //         });
        //
        // // pdao.delete("123456789");
        //
        // Persona nuevaPersona = Persona.builder()
        //         .identificacion("123456789")
        //         .primerNombre("Juan")
        //         .segundoNombre("Carlos")
        //         .primerApellido("Pérez")
        //         .segundoApellido("Gómez")
        //         .fechaNacimiento(LocalDate.of(2004, 07, 15))
        //         .telefono("1234567890")
        //         .tipoUsuario(TipoUsuario.CLIENTE) // Asignar el tipo de usuario que corresponda
        //         .build();
        // System.out.println("Guarda una persona en la base de datos");
        // pdao.save(nuevaPersona);
        HibernateUtil.getInstance().closeEntityManagerFactory();
    }
}
