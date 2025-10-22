package com.secondOrganization.service;

import com.secondOrganization.model.entity.ProjectAssignment;

import java.util.List;
import java.util.Optional;

public interface ProjectAssignmentService {
    void save(ProjectAssignment assignment) throws Exception;
    void edit(ProjectAssignment assignment) throws Exception;
    void remove(ProjectAssignment assignment) throws Exception;
    void removeById(Long id) throws Exception;

    List<ProjectAssignment> findAll() throws Exception;
    Optional<ProjectAssignment> findById(Long id) throws Exception;
    List<ProjectAssignment> findByProjectId(Long projectId) throws Exception;
    List<ProjectAssignment> findByPersonId(Long personId) throws Exception;
}