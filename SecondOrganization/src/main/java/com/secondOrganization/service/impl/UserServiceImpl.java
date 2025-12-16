package com.secondOrganization.service.impl;

import com.secondOrganization.controller.exception.NoContentException;
import com.secondOrganization.model.entity.Department;
import com.secondOrganization.model.entity.User;
import com.secondOrganization.model.entity.enums.Role;
import com.secondOrganization.service.UserService;
import com.secondOrganization.utils.PasswordUtil;
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
    public void save(User user) throws Exception {
        log.info("Saving user: {}", user.getUsername());

        try {
            if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
                throw new IllegalArgumentException("Username cannot be null or empty");
            }

            if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
                throw new IllegalArgumentException("Password cannot be null or empty");
            }

            String rawPassword = user.getPassword();
            if (rawPassword.length() < 5 || rawPassword.length() > 20) {
                throw new IllegalArgumentException("Password must be between 5 and 20 characters");
            }

            Optional<User> existing = findByUsername(user.getUsername().trim());
            if (existing.isPresent()) {
                String msg = "Username already exists: " + user.getUsername();
                log.warn(msg);
                throw new IllegalArgumentException(msg);
            }

            String password = user.getPassword();
            if (!password.startsWith("$2a$") && !password.startsWith("$2b$") && !password.startsWith("$2y$")) {
                password = PasswordUtil.hashPassword(password);
                user.setPassword(password);
                log.debug("Password hashed for user: {}", user.getUsername());
            } else {
                log.debug("Password already hashed for user: {}", user.getUsername());
            }

            user.setUsername(user.getUsername().trim());
            user.setActive(true);
            user.setDeleted(false);

            entityManager.persist(user);
            entityManager.flush();

            log.info(" User saved successfully: {}", user.getUsername());

        } catch (IllegalArgumentException e) {
            log.warn("Business rule violation: {}", e.getMessage());
            throw e;

        } catch (Exception e) {
            log.error("Unexpected error saving user {}: {}",
                    user.getUsername(), e.getMessage(), e);
            throw new Exception("Failed to save user: " + e.getMessage(), e);
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
            log.warn("User not found: {}", username);
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

    @Override
    public Optional<User> findByUsernameAndPassword(String username, String password) throws Exception {
        log.debug("Attempting authentication for user: {}", username);

        Optional<User> userOpt = findByUsername(username);
        if (userOpt.isEmpty()) {
            log.warn(" User not found: {}", username);
            return Optional.empty();
        }

        User user = userOpt.get();

        if (!user.isActive()) {
            log.warn(" User is inactive: {}", username);
            return Optional.empty();
        }

        String storedPassword = user.getPassword();
        boolean passwordMatches;

        if (storedPassword.startsWith("$2a$") || storedPassword.startsWith("$2b$") || storedPassword.startsWith("$2y$")) {
            passwordMatches = PasswordUtil.checkPassword(password, storedPassword);
            log.debug("BCrypt password check for user {}: {}", username, passwordMatches);
        } else {
            passwordMatches = storedPassword.equals(password);
            log.debug("Plain text password check for user {}: {}", username, passwordMatches);

            if (passwordMatches) {
                log.info("Upgrading plain text password to BCrypt for user: {}", username);
                user.setPassword(PasswordUtil.hashPassword(password));
                entityManager.merge(user);
                entityManager.flush();
            }
        }

        if (passwordMatches) {
            log.info(" Authentication successful for user: {}", username);
            return Optional.of(user);
        } else {
            log.warn(" Invalid password for user: {}", username);
            return Optional.empty();
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