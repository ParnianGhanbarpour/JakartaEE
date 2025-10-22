package com.secondOrganization.service.impl;

import com.secondOrganization.model.entity.Branch;
import com.secondOrganization.service.BranchService;
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
public class BranchServiceImpl implements BranchService, Serializable {

    @PersistenceContext(unitName = "organization")
    private EntityManager entityManager;

    @Transactional
    @Override
    public void save(Branch branch) throws Exception {
        log.info("Saving branch: {}", branch);
        entityManager.persist(branch);
    }

    @Transactional
    @Override
    public void edit(Branch branch) throws Exception {
        log.info("Editing branch id={}", branch.getId());
        entityManager.merge(branch);
    }

    @Transactional
    @Override
    public void remove(Branch branch) throws Exception {
        log.info("Soft deleting branch id={}", branch.getId());
        Branch b = entityManager.find(Branch.class, branch.getId());
        if (b != null) {
            b.setDeleted(true);
            entityManager.merge(b);
        }
    }

    @Transactional
    @Override
    public void removeById(Integer id) throws Exception {
        Branch b = entityManager.find(Branch.class, id);
        if (b != null) {
            b.setDeleted(true);
            entityManager.merge(b);
        }
    }

    @Transactional
    @Override
    public List<Branch> findAll() throws Exception {
        TypedQuery<Branch> q = entityManager.createQuery("select b from Branch  b where b.deleted=false", Branch.class);
        return q.getResultList();
    }

    @Transactional
    @Override
    public Optional<Branch> findById(Integer id) throws Exception {
        return Optional.ofNullable(entityManager.find(Branch.class, id));
    }

    @Transactional
    @Override
    public List<Branch> findByCity(String city) throws Exception {
        TypedQuery<Branch> q = entityManager.createQuery("select b from Branch b where b.city = :city and b.deleted=false", Branch.class);
        q.setParameter("city", city);
        return q.getResultList();
    }

    @Transactional
    @Override
    public List<Branch> findByManager(String manager) throws Exception {
        TypedQuery<Branch> q = entityManager.createQuery("select b from Branch b where b.manager = :manager and b.deleted=false", Branch.class);
        q.setParameter("manager", manager);
        return q.getResultList();
    }

    @Transactional
    @Override
    public List<Branch> findByOrganizationId(Integer organizationId) throws Exception {
        TypedQuery<Branch> q = entityManager.createQuery("select b from Branch b where b.organization.id = :orgId and b.deleted=false", Branch.class);
        q.setParameter("orgId", organizationId);
        return q.getResultList();
    }
}