package com.secondOrganization.repository;

import com.secondOrganization.model.entity.Token;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.time.LocalDateTime;
import java.util.Optional;

@Stateless
@CacheDB
public class TokenRepository extends BaseRepository<Token> {

    @PersistenceContext(unitName = "cachePU")
    private EntityManager entityManager;

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    protected Class<Token> getEntityClass() {
        return Token.class;
    }

    public Optional<Token> findByTokenValue(String tokenValue) {
        return entityManager.createQuery("SELECT t FROM Token t WHERE t.tokenValue = :tokenValue", Token.class)
                .setParameter("tokenValue", tokenValue)
                .getResultList().stream().findFirst();
    }

    public void deleteExpired() {
        entityManager.createQuery("DELETE FROM Token t WHERE t.expiry < :now")
                .setParameter("now", LocalDateTime.now())
                .executeUpdate();
    }
}