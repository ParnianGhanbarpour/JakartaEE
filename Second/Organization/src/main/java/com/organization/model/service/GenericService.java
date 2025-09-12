package com.example.organization.model.service;

import com.example.organization.model.repository.CrudRepository;

import java.util.List;

public class GenericService<T, I> {

    private final CrudRepository<T, I> repository;

    public GenericService() {
        this.repository = new CrudRepository<>();
    }

    public T save(T entity) throws Exception {
        try (CrudRepository<T, I> repo = new CrudRepository<>()) {
            return repo.save(entity);
        }
    }

    public T edit(T entity, Class<T> tClass) throws Exception {
        try (CrudRepository<T, I> repo = new CrudRepository<>()) {
            if (repo.findById(getId(entity), tClass) != null) {
                return repo.edit(entity);
            } else {
                throw new RuntimeException(tClass.getSimpleName() + " not found!");
            }
        }
    }

    public T deleteById(I id, Class<T> tClass) throws Exception {
        try (CrudRepository<T, I> repo = new CrudRepository<>()) {
            if (repo.findById(id, tClass) != null) {
                return repo.deleteById(id, tClass);
            } else {
                throw new RuntimeException(tClass.getSimpleName() + " not found!");
            }
        }
    }

    public List<T> findAll(Class<T> tClass) throws Exception {
        try (CrudRepository<T, I> repo = new CrudRepository<>()) {
            return repo.findAll(tClass);
        }
    }

    public T findById(I id, Class<T> tClass) throws Exception {
        try (CrudRepository<T, I> repo = new CrudRepository<>()) {
            return repo.findById(id, tClass);
        }
    }

    private I getId(T entity) {
        try {
            return (I) entity.getClass().getMethod("getId").invoke(entity);
        } catch (Exception e) {
            throw new RuntimeException("Cannot get id from entity", e);
        }
    }
}
