package com.secondOrganization.service;

import com.secondOrganization.model.entity.Token;

import java.util.List;
import java.util.Optional;

public interface TokenService {
    void save(Token token) throws Exception;
    void remove(Token token) throws Exception;
    void removeById(Long id) throws Exception;

    Optional<Token> findById(Long id) throws Exception;
    Optional<Token> findByValue(String tokenValue) throws Exception;
    List<Token> findAll() throws Exception;
    void removeExpiredTokens() throws Exception;
}