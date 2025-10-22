package com.secondOrganization.service;

import com.secondOrganization.model.entity.Organization;

import java.util.List;
import java.util.Optional;

public interface OrganizationService {
    void save(Organization organization) throws Exception;
    void edit(Organization organization) throws Exception;
    void remove(Organization organization) throws Exception;
    void removeById(Long id) throws Exception;

    List<Organization> findAll() throws Exception;
    Optional<Organization> findById(Long id) throws Exception;
    Optional<Organization> findByName(String name) throws Exception;
}
