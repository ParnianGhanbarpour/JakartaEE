package com.secondOrganization.service.impl;

import com.secondOrganization.model.entity.Token;
import com.secondOrganization.service.TokenService;
import jakarta.enterprise.context.SessionScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@SessionScoped
public class TokenServiceImpl implements TokenService, Serializable {

    @PersistenceContext(unitName = "organization")
    private EntityManager entityManager;

    @Transactional
    @Override
    public void save(Token token) throws Exception {
        log.info("Saving token for user {}", token.getUsername());
        entityManager.persist(token);
    }

    @Transactional
    @Override
    public void remove(Token token) throws Exception {
        Token t = entityManager.find(Token.class, token.getId());
        if (t != null) {
            entityManager.remove(t);
        }
    }

    @Transactional
    @Override
    public void removeById(Long id) throws Exception {
        Token t = entityManager.find(Token.class, id);
        if (t != null) {
            entityManager.remove(t);
        }
    }

    @Transactional
    @Override
    public Optional<Token> findById(Long id) throws Exception {
        return Optional.ofNullable(entityManager.find(Token.class, id));
    }

    @Transactional
    @Override
    public Optional<Token> findByValue(String tokenValue) throws Exception {
        TypedQuery<Token> q = entityManager.createQuery("select t from Token t where t.tokenValue = :val and t.deleted=false", Token.class);
        q.setParameter("val", tokenValue);
        return q.getResultList().stream().findFirst();
    }

    @Transactional
    @Override
    public List<Token> findAll() throws Exception {
        TypedQuery<Token> q = entityManager.createQuery("select t from Token t where t.deleted=false", Token.class);
        return q.getResultList();
    }

    @Transactional
    @Override
    public void removeExpiredTokens() throws Exception {
        TypedQuery<Token> q = entityManager.createQuery("select t from Token t where t.expiry < :now", Token.class);
        q.setParameter("now", LocalDateTime.now());
        List<Token> expired = q.getResultList();
        for (Token t : expired) {
            entityManager.remove(t);
        }
    }
}