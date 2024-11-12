package co.edu.unbosque.model.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import co.edu.unbosque.model.HibernateUtil;
import co.edu.unbosque.model.entity.Venta;
import jakarta.persistence.EntityManager;

public class VentaDAO extends GenericDAO<Long, Venta>{
    
    public VentaDAO(){
        super(Venta.class);
    }

    public CompletableFuture<List<Venta>> findByAtribute(String attributeName, LocalDate fecha) {
        return CompletableFuture.supplyAsync(() -> {
            EntityManager entityManager = HibernateUtil.getInstance().getEntityManager();
            try {
                String query = "SELECT v FROM Venta v WHERE v.fechaVenta = :fecha";
                return entityManager.createQuery(query, Venta.class)
                                    .setParameter("fecha", fecha)
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
}
