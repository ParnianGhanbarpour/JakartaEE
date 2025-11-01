package com.secondOrganization.service.impl;

import com.secondOrganization.controller.exception.NoContentException;
import com.secondOrganization.model.entity.Department;
import com.secondOrganization.model.entity.User;
import com.secondOrganization.model.entity.enums.Role;
import com.secondOrganization.service.UserService;
import jakarta.enterprise.context.ApplicationScoped;
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
public class UserServiceImpl implements UserService, Serializable {

    @PersistenceContext(unitName = "organization")
    private EntityManager entityManager;

    @Transactional
    @Override
    public void save(User user) throws Exception {
        log.info("Saving user: {}", user.getUsername());
        user.setActive(true);
        entityManager.persist(user);
        entityManager.flush();
        log.info("User saved successfully: {}", user.getUsername());
    }

    @Transactional
    @Override
    public void edit(User user) throws Exception {
        log.info("Editing user: {}", user.getUsername());
        entityManager.merge(user);
    }

    @Transactional
    @Override
    public void remove(User user) throws Exception {
        user = entityManager.find(User.class, user.getUsername());
        if (user != null) {
            user.setDeleted(true);
            entityManager.merge(user);
        }
    }

    @Transactional
    @Override
    public void removeByUsername(String username) throws Exception {
        User user = entityManager.find(User.class, username);
        if (user != null) {
            user.setDeleted(true);
            entityManager.merge(user);
        }
    }

    @Transactional
    @Override
    public List<User> findAll() throws Exception {
        TypedQuery<User> query = entityManager.createQuery(
                "select u from User u where u.deleted=false", User.class);
        return query.getResultList();
    }

    @Transactional
    @Override
    public Optional<User> findByUsername(String username) throws Exception {
        log.debug("Finding user by username: {}", username);
        try {
            TypedQuery<User> query = entityManager.createQuery(
                    "select u from User u where u.username=:username and u.deleted=false",
                    User.class);
            query.setParameter("username", username);

            List<User> results = query.getResultList();

            if (results.isEmpty()) {
                log.warn("User not found: {}", username);
                return Optional.empty();
            }

            User user = results.get(0);
            log.debug("User found: {} (active={})", username, user.isActive());
            return Optional.of(user);

        } catch (Exception e) {
            log.error("Error finding user by username {}: {}", username, e.getMessage(), e);
            return Optional.empty();
        }
    }

    @Transactional
    @Override
    public Optional<User> findByUsernameAndPassword(String username, String password) throws Exception {
        log.debug("Attempting login for user: {}", username);

        try {
            TypedQuery<User> query = entityManager.createQuery(
                    "select u from User u where u.username=:username and u.deleted=false",
                    User.class);
            query.setParameter("username", username);

            List<User> results = query.getResultList();

            if (results.isEmpty()) {
                log.warn("User not found: {}", username);
                return Optional.empty();
            }

            User user = results.get(0);
            log.debug("User found: {}, checking password...", username);
            log.debug("Stored password: {}, Provided password: {}", user.getPassword(), password);

            // بررسی password (فعلاً plain text - بعداً باید hash بشه)
            if (user.getPassword().equals(password)) {
                log.info(" Login successful for user: {}", username);
                return Optional.of(user);
            } else {
                log.warn(" Invalid password for user: {}", username);
                return Optional.empty();
            }

        } catch (Exception e) {
            log.error("Error during login for user {}: {}", username, e.getMessage(), e);
            return Optional.empty();
        }
    }

    @Transactional
    @Override
    public List<User> findByRole(Role role) throws Exception {
        TypedQuery<User> query = entityManager.createQuery(
                "select u from User u where u.deleted=false", User.class);
        return query.getResultList();
    }

    @Transactional
    @Override
    public List<User> findByDepartment(Department department) throws Exception {
        TypedQuery<User> query = entityManager.createQuery(
                "select u from User u where u.deleted=false", User.class);
        return query.getResultList();
    }

    @Transactional
    @Override
    public List<User> findByActive(Boolean active) throws Exception {
        TypedQuery<User> query = entityManager.createQuery(
                "select u from User u where u.active=:active and u.deleted=false", User.class);
        query.setParameter("active", active);
        return query.getResultList();
    }

    @Transactional
    @Override
    public List<User> findUserByUsernames(List<String> userList) throws Exception {
        TypedQuery<User> query = entityManager.createQuery(
                "select u from User u where u.username in :userList and u.deleted=false",
                User.class);
        query.setParameter("userList", userList);
        return query.getResultList();
    }

    @Transactional
    @Override
    public Optional<User> findById(long id) throws NoContentException {
        try {
            TypedQuery<User> query = entityManager.createQuery(
                    "select u from User u where u.id=:id and u.deleted=false", User.class);
            query.setParameter("id", id);

            List<User> results = query.getResultList();

            if (results.isEmpty()) {
                throw new NoContentException("User with id : " + id + " not found!");
            }

            return Optional.of(results.get(0));

        } catch (Exception e) {
            log.error("Error finding user by id {}: {}", id, e.getMessage());
            throw new NoContentException("User with id : " + id + " not found!");
        }
    }
}