package com.secondOrganization.service.impl;

import com.secondOrganization.model.entity.Branch;
import com.secondOrganization.service.BranchService;
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
public class BranchServiceImpl implements BranchService, Serializable {

    @PersistenceContext(unitName = "organization")
    private EntityManager entityManager;

    @Transactional
    @Override
    public void save(Branch branch) throws Exception {
        log.info("Saving branch: {}", branch);
        if (branch.getOrganization() != null && branch.getOrganization().getId() == null) {
            throw new IllegalArgumentException("Organization must be persisted first");
        }
        entityManager.persist(branch);
        log.info("Branch saved successfully with ID: {}", branch.getId());
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
    public void removeById(Long id) throws Exception {
        Branch b = entityManager.find(Branch.class, id);
        if (b != null) {
            b.setDeleted(true);
            entityManager.merge(b);
        }
    }

    @Override
    public List<Branch> findAll() throws Exception {
        log.info("Fetching all branches with organization");

        TypedQuery<Branch> q = entityManager.createQuery(
                "SELECT b FROM Branch b " +
                        "LEFT JOIN FETCH b.organization " +
                        "WHERE b.deleted = false " +
                        "ORDER BY b.organization.name, b.name", Branch.class);

        List<Branch> branches = q.getResultList();
        log.info("Found {} branches", branches.size());

        for (Branch branch : branches) {
            log.info("Branch: {}, Organization: {}",
                    branch.getName(),
                    branch.getOrganization() != null ? branch.getOrganization().getName() : "null");
        }
        return branches;
    }

    @Override
    public Optional<Branch> findById(Long id) throws Exception {
        log.info("Finding branch by ID: {}", id);

        TypedQuery<Branch> q = entityManager.createQuery(
                "SELECT b FROM Branch b " +
                        "LEFT JOIN FETCH b.organization " +
                        "WHERE b.id = :id AND b.deleted = false", Branch.class);
        q.setParameter("id", id);
        try {
            Branch branch = q.getSingleResult();
            return Optional.of(branch);
        } catch (Exception e) {
            log.warn("Branch not found with ID: {}", id);
            return Optional.empty();
        }
    }

    @Transactional
    @Override
    public List<Branch> findByCity(String city) throws Exception {
        TypedQuery<Branch> q = entityManager.createQuery(
                "SELECT b FROM Branch b " +
                        "LEFT JOIN FETCH b.organization " +
                        "WHERE b.city = :city AND b.deleted = false", Branch.class);
        q.setParameter("city", city);
        return q.getResultList();
    }

    @Transactional
    @Override
    public List<Branch> findByManager(String manager) throws Exception {
        TypedQuery<Branch> q = entityManager.createQuery(
                "SELECT b FROM Branch b " +
                        "LEFT JOIN FETCH b.organization " +
                        "WHERE b.manager = :manager AND b.deleted = false", Branch.class);
        q.setParameter("manager", manager);
        return q.getResultList();
    }


    @Transactional
    @Override
    public List<Branch> findByOrganizationId(Long organizationId) throws Exception {
        log.info("Finding branches by organization ID: {}", organizationId);

        TypedQuery<Branch> q = entityManager.createQuery(
                "SELECT b FROM Branch b " +
                        "LEFT JOIN FETCH b.organization " +
                        "WHERE b.organization.id = :orgId AND b.deleted = false", Branch.class);
        q.setParameter("orgId", organizationId);

        List<Branch> branches = q.getResultList();
        log.info("Found {} branches for organization ID: {}", branches.size(), organizationId);

        return branches;
    }
}