package com.secondOrganization.repository;

import com.secondOrganization.model.entity.Branch;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class BranchRepository extends BaseRepository<Branch> {

    @PersistenceContext(unitName = "organization")
    private EntityManager entityManager;

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    protected Class<Branch> getEntityClass() {
        return Branch.class;
    }
}
