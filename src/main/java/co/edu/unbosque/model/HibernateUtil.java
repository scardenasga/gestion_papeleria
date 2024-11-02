package co.edu.unbosque.model;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Clase encargada de abrir y cerrar una conexion con la base de datos.
 * @author  Sebastian Cardenas Garcia
 */
public class HibernateUtil {

    /**
     * Instancia de Hibernate util para menejar una sola instancia de esta clase.
     */
    private static HibernateUtil instance;

    /**
     *Interfaz EntityManagerFactory que nos va a permitir establecer la configuracion de nuestra conexion
     * con la base de datos. 
     */
    private static EntityManagerFactory entityManagerFactory;

    /**
     * Objeto ExecutorService que nos va a permitir realizar cualquier accion con la base de datos
     * con un hilo aparte al hilo principal.
     */
    private static final ExecutorService executorService = Executors.newCachedThreadPool();

    /**
     * Constructor privado que accegura que solo halla una instancia de esta clase durante
     * la ejecución del programa.
     */
    private HibernateUtil() {
        buildEntityManagerFactory();
    }

    /**
     * Metodo que se encarga de crear una instancia del objeto HibernateUtil en el caso
     * de que no exista por el contrario devulve la instancia existente
     * @return Una instancia de HibernateUtil
     */
    public static synchronized HibernateUtil getInstance() {
        if (instance == null) {
            instance = new HibernateUtil();
        }
        return instance;
    }

    /**
     * Metodo encargado de realizar la configuracion al atributo entityManagerFactory.
     */
    private void buildEntityManagerFactory() {
        if (entityManagerFactory == null) {
            entityManagerFactory = Persistence.createEntityManagerFactory("Persistencia");
        }
    }

    /**
     * Metodo que se encarga de retornar un EntityManager a partir de la interfaz
     * EntityManagerFactory.
     * @return  Una instancia de un EntityManager.
     */
    public EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    /**
     * Metodo que se encarga de ejecutar las tareas en un hilo aparte relacionados 
     * con la conexion a la base de datos.
     * @param task La tarea que se pretende realizar con la base de datos.
     */
    public void executeAsync(Runnable task) {
        executorService.submit(task);
    }

    /**
     * Metodo que se encarga de cerrar la secion con la interface EntityManagerFactory.
     */
    public void closeEntityManagerFactory() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            executorService.shutdown();
            try {
                // Esperar hasta que todas las tareas terminen de ejecutarse
                if (!executorService.awaitTermination(60, java.util.concurrent.TimeUnit.SECONDS)) {
                    executorService.shutdownNow();
                }
            } catch (InterruptedException e) {
                executorService.shutdownNow();
            }
    
            entityManagerFactory.close();
        }
    }

    public static ExecutorService getExecutorService() {
        return executorService;
    }

}
