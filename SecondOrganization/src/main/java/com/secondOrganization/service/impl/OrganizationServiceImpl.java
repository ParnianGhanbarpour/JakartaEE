package com.secondOrganization.service.impl;

import com.secondOrganization.model.entity.Organization;
import com.secondOrganization.service.OrganizationService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Slf4j
@ApplicationScoped
public class OrganizationServiceImpl implements OrganizationService, Serializable {

    @PersistenceContext(unitName = "organization")
    private EntityManager entityManager;

    @Transactional
    @Override
    public void save(Organization organization) throws Exception {
        log.info("Organization Saved: {}", organization.getName());
        entityManager.persist(organization);
    }

    @Transactional
    @Override
    public void edit(Organization organization) throws Exception {
        log.info("Organization Edited: {}", organization.getId());
        entityManager.merge(organization);
    }

    @Transactional
    @Override
    public void remove(Organization organization) throws Exception {
        organization = entityManager.find(Organization.class, organization.getId());
        if (organization != null) {
            organization.setDeleted(true);
            entityManager.merge(organization);
            log.info("Organization soft deleted: {}", organization.getId());
        }
    }

    @Transactional
    @Override
    public void removeById(Long id) throws Exception {
        Organization organization = entityManager.find(Organization.class, id);
        if (organization != null) {
            organization.setDeleted(true);
            entityManager.merge(organization);
            log.info("Organization soft deleted by ID: {}", id);
        }
    }

    @Transactional
    @Override
    public List<Organization> findAll() throws Exception {
        TypedQuery<Organization> query = entityManager.createQuery(
                "select o from Organization o where o.deleted=false", Organization.class);
        return query.getResultList();
    }

    @Transactional
    @Override
    public Optional<Organization> findById(Long id) throws Exception {
        return Optional.ofNullable(entityManager.find(Organization.class, id));
    }

    @Transactional
    @Override
    public Optional<Organization> findByName(String name) throws Exception {
        TypedQuery<Organization> query = entityManager.createQuery(
                "select o from Organization o where o.name = :name and o.deleted=false", Organization.class);
        query.setParameter("name", name);
        List<Organization> result = query.getResultList();
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }
}