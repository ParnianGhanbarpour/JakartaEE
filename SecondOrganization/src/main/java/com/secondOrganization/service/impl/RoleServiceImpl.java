package com.secondOrganization.service.impl;


import com.secondOrganization.model.entity.Role;
import com.secondOrganization.service.RoleService;
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
@SessionScoped
public class RoleServiceImpl implements RoleService, Serializable {
    @PersistenceContext(unitName = "organization")
    private EntityManager entityManager;

    @Transactional
    @Override
    public void save(Role role) throws Exception {
        entityManager.persist(role);
    }

    @Transactional
    @Override
    public void edit(Role role) throws Exception {
        entityManager.merge(role);
    }

    @Transactional
    @Override
    public void remove(Role role) throws Exception {
        role = entityManager.find(Role.class, role.getId());
        role.setDeleted(true);
        entityManager.merge(role);
    }

    @Transactional
    @Override
    public void removeById(Long id) throws Exception {
        Role role = entityManager.find(Role.class, id);
        role.setDeleted(true);
        entityManager.merge(role);
    }

    @Transactional
    @Override
    public Optional<Role> findById(Long id) throws Exception {
        return Optional.ofNullable(entityManager.find(Role.class, id));
    }

    @Transactional
    @Override
    public List<Role> findAll() throws Exception {
        TypedQuery<Role> query = entityManager.createQuery("select oo from roleEntity oo where oo.deleted=false", Role.class);
        return query.getResultList();
    }

    @Transactional
    @Override
    public List<Role> findByRoleName(String roleName) throws Exception {
        TypedQuery<Role> query = entityManager.createQuery("select oo from roleEntity oo where oo.role=:roleName", Role.class);
        query.setParameter("roleName",roleName);
        return query.getResultList();
    }

    @Transactional
    @Override
    public List<Role> findByUser(String username) throws Exception {
        TypedQuery<Role> query = entityManager.createQuery("select oo from roleEntity oo where oo.user=:username", Role.class);
        query.setParameter("username",username);
        return query.getResultList();
    }

    @Transactional
    @Override
    public List<Role> findByUsernameAndRoleName(String username, String roleName) throws Exception {
        TypedQuery<Role> query = entityManager.
                createQuery
                        ("select oo from roleEntity oo where oo.user.username=:username and oo.role=:roleName and deleted=false", Role.class);
        query.setParameter("username",username);
        query.setParameter("roleName",roleName);
        return query.getResultList();
    }
}
