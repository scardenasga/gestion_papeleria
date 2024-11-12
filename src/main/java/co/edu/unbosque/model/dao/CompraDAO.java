package co.edu.unbosque.model.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import co.edu.unbosque.model.HibernateUtil;
import co.edu.unbosque.model.entity.Compra;
import jakarta.persistence.EntityManager;

public class CompraDAO extends GenericDAO<Long, Compra> {

    public CompraDAO() {
        super(Compra.class);
    }

    public CompletableFuture<List<Compra>> findByAtribute(String attributeName, LocalDate fecha) {
        return CompletableFuture.supplyAsync(() -> {
            EntityManager entityManager = HibernateUtil.getInstance().getEntityManager();
            try {
                String query = "SELECT c FROM Compra c WHERE c.fechaCompra = :fecha";
                return entityManager.createQuery(query, Compra.class)
                                    .setParameter("fecha", fecha)
                                    .getResultList();
            } finally {
                entityManager.close();
            }
        });
    }
}
