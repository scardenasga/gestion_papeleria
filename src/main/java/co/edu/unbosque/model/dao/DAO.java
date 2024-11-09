package co.edu.unbosque.model.dao;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CompletableFuture;
/**
 * 
 */
public interface DAO<ID, T extends Serializable> {

    void save(T entity);

    void update(ID id, T entity);

    void delete(ID id);

    T findById(ID id);

    CompletableFuture<List<T>> findAll();
    
    List<Object[]> executeCustomQuery(String sql);

    Object[] executeSingleResultQuery(String sql);
}
