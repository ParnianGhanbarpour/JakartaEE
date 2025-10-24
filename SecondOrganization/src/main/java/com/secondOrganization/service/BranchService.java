package com.secondOrganization.service;

import com.secondOrganization.model.entity.Branch;

import java.util.List;
import java.util.Optional;

public interface BranchService {
    void save(Branch branch) throws Exception;
    void edit(Branch branch) throws Exception;
    void remove(Branch branch) throws Exception;
    void removeById(Long id) throws Exception;

    List<Branch> findAll() throws Exception;
    Optional<Branch> findById(Integer id) throws Exception;
    List<Branch> findByCity(String city) throws Exception;
    List<Branch> findByManager(String manager) throws Exception;
    List<Branch> findByOrganizationId(Integer organizationId) throws Exception;
}