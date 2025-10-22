package com.secondOrganization.service;

import com.secondOrganization.model.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    void save(Role role) throws Exception;
    void edit(Role role) throws Exception;
    void remove(Role role) throws Exception;
    void removeById(Long id) throws Exception;

    Optional<Role> findById(Long id) throws Exception;
    List<Role> findAll() throws Exception;

    List<Role> findByRoleName(String roleName) throws Exception;
    List<Role> findByUser(String username) throws Exception;

    List<Role> findByUsernameAndRoleName(String username, String roleName) throws Exception;
}
