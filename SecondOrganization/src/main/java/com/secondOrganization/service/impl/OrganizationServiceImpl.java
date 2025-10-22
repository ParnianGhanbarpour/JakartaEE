package com.secondOrganization.service.impl;

import com.secondOrganization.model.entity.Organization;
import com.secondOrganization.service.OrganizationService;
import jakarta.enterprise.context.SessionScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Slf4j
@SessionScoped
public class OrganizationServiceImpl implements OrganizationService, Serializable {
    @PersistenceContext(unitName = "organization")
    private EntityManager entityManager;

    @Transactional
    @Override
    public void save(Organization organization) throws Exception {
        log.info("Organization Saved");
        entityManager.persist(organization);
    }

    @Transactional
    @Override
    public void edit(Organization organization) throws Exception {
        entityManager.merge(organization);
    }

    @Transactional
    @Override
    public void remove(Organization organization) throws Exception {
         organization = entityManager.find(Organization.class, organization.getId());
        organization.setDeleted(true);
        entityManager.merge(organization);

    }

    @Transactional
    @Override
    public void removeById(Long id) throws Exception {
        Organization organization = entityManager.find(Organization.class, id);
        organization.setDeleted(true);
        entityManager.merge(organization);
    }

    @Transactional
    @Override
    public List<Organization> findAll() throws Exception {
        TypedQuery<Organization> query = entityManager.createQuery("select oo from organizationEntity oo where oo.deleted=false", Organization.class);
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
        TypedQuery<Organization> query = entityManager.createQuery("select oo from organizationEntity oo where oo.name=:name", Organization.class);
        query.setParameter(name,"name");
        List<Organization> result = query.getResultList();
        return Optional.ofNullable((result.isEmpty()) ? null : result.get(0));
    }
}
