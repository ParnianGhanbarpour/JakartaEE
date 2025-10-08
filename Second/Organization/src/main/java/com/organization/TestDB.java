package com.organization;

import com.organization.model.entity.Organization;

import javax.persistence.*;

public class TestDB {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("organization");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        em.persist(new Organization());
        em.getTransaction().commit();

        em.close();
        emf.close();
    }
}
