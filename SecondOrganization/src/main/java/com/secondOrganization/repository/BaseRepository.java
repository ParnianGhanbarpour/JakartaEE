package com.secondOrganization.repository;

import com.secondOrganization.model.entity.Base;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.stat.Statistics;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public abstract class BaseRepository<T extends Base> {

    protected abstract EntityManager getEntityManager();

    protected abstract Class<T> getEntityClass();

    @Transactional
    public T save(T entity) {
        if (entity.getId() == null) {
            getEntityManager().persist(entity);
        } else {
            entity = getEntityManager().merge(entity);
        }
        return entity;
    }

    public Optional<T> findById(Long id) {
        return Optional.ofNullable(getEntityManager().find(getEntityClass(), id));
    }

    public List<T> findAll(int page, int size) {
        TypedQuery<T> query = getEntityManager().createQuery("SELECT e FROM " + getEntityClass().getSimpleName() + " e", getEntityClass());
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        return query.getResultList();
    }

    @Transactional
    public void softDeleteAt(Long id) {
        findById(id).ifPresent(entity -> {
            entity.setDeletedAt(LocalDateTime.now());
            getEntityManager().merge(entity);
        });
    }

    @Transactional
    public void hardDelete(Long id) {
        findById(id).ifPresent(entity -> getEntityManager().remove(entity));
    }

    public List<T> findAllIncludingDeleted() {
        return getEntityManager().createQuery("SELECT e FROM " + getEntityClass().getSimpleName() + " e", getEntityClass())
                .getResultList();
    }

    public Optional<T> findByIdIncludingDeleted(Long id) {
        Query query = getEntityManager().createQuery("SELECT e FROM " + getEntityClass().getSimpleName() + " e WHERE e.id = :id");
        query.setParameter("id", id);
        return query.getResultList().stream().findFirst().map(e -> (T) e);
    }

    // Cache stats (override در subclasses اگر نیاز)
    protected Statistics getCacheStatistics() {
        return getEntityManager().unwrap(Session.class).getSessionFactory().getStatistics();
    }
}