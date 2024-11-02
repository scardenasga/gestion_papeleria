package co.edu.unbosque;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {

    EntityManagerFactory emf;

    public Main(){
        emf = Persistence.createEntityManagerFactory("Persistencia");

    }
    public static void main(String[] args) {
        Main userService = new Main();

        // Establecer el usuario actual en la base de datos (simula SET myapp.usuario_actual = 'KAREN';)
        userService.setUsuarioActual("KAREN");

        // Mostrar el valor de la variable (simula SHOW myapp.usuario_actual;)
        userService.showUsuarioActual();

    }

    public void setUsuarioActual(String usuarioActual) {
        EntityManager entityManager = emf.createEntityManager();
        
        try {
            // Inicia una transacción
            entityManager.getTransaction().begin();

            // Ejecuta el comando SQL para establecer la variable de configuración
            entityManager.createNativeQuery("SET myapp.usuario_actual = '"+usuarioActual+"'")
                         .executeUpdate();

            // Confirma la transacción
            entityManager.getTransaction().commit();

            System.out.println("El usuario actual ha sido establecido en la base de datos: " + usuarioActual);
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    public void showUsuarioActual() {
        EntityManager entityManager = emf.createEntityManager();
        
        try {
            // Ejecuta el comando SQL para mostrar la variable de configuración
            String usuarioActual = (String) entityManager.createNativeQuery("SHOW myapp.usuario_actual")
                                                         .getSingleResult();
    
            System.out.println("El usuario actual es: " + usuarioActual);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }
}