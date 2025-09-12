package com.example.organization.model.tools;


import lombok.Getter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaProvider {
    @Getter
    private static JpaProvider provider = new JpaProvider();
    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("organization");

    private JpaProvider() {
    }

    public EntityManager getEntityManager() {
        return factory.createEntityManager();
    }
}
