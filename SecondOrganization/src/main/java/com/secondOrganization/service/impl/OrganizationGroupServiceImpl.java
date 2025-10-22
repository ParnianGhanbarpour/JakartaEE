package com.secondOrganization.service.impl;

import com.secondOrganization.model.entity.OrganizationGroup;
import com.secondOrganization.service.OrganizationGroupService;
import jakarta.enterprise.context.ApplicationScoped;
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
@ApplicationScoped
public class OrganizationGroupServiceImpl implements OrganizationGroupService, Serializable {

    @PersistenceContext(unitName = "organization")
    private EntityManager entityManager;

    @Transactional
    @Override
    public void save(OrganizationGroup group) throws Exception {
        log.info("Saving group: {}", group);
        entityManager.persist(group);
    }

    @Transactional
    @Override
    public void edit(OrganizationGroup group) throws Exception {
        log.info("Editing group id={}", group.getId());
        entityManager.merge(group);
    }

    @Transactional
    @Override
    public void remove(OrganizationGroup group) throws Exception {
        log.info("Removing group id={}", group.getId());
        OrganizationGroup g = entityManager.find(OrganizationGroup.class, group.getId());
        if (g != null) {
            g.setDeleted(true);
            entityManager.merge(g);
        }
    }

    @Transactional
    @Override
    public void removeById(Long id) throws Exception {
        OrganizationGroup g = entityManager.find(OrganizationGroup.class, id);
        if (g != null) {
            g.setDeleted(true);
            entityManager.merge(g);
        }
    }

    @Transactional
    @Override
    public List<OrganizationGroup> findAll() throws Exception {
        TypedQuery<OrganizationGroup> q = entityManager.createQuery("select g from OrganizationGroup g where g.deleted=false", OrganizationGroup.class);
        return q.getResultList();
    }

    @Transactional
    @Override
    public Optional<OrganizationGroup> findById(Long id) throws Exception {
        return Optional.ofNullable(entityManager.find(OrganizationGroup.class, id));
    }

    @Transactional
    @Override
    public List<OrganizationGroup> findByDepartmentId(Long departmentId) throws Exception {
        TypedQuery<OrganizationGroup> q = entityManager.createQuery("select g from OrganizationGroup g where g.department.id = :deptId and g.deleted=false", OrganizationGroup.class);
        q.setParameter("deptId", departmentId);
        return q.getResultList();
    }

    @Transactional
    @Override
    public List<OrganizationGroup> findByName(String name) throws Exception {
        TypedQuery<OrganizationGroup> q = entityManager.createQuery("select g from OrganizationGroup g where g.name = :name and g.deleted=false", OrganizationGroup.class);
        q.setParameter("name", name);
        return q.getResultList();
    }
}