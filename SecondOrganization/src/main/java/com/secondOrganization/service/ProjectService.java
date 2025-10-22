package com.secondOrganization.service;

import com.secondOrganization.model.entity.Project;
import com.secondOrganization.model.entity.enums.ProjectStatus;

import java.util.List;
import java.util.Optional;

public interface ProjectService {
    void save(Project project) throws Exception;
    void edit(Project project) throws Exception;
    void remove(Project project) throws Exception;
    void removeById(Long id) throws Exception;

    List<Project> findAll() throws Exception;
    Optional<Project> findById(Long id) throws Exception;
    List<Project> findByTitle(String title) throws Exception;
    List<Project> findByStatus(ProjectStatus status) throws Exception;
}