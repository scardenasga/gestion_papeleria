package co.edu.unbosque.model.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import co.edu.unbosque.model.HibernateUtil;
import co.edu.unbosque.model.entity.Venta;
import jakarta.persistence.EntityManager;

public class VentaDAO extends GenericDAO<Long, Venta> {

    public VentaDAO() {
        super(Venta.class);
    }

    public CompletableFuture<List<Venta>> findByAtribute(String attributeName, LocalDate fecha) {
        return CompletableFuture.supplyAsync(() -> {
            EntityManager entityManager = HibernateUtil.getInstance().getEntityManager();
            try {
                // Convertir LocalDate a java.sql.Date
                java.sql.Date sqlDate = java.sql.Date.valueOf(fecha);
                String query = "SELECT v FROM Venta v WHERE v.fechaVenta = :fecha";
                return entityManager.createQuery(query, Venta.class)
                                    .setParameter("fecha", sqlDate) // Usar sqlDate aquí
                                    .getResultList();
            } finally {
                entityManager.close();
            }
        });
    }

    public CompletableFuture<List<Venta>> findByDateRange(LocalDate fechaInicio, LocalDate fechaFin) {
        return CompletableFuture.supplyAsync(() -> {
            EntityManager entityManager = HibernateUtil.getInstance().getEntityManager();
            try {
                String query = "SELECT v FROM Venta v WHERE v.fechaVenta BETWEEN :fechaInicio AND :fechaFin";
                return entityManager.createQuery(query, Venta.class)
                                    .setParameter("fechaInicio", java.sql.Date.valueOf(fechaInicio))
                                    .setParameter("fechaFin", java.sql.Date.valueOf(fechaFin))
                                    .getResultList();
            } finally {
                entityManager.close();
            }
        });
    }

    // Método para obtener todas las ventas
    @Override
    public CompletableFuture<List<Venta>> findAll() {
        return CompletableFuture.supplyAsync(() -> {
            EntityManager entityManager = HibernateUtil.getInstance().getEntityManager();
            try {
                return entityManager.createQuery("SELECT v FROM Venta v", Venta.class).getResultList();
            } finally {
                entityManager.close();
            }
        });
    }

    public Object[] executeSingleResultQuery(String sql, Object... params) {
        EntityManager entityManager = HibernateUtil.getInstance().getEntityManager();
        try {
            // Crear la consulta nativa
            var query = entityManager.createNativeQuery(sql);
            
            // Establecer parámetros si los hay
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i + 1, params[i]);
            }
    
            // Ejecutar la consulta y obtener el resultado
            List<Object[]> results = query.getResultList();
            if (!results.isEmpty()) {
                return results.get(0); // Retornar el primer resultado si existe
            }
        } catch (Exception e) {
            e.printStackTrace(); // Manejo de excepciones
        } finally {
            entityManager.close(); // Asegurarse de cerrar el EntityManager
        }
        return null; // Retornar null si no hay resultados
    }
}
