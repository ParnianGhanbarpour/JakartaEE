package com.secondOrganization.service.impl;

import com.secondOrganization.model.entity.ProjectAssignment;
import com.secondOrganization.service.ProjectAssignmentService;
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
public class ProjectAssignmentServiceImpl implements ProjectAssignmentService, Serializable {

    @PersistenceContext(unitName = "organization")
    private EntityManager entityManager;

    @Transactional
    @Override
    public void save(ProjectAssignment assignment) throws Exception {
        log.info("Saving project assignment: {}", assignment);
        entityManager.persist(assignment);
    }

    @Transactional
    @Override
    public void edit(ProjectAssignment assignment) throws Exception {
        log.info("Editing project assignment");
        entityManager.merge(assignment);
    }

    @Transactional
    @Override
    public void remove(ProjectAssignment assignment) throws Exception {
        log.info("Removing project assignment");
        // logical delete via Base.deleted
        // fetch managed entity then set deleted
        ProjectAssignment pa = entityManager.find(ProjectAssignment.class, assignment.getId());
        if (pa != null) {
            pa.setDeleted(true);
            entityManager.merge(pa);
        }
    }

    @Transactional
    @Override
    public void removeById(Long id) throws Exception {
        ProjectAssignment pa = entityManager.find(ProjectAssignment.class, id);
        if (pa != null) {
            pa.setDeleted(true);
            entityManager.merge(pa);
        }
    }

    @Transactional
    @Override
    public List<ProjectAssignment> findAll() throws Exception {
        TypedQuery<ProjectAssignment> q = entityManager.createQuery("select pa from projectAssignmentEntity pa where pa.deleted=false", ProjectAssignment.class);
        return q.getResultList();
    }

    @Transactional
    @Override
    public Optional<ProjectAssignment> findById(Long id) throws Exception {
        return Optional.ofNullable(entityManager.find(ProjectAssignment.class, id));
    }

    @Transactional
    @Override
    public List<ProjectAssignment> findByProjectId(Long projectId) throws Exception {
        TypedQuery<ProjectAssignment> q = entityManager.createQuery("select pa from projectAssignmentEntity pa where pa.project.id = :projectId and pa.deleted=false", ProjectAssignment.class);
        q.setParameter("projectId", projectId);
        return q.getResultList();
    }

    @Transactional
    @Override
    public List<ProjectAssignment> findByPersonId(Long personId) throws Exception {
        TypedQuery<ProjectAssignment> q = entityManager.createQuery("select pa from projectAssignmentEntity pa where pa.person.id = :personId and pa.deleted=false", ProjectAssignment.class);
        q.setParameter("personId", personId);
        return q.getResultList();
    }
}