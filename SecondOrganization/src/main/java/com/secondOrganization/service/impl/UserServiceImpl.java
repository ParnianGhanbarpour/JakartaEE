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
import jakarta.validation.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@ApplicationScoped
public class UserServiceImpl implements UserService, Serializable {

    @PersistenceContext(unitName = "organization")
    private EntityManager entityManager;

    @Transactional
    @Override
    public void save(User user)  throws Exception {
        log.info("Saving user: {}", user.getUsername());

        try {
            Optional<User> existing = findByUsername(user.getUsername());
            if (existing.isPresent()) {
                String msg = "Username already exists: " + user.getUsername();
                log.warn(msg);
                throw new IllegalArgumentException(msg);
            }

            user.setActive(true);
            user.setDeleted(false);

            entityManager.persist(user);
            entityManager.flush();

            log.info(" User saved successfully: {}", user.getUsername());

        } catch (ConstraintViolationException e) {
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            for (ConstraintViolation<?> violation : violations) {
                log.error("Validation error: {} = {}",
                        violation.getPropertyPath(),
                        violation.getMessage());
            }
            throw new Exception("Validation failed for user: " + user.getUsername(), e);

        } catch (Exception e) {
            log.error("Error saving user {}: {}", user.getUsername(), e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    @Override
    public void edit(User user) throws Exception {
        log.info("Editing user: {}", user.getUsername());

        if (user.getId() == null) {
            throw new IllegalArgumentException("User ID is required for edit");
        }

        User managed = entityManager.merge(user);
        entityManager.flush();

        log.info(" User updated successfully: {}", managed.getUsername());
    }

    @Transactional
    @Override
    public void remove(User user) throws Exception {
        User managedUser = entityManager.find(User.class, user.getId());
        if (managedUser != null) {
            managedUser.setDeleted(true);
            entityManager.merge(managedUser);
            log.info(" User soft deleted: {}", managedUser.getUsername());
        } else {
            log.warn("User not found for deletion: {}", user.getId());
        }
    }

    @Transactional
    @Override
    public void removeByUsername(String username) throws Exception {
        Optional<User> userOpt = findByUsername(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setDeleted(true);
            entityManager.merge(user);
            log.info(" User soft deleted by username: {}", username);
        } else {
            log.warn("  User not found: {}", username);
        }
    }

    @Transactional
    @Override
    public List<User> findAll() throws Exception {
        log.debug("Finding all users");

        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u WHERE u.deleted = false ORDER BY u.username",
                User.class
        );

        List<User> users = query.getResultList();
        log.debug(" Found {} users", users.size());

        return users;
    }

    @Transactional
    @Override
    public Optional<User> findByUsername(String username) throws Exception {
        log.debug("Finding user by username: {}", username);

        if (username == null || username.trim().isEmpty()) {
            return Optional.empty();
        }

        try {
            TypedQuery<User> query = entityManager.createQuery(
                    "SELECT u FROM User u WHERE u.username = :username AND u.deleted = false",
                    User.class
            );
            query.setParameter("username", username.trim());

            List<User> results = query.getResultList();

            if (results.isEmpty()) {
                log.warn("User not found: {}", username);
                return Optional.empty();
            }

            User user = results.get(0);
            log.debug(" User found: {} (active={})", username, user.isActive());
            return Optional.of(user);

        } catch (Exception e) {
            log.error("Error finding user by username {}: {}", username, e.getMessage());
            throw e;
        }
    }


    @Transactional
    @Override
    public Optional<User> findByUsernameAndPassword(String username, String password) throws Exception {
        log.debug("Attempting authentication for user: {}", username);

        if (username == null || password == null) {
            return Optional.empty();
        }

        try {
            TypedQuery<User> query = entityManager.createQuery(
                    "SELECT u FROM User u WHERE u.username = :username AND u.deleted = false",
                    User.class
            );
            query.setParameter("username", username.trim());

            List<User> results = query.getResultList();

            if (results.isEmpty()) {
                log.warn(" User not found: {}", username);
                return Optional.empty();
            }

            User user = results.get(0);

            if (user.getPassword().equals(password)) {
                if (!user.isActive()) {
                    log.warn(" User is inactive: {}", username);
                    return Optional.empty();
                }

                log.info(" Authentication successful for user: {}", username);
                return Optional.of(user);
            } else {
                log.warn(" Invalid password for user: {}", username);
                return Optional.empty();
            }

        } catch (Exception e) {
            log.error("Error during authentication for user {}: {}", username, e.getMessage());
            throw e;
        }
    }


    @Transactional
    @Override
    public List<User> findByRole(Role role) throws Exception {
        return findAll();
    }

    @Transactional
    @Override
    public List<User> findByDepartment(Department department) throws Exception {
        return findAll();
    }

    @Transactional
    @Override
    public List<User> findByActive(Boolean active) throws Exception {
        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u WHERE u.active = :active AND u.deleted = false",
                User.class
        );
        query.setParameter("active", active);
        return query.getResultList();
    }


    @Transactional
    @Override
    public List<User> findUserByUsernames(List<String> userList) throws Exception {
        if (userList == null || userList.isEmpty()) {
            return List.of();
        }

        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u WHERE u.username IN :userList AND u.deleted = false",
                User.class
        );
        query.setParameter("userList", userList);
        return query.getResultList();
    }

    @Transactional
    @Override
    public Optional<User> findById(long id) throws NoContentException {
        try {
            User user = entityManager.find(User.class, id);

            if (user == null || user.isDeleted()) {
                throw new NoContentException("User with id: " + id + " not found!");
            }

            return Optional.of(user);

        } catch (NoContentException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error finding user by id {}: {}", id, e.getMessage());
            throw new NoContentException("User with id: " + id + " not found!");
        }
    }
}