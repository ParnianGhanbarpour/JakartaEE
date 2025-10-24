package com.secondOrganization.repository;

import com.secondOrganization.model.entity.Department;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class DepartmentRepository extends BaseRepository<Department> {

    @PersistenceContext(unitName = "organization")
    private EntityManager entityManager;

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    protected Class<Department> getEntityClass() {
        return Department.class;
    }
}
