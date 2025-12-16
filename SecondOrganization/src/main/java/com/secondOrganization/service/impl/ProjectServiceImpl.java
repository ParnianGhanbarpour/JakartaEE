package com.secondOrganization.service.impl;

import com.secondOrganization.model.entity.Person;
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
import com.secondOrganization.model.entity.ProjectAssignment;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@ApplicationScoped
@Transactional
public class ProjectServiceImpl implements ProjectService, Serializable {

    @PersistenceContext(unitName = "organization")
    private EntityManager entityManager;

    @Override
    public void save(Project project) throws Exception {
        log.info("Saving project: {}", project);

        if (project.getBudget() == null) {
            throw new IllegalArgumentException("بودجه نمی‌تواند null باشد");
        }

        if (project.getBudget().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("بودجه باید بیشتر از صفر باشد");
        }
        entityManager.persist(project);
    }


    @Override
    public void edit(Project project) throws Exception {
        log.info("Editing project id={}", project.getId());
        entityManager.merge(project);
    }

    @Override
    public void remove(Project project) throws Exception {
        log.info("Soft deleting project id={}", project.getId());
        Project p = entityManager.find(Project.class, project.getId());
        if (p != null) {
            p.setDeleted(true);
            entityManager.merge(p);
        }
    }

    @Override
    public void removeById(Long id) throws Exception {
        Project p = entityManager.find(Project.class, id);
        if (p != null) {
            p.setDeleted(true);
            entityManager.merge(p);
        }
    }

    @Override
    public List<Project> findAll() throws Exception {
        log.info("Fetching all projects");

        try {
            TypedQuery<Project> projectQuery = entityManager.createQuery(
                    "select p from Project p where p.deleted = false",
                    Project.class
            );

            List<Project> projects = projectQuery.getResultList();

            if (projects.isEmpty()) {
                return projects;
            }

            List<Long> projectIds = projects.stream()
                    .map(Project::getId)
                    .collect(Collectors.toList());

            TypedQuery<Object[]> personsQuery = entityManager.createQuery(
                    "select p.id, person from Project p " +
                            "left join p.persons person " +
                            "where p.id in :projectIds",
                    Object[].class
            );
            personsQuery.setParameter("projectIds", projectIds);

            List<Object[]> personsResults = personsQuery.getResultList();

            Map<Long, List<Person>> personsMap = new HashMap<>();
            for (Object[] result : personsResults) {
                Long projectId = (Long) result[0];
                Person person = (Person) result[1];

                personsMap.computeIfAbsent(projectId, k -> new ArrayList<>())
                        .add(person);
            }

            TypedQuery<Object[]> assignmentsQuery = entityManager.createQuery(
                    "select p.id, assignment from Project p " +
                            "left join p.assignments assignment " +
                            "where p.id in :projectIds",
                    Object[].class
            );
            assignmentsQuery.setParameter("projectIds", projectIds);

            List<Object[]> assignmentsResults = assignmentsQuery.getResultList();

            Map<Long, Set<ProjectAssignment>> assignmentsMap = new HashMap<>(); // Set استفاده از
            for (Object[] result : assignmentsResults) {
                Long projectId = (Long) result[0];
                ProjectAssignment assignment = (ProjectAssignment) result[1]; // ProjectAssignment استفاده از

                assignmentsMap.computeIfAbsent(projectId, k -> new HashSet<>())
                        .add(assignment);
            }

            for (Project project : projects) {
                Long id = project.getId();
                project.setPersons((Set<Person>) personsMap.getOrDefault(id, new ArrayList<>()));
                project.setAssignments(assignmentsMap.getOrDefault(id, new HashSet<>())); // Set استفاده از
            }

            log.info("Successfully loaded {} projects with their associations", projects.size());
            return projects;

        } catch (Exception e) {
            log.error("Error fetching projects: {}", e.getMessage(), e);
            throw new RuntimeException("خطا در دریافت پروژه‌ها", e);
        }
    }


    @Override
    public Optional<Project> findById(Long id) throws Exception {
        return Optional.ofNullable(entityManager.find(Project.class, id));
    }

    @Override
    public List<Project> findByTitle(String title) throws Exception {
        TypedQuery<Project> q = entityManager.createQuery("select p from Project p where p.title = :title and p.deleted=false", Project.class);
        q.setParameter("title", title);
        return q.getResultList();
    }

    @Override
    public List<Project> findByStatus(ProjectStatus status) throws Exception {
        TypedQuery<Project> q = entityManager.createQuery("select p from Project p where p.status = :status and p.deleted=false", Project.class);
        q.setParameter("status", status);
        return q.getResultList();
    }
}