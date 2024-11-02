package co.edu.unbosque.model.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import co.edu.unbosque.model.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class GenericDAO<ID, T extends Serializable> implements DAO<ID, T> {

    private final Class<T> entityClass;

    public GenericDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public void save(T entity) {

        HibernateUtil.getInstance().executeAsync(() -> {

            EntityManager entityManager = HibernateUtil.getInstance().getEntityManager();

            try {
                entityManager.getTransaction().begin();
                entityManager.persist(entity);
                entityManager.getTransaction().commit();
                System.out.println("la persona se guardo con exito");
            } catch (Exception e) {
                if (entityManager.getTransaction().isActive()) {
                    entityManager.getTransaction().rollback();
                }
                System.out.println("ocurrio un problema con la insercion");
            } finally {
                entityManager.close();
            }

        });
    }

    @Override
    public void update(ID id, T entity) {

        HibernateUtil.getInstance().executeAsync(() -> {

            EntityManager entityManager = HibernateUtil.getInstance().getEntityManager();

            try {
                entityManager.getTransaction().begin();
                entityManager.merge(entity);
                entityManager.getTransaction().commit();
            } catch (Exception e) {
                if (entityManager.getTransaction().isActive()) {
                    entityManager.getTransaction().rollback();
                }
                System.out.println("ocurrio un problema con la actualizacion");
            } finally {
                entityManager.close();
            }

        });
    }

    @Override
    public void delete(ID id) {

        HibernateUtil.getInstance().executeAsync(() -> {

            EntityManager entityManager = HibernateUtil.getInstance().getEntityManager();

            try {
                entityManager.getTransaction().begin();

                T entity = entityManager.find(entityClass, id);
                if (entity != null) {
                    entityManager.remove(entity); // Eliminar la entidad encontrada
                } else {
                    System.out.println("La entidad con ID " + id + " no existe.");
                }

                entityManager.getTransaction().commit();
            } catch (Exception e) {
                if (entityManager.getTransaction().isActive()) {
                    entityManager.getTransaction().rollback();
                }
                System.out.println("ocurrio un problema con la eliminacion");
            } finally {
                entityManager.close();
            }

        });
    }

    @Override
    public CompletableFuture<T> findById(ID id) {

        return CompletableFuture.supplyAsync(() -> {

            EntityManager entityManager = HibernateUtil.getInstance().getEntityManager();

            try {

                return entityManager.find(entityClass, id);
            } finally {
                entityManager.close();
            }

        }, HibernateUtil.getExecutorService());
    }

    @Override
    public CompletableFuture<List<T>> findAll() {
        return CompletableFuture.supplyAsync(() -> {
            EntityManager entityManager = HibernateUtil.getInstance().getEntityManager();

            try {
                CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
                CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
                Root<T> root = criteriaQuery.from(entityClass);
                criteriaQuery.select(root);

                return entityManager.createQuery(criteriaQuery).getResultList();
            }catch(Exception e){
                System.out.println("ocurrio un problema al tratar de obtener todos los usuarios");
            } 
            finally {
                entityManager.close();
            }
            return null;

        }, HibernateUtil.getExecutorService());
    }

    @Override
    public CompletableFuture<List<T>> findByAtribute(String atributo, Object dato) {

        return CompletableFuture.supplyAsync(() -> {

            EntityManager entityManager = HibernateUtil.getInstance().getEntityManager();

            try {
                CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
                CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
                Root<T> root = criteriaQuery.from(entityClass);

                Predicate predicate = criteriaBuilder.equal(root.get(atributo), dato);
                criteriaQuery.select(root).where(predicate);

                return entityManager.createQuery(criteriaQuery).getResultList();
            } finally {
                entityManager.close();
            }

        }, HibernateUtil.getExecutorService());
    }

    @Override
    public CompletableFuture<List<T>> findByAtributes(Map<String, Object> atributos) {

        return CompletableFuture.supplyAsync(() -> {

            EntityManager entityManager = HibernateUtil.getInstance().getEntityManager();

            try {
                CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
                CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
                Root<T> root = criteriaQuery.from(entityClass);

                // Construir la lista de predicados (condiciones)
                Predicate[] predicates = atributos.entrySet().stream()
                        .map(entry -> criteriaBuilder.equal(root.get(entry.getKey()), entry.getValue()))
                        .toArray(Predicate[]::new);

                // Aplicar las condiciones
                criteriaQuery.select(root).where(predicates);

                return entityManager.createQuery(criteriaQuery).getResultList();
            } finally {
                entityManager.close();
            }

        }, HibernateUtil.getExecutorService());
    }

}
