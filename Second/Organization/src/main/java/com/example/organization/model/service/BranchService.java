package com.example.organization.model.service;

import com.example.organization.model.entity.Branch;
import com.example.organization.model.repository.CrudRepository;

import java.util.List;

public class BranchService {

    public Branch save(Branch branch) throws Exception {
        try (CrudRepository<Branch, Integer> repository = new CrudRepository<>()) {
            return repository.save(branch);
        }
    }

    public Branch edit(Branch branch) throws Exception {
        try (CrudRepository<Branch, Integer> repository = new CrudRepository<>()) {
            if (repository.findById(branch.getId(), Branch.class) != null) {
                return repository.edit(branch);
            } else {
                throw new Exception();
            }
        }
    }

    public Branch deleteById(int id) throws Exception {
        try (CrudRepository<Branch, Integer> repository = new CrudRepository<>()) {
            if (repository.findById(id, Branch.class) != null) {
                return repository.deleteById(id, Branch.class);
            } else {
                throw new Exception();
            }
        }
    }

    public List<Branch> findAll() throws Exception {
        try (CrudRepository<Branch, Integer> repository = new CrudRepository<>()) {
            return repository.findAll(Branch.class);
        }
    }

    public Branch findById(int id) throws Exception {
        try (CrudRepository<Branch, Integer> repository = new CrudRepository<>()) {
            return repository.findById(id, Branch.class);
        }
    }
}
