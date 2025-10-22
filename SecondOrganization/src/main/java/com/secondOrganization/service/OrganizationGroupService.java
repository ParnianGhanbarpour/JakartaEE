package com.secondOrganization.service;

import com.secondOrganization.model.entity.OrganizationGroup;

import java.util.List;
import java.util.Optional;

public interface OrganizationGroupService {
    void save(OrganizationGroup group) throws Exception;
    void edit(OrganizationGroup group) throws Exception;
    void remove(OrganizationGroup group) throws Exception;
    void removeById(Long id) throws Exception;

    List<OrganizationGroup> findAll() throws Exception;
    Optional<OrganizationGroup> findById(Long id) throws Exception;
    List<OrganizationGroup> findByDepartmentId(Long departmentId) throws Exception;
    List<OrganizationGroup> findByName(String name) throws Exception;
}