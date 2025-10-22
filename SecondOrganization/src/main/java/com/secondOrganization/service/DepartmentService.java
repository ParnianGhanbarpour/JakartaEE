package com.secondOrganization.service;


import com.secondOrganization.model.entity.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {
    void save(Department department) throws Exception;

    void edit(Department department) throws Exception;

    void remove(Department department) throws Exception;

    void removeById(Long id) throws Exception;

    List<Department> findAll() throws Exception;

    Optional<Department> findById(Long id) throws Exception;

    Optional<Department> findByTitle(String title) throws Exception;
}
