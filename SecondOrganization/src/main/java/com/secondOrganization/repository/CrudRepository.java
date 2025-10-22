package com.secondOrganization.repository;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;

@Stateless
public class CrudRepository<T, I> {

    @PersistenceContext(unitName = "organization")
    private EntityManager entityManager;

    public T save(T t) {
        entityManager.persist(t);
        return t;
    }

    public T edit(T t) {
        return entityManager.merge(t);
    }

    public T deleteById(I id, Class<T> tClass) {
        T t = entityManager.find(tClass, id);
        if (t != null) entityManager.remove(t);
        return t;
    }

    public List<T> findAll(Class<T> tClass) {
        String entityName = tClass.isAnnotationPresent(jakarta.persistence.Entity.class) &&
                !tClass.getAnnotation(jakarta.persistence.Entity.class).name().isEmpty()
                ? tClass.getAnnotation(jakarta.persistence.Entity.class).name()
                : tClass.getSimpleName();
        TypedQuery<T> query = entityManager.createQuery("select e from " + entityName + " e", tClass);
        return query.getResultList();
    }

    public T findById(I id, Class<T> tClass) {
        return entityManager.find(tClass, id);
    }
}
