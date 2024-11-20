package co.edu.unbosque.model.dao;

import co.edu.unbosque.model.HibernateUtil;
import co.edu.unbosque.model.entity.Compra;
import co.edu.unbosque.view.util.connection.DataSourceSingleton;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CompraDAO extends GenericDAO<Long, Compra> {

    public CompraDAO() {
        super(Compra.class);
    }
    
    public CompletableFuture<List<Compra>> findByAtribute(String attributeName, LocalDate fecha) {
        return CompletableFuture.supplyAsync(() -> {
            EntityManager entityManager = HibernateUtil.getInstance().getEntityManager();
            try {
                // Convertir LocalDate a java.sql.Date
                java.sql.Date sqlDate = java.sql.Date.valueOf(fecha);
                String query = "SELECT c FROM Compra c WHERE c.fechaCompra = :fecha";
                return entityManager.createQuery(query, Compra.class)
                                    .setParameter("fecha", sqlDate)
                                    .getResultList();
            } finally {
                entityManager.close();
            }
        });
    }

    public Object[] executeSingleResultQuery(String sql, Object... params) {
        try (Connection connection = DataSourceSingleton.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // Establecer parámetros si los hay
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                // Suponiendo que solo esperamos un único resultado
                int columnCount = resultSet.getMetaData().getColumnCount();
                Object[] results = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    results[i] = resultSet.getObject(i + 1);
                }
                return results;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Manejo de excepciones
        }
        return null; // Retornar null si no hay resultados
    }
}
