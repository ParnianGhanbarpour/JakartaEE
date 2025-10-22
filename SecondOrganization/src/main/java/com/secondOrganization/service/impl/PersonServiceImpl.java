package com.secondOrganization.service.impl;

import com.secondOrganization.model.entity.Person;
import com.secondOrganization.service.PersonService;
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
public class PersonServiceImpl implements PersonService, Serializable {

    @PersistenceContext(unitName = "organization")
    private EntityManager entityManager;

    @Transactional
    @Override
    public void save(Person person) throws Exception {
        log.info("Saving person: {}", person);
        entityManager.persist(person);
        log.info("Person saved successfully");
    }

    @Transactional
    @Override
    public void edit(Person person) throws Exception {
        log.info("Editing person with ID: {}", person.getId());
        entityManager.merge(person);
        log.info("Person updated successfully");
    }

    @Transactional
    @Override
    public void remove(Person person) throws Exception {
        log.info("Removing person (logical delete) with ID: {}", person.getId());
        person = entityManager.find(Person.class, person.getId());
        if (person != null) {
            person.setDeleted(true);
            entityManager.merge(person);
            log.info("Person marked as deleted");
        } else {
            log.warn("Person not found for deletion: {}", person.getId());
        }
    }

    @Transactional
    @Override
    public void removeById(Long id) throws Exception {
        log.info("Removing person (logical delete) by ID: {}", id);
        Person person = entityManager.find(Person.class, id);
        if (person != null) {
            person.setDeleted(true);
            entityManager.merge(person);
            log.info("Person marked as deleted");
        } else {
            log.warn("Person not found for deletion by ID: {}", id);
        }
    }

    @Transactional
    @Override
    public List<Person> findAll() throws Exception {
        log.info("Fetching all persons");
        TypedQuery<Person> query =
                entityManager.createQuery("select p from Person p where p.deleted=false", Person.class);
        return query.getResultList();
    }

    @Transactional
    @Override
    public Optional<Person> findById(Long id) throws Exception {
        log.info("Finding person by ID: {}", id);
        return Optional.ofNullable(entityManager.find(Person.class, id));
    }

    @Transactional
    @Override
    public Optional<Person> findByUsername(String username) throws Exception {
        log.info("Finding person by username: {}", username);
        TypedQuery<Person> query =
                entityManager.createQuery("select p from Person p where p.user.username=:username and p.deleted=false", Person.class);
        query.setParameter("username", username);
        return query.getResultList().stream().findFirst();
    }

    @Transactional
    @Override
    public List<Person> findByName(String name) throws Exception {
        log.info("Finding persons by name: {}", name);
        TypedQuery<Person> query =
                entityManager.createQuery("select p from Person p where p.name=:name and p.deleted=false", Person.class);
        query.setParameter("name", name);
        return query.getResultList();
    }

    @Transactional
    @Override
    public List<Person> findByFamily(String family) throws Exception {
        log.info("Finding persons by family: {}", family);
        TypedQuery<Person> query =
                entityManager.createQuery("select p from Person p where p.family=:family and p.deleted=false", Person.class);
        query.setParameter("family", family);
        return query.getResultList();
    }

    @Transactional
    @Override
    public List<Person> findByNameAndFamily(String name, String family) throws Exception {
        log.info("Finding persons by name={} and family={}", name, family);
        TypedQuery<Person> query =
                entityManager.createQuery("select p from Person  p where p.name=:name and p.family=:family and p.deleted=false", Person.class);
        query.setParameter("name", name);
        query.setParameter("family", family);
        return query.getResultList();
    }

    @Transactional
    @Override
    public Optional<Person> findByNationalCode(String code) throws Exception {
        log.info("Finding person by national code: {}", code);
        TypedQuery<Person> query =
                entityManager.createQuery("select p from Person p where p.nationalCode=:code and p.deleted=false", Person.class);
        query.setParameter("code", code);
        return query.getResultList().stream().findFirst();
    }
}
