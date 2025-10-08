package com.organization.model.service;

import com.organization.controller.exception.OrganizationNotFoundException;
import com.organization.model.entity.Organization;
import com.organization.model.repository.CrudRepository;

import java.util.List;

public class OrganizationService {

    public Organization save(Organization organization) throws Exception {
        try (CrudRepository<Organization, Integer> repository = new CrudRepository<>()) {
            return repository.save(organization);
        }
    }

    public Organization edit(Organization organization) throws Exception {
        try (CrudRepository<Organization, Integer> repository = new CrudRepository<>()) {
            if (repository.findById(organization.getId(), Organization.class) != null) {
                return repository.edit(organization);
            } else {
                throw new OrganizationNotFoundException();
            }
        }
    }

    public Organization deleteById(int id) throws Exception {
        try (CrudRepository<Organization, Integer> repository = new CrudRepository<>()) {
            if (repository.findById(id, Organization.class) != null) {
                return repository.deleteById(id, Organization.class);
            } else {
                throw new OrganizationNotFoundException();
            }
        }
    }

    public List<Organization> findAll() throws Exception {
        try (CrudRepository<Organization, Integer> repository = new CrudRepository<>()) {
            return repository.findAll(Organization.class);
        }
    }

    public Organization findById(int id) throws Exception {
        try (CrudRepository<Organization, Integer> repository = new CrudRepository<>()) {
            return repository.findById(id, Organization.class);
        }
    }
}
