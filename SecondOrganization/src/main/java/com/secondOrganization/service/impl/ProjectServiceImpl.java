package com.secondOrganization.service.impl;

import com.secondOrganization.model.entity.Project;
import com.secondOrganization.model.entity.enums.ProjectStatus;
import com.secondOrganization.service.ProjectService;
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
public class ProjectServiceImpl implements ProjectService, Serializable {

    @PersistenceContext(unitName = "organization")
    private EntityManager entityManager;

    @Transactional
    @Override
    public void save(Project project) throws Exception {
        log.info("Saving project: {}", project);
        entityManager.persist(project);
    }

    @Transactional
    @Override
    public void edit(Project project) throws Exception {
        log.info("Editing project id={}", project.getId());
        entityManager.merge(project);
    }

    @Transactional
    @Override
    public void remove(Project project) throws Exception {
        log.info("Soft deleting project id={}", project.getId());
        Project p = entityManager.find(Project.class, project.getId());
        if (p != null) {
            p.setDeleted(true);
            entityManager.merge(p);
        }
    }

    @Transactional
    @Override
    public void removeById(Long id) throws Exception {
        Project p = entityManager.find(Project.class, id);
        if (p != null) {
            p.setDeleted(true);
            entityManager.merge(p);
        }
    }

    @Transactional
    @Override
    public List<Project> findAll() throws Exception {
        TypedQuery<Project> q = entityManager.createQuery("select p from Project p where p.deleted=false", Project.class);
        return q.getResultList();
    }

    @Transactional
    @Override
    public Optional<Project> findById(Long id) throws Exception {
        return Optional.ofNullable(entityManager.find(Project.class, id));
    }

    @Transactional
    @Override
    public List<Project> findByTitle(String title) throws Exception {
        TypedQuery<Project> q = entityManager.createQuery("select p from Project p where p.title = :title and p.deleted=false", Project.class);
        q.setParameter("title", title);
        return q.getResultList();
    }

    @Transactional
    @Override
    public List<Project> findByStatus(ProjectStatus status) throws Exception {
        TypedQuery<Project> q = entityManager.createQuery("select p from Project p where p.status = :status and p.deleted=false", Project.class);
        q.setParameter("status", status);
        return q.getResultList();
    }
}