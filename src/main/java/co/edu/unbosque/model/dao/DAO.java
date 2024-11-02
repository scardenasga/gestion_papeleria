package co.edu.unbosque.model.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
/**
 * 
 */
public interface DAO<ID, T extends Serializable> {

    void save(T entity);

    void update(ID id, T entity);

    void delete(ID id);

    CompletableFuture<T> findById(ID id);

    CompletableFuture<List<T>> findAll();
    
    CompletableFuture<List<T>> findByAtribute(String atributo, Object dato);

    CompletableFuture<List<T>> findByAtributes(Map<String, Object> atributos);
}
