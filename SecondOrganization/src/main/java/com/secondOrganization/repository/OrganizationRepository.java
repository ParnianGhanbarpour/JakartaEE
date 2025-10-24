package com.secondOrganization.repository;

import com.secondOrganization.model.entity.Organization;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class OrganizationRepository extends BaseRepository<Organization> {

    @PersistenceContext(unitName = "organization")
    private EntityManager entityManager;

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    protected Class<Organization> getEntityClass() {
        return Organization.class;
    }
}
