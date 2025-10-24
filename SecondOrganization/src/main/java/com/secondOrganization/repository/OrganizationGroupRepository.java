package com.secondOrganization.repository;

import com.secondOrganization.model.entity.OrganizationGroup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class OrganizationGroupRepository extends BaseRepository<OrganizationGroup> {

    @PersistenceContext(unitName = "organization")
    private EntityManager entityManager;

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    protected Class<OrganizationGroup> getEntityClass() {
        return OrganizationGroup.class;
    }
}
