package co.edu.unbosque.model;

import java.util.concurrent.Future;

import jakarta.persistence.EntityManager;

/**
 * Metodo encargado de establecer una variable en la base de datos
 * que permita conocer quien esta accediendo al sistema esto con el fin de llevar
 * un registro en las auditorias.
 * @author  Sebastian Cardenas Garcia
 */
public class EstablecerSecion {

    /**
     * Metodo encargado de establecer en la base de datos una variables con el id
     * del usuario que esta accediendo al sistema.
     * @param usuarioActual Es el id del usuario que esta accediendo al sistema.
     */
    //  public Future<Void> setUsuarioActual(String usuarioActual) {
    //     return HibernateUtil.getExecutorService().submit(() -> {
    //         EntityManager entityManager = HibernateUtil.getInstance().getEntityManager();
    //         try {
    //             entityManager.getTransaction().begin();
    //             entityManager.createNativeQuery("SET myapp.usuario_actual = '" + usuarioActual + "'")
    //                     .executeUpdate();
    //             entityManager.getTransaction().commit();
                
    //             System.out.println("El usuario actual ha sido establecido en la base de datos: " + usuarioActual);
    //         } catch (Exception e) {
    //             if (entityManager.getTransaction().isActive()) {
    //                 entityManager.getTransaction().rollback();
    //             }
    //             e.printStackTrace();
    //         } finally {
    //             entityManager.close();
    //         }
    //         return null; // Retornar null porque el tipo de Future es Void
    //     });
    // }

    public void setUsuarioActual(String usuarioActual) {


            EntityManager entityManager = HibernateUtil.getInstance().getEntityManager();
            try {
                entityManager.getTransaction().begin();
                entityManager.createNativeQuery("SET myapp.usuario_actual = '"+usuarioActual+"';")
                        .executeUpdate();
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

    /**
     * Funcion encargada de imprimir el usuario que esta en el sistema.
     */
    // public Future<Void> showUsuarioActual() {
    //     return HibernateUtil.getExecutorService().submit(() -> {
    //         EntityManager entityManager = HibernateUtil.getInstance().getEntityManager();
    //         try {
    //             String usuarioActual = (String) entityManager.createNativeQuery("SHOW myapp.usuario_actual")
    //                     .getSingleResult();
    //             System.out.println("El usuario actual es: " + usuarioActual);
    //         } catch (Exception e) {
    //             e.printStackTrace();
    //         } finally {
    //             entityManager.close();
    //         }
    //         return null; // Retornar null porque el tipo de Future es Void
    //     });
    // }

    public void showUsuarioActual() {
            EntityManager entityManager = HibernateUtil.getInstance().getEntityManager();
            try {
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
