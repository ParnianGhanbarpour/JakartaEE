package com.secondOrganization.repository;

import com.secondOrganization.model.entity.Person;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class PersonRepository extends BaseRepository<Person> {

    @PersistenceContext(unitName = "organization")
    private EntityManager entityManager;

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    protected Class<Person> getEntityClass() {
        return Person.class;
    }
}
