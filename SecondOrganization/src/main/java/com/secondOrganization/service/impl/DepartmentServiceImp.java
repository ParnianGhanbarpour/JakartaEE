package com.secondOrganization.service.impl;

import com.secondOrganization.model.entity.Department;
import com.secondOrganization.service.DepartmentService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Slf4j
@ApplicationScoped
public class DepartmentServiceImp implements DepartmentService, Serializable {

    @PersistenceContext(unitName = "organization")
    private EntityManager entityManager;

    @Transactional
    @Override
    public void save(Department department) throws Exception {
        try {
            log.info("Saving Department: {}", department.getName());
            entityManager.persist(department);
            entityManager.flush();
            log.info("Department Saved Successfully");
        } catch (Exception e) {
            log.error("Error saving department: {}", e.getMessage(), e);
            throw new Exception("خطا در ذخیره دپارتمان: " + e.getMessage(), e);
        }
    }

    @Transactional
    @Override
    public void edit(Department department) throws Exception {
        try {
            log.info("Updating Department: {}", department.getName());
            entityManager.merge(department);
            entityManager.flush();
            log.info("Department Updated Successfully");
        } catch (Exception e) {
            log.error("Error updating department: {}", e.getMessage(), e);
            throw new Exception("خطا در به‌روزرسانی دپارتمان: " + e.getMessage(), e);
        }
    }

    @Transactional
    @Override
    public void remove(Department department) throws Exception {
        try {
            log.info("Soft deleting Department: {}", department.getName());
            Department managedDept = entityManager.merge(department);
            managedDept.setDeleted(true);
            entityManager.flush();
            log.info("Department Soft Deleted Successfully");
        } catch (Exception e) {
            log.error("Error soft deleting department: {}", e.getMessage(), e);
            throw new Exception("خطا در حذف دپارتمان: " + e.getMessage(), e);
        }
    }

    @Transactional
    @Override
    public void removeById(Long id) throws Exception {
        try {
            log.info("Soft deleting Department by ID: {}", id);
            Department department = entityManager.find(Department.class, id);
            if (department != null) {
                department.setDeleted(true);
                entityManager.flush();
                log.info("Department Soft Deleted Successfully by ID: {}", id);
            } else {
                throw new Exception("دپارتمان با شناسه " + id + " یافت نشد");
            }
        } catch (Exception e) {
            log.error("Error soft deleting department by ID: {}", e.getMessage(), e);
            throw new Exception("خطا در حذف دپارتمان: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Department> findAll() throws Exception {
        try {
            TypedQuery<Department> query = entityManager.createQuery(
                    "SELECT d FROM Department d WHERE d.deleted = false", Department.class);
            return query.getResultList();
        } catch (Exception e) {
            log.error("Error finding all departments: {}", e.getMessage(), e);
            throw new Exception("خطا در دریافت لیست دپارتمان‌ها", e);
        }
    }

    @Override
    public Optional<Department> findById(Long id) throws Exception {
        try {
            Department department = entityManager.find(Department.class, id);
            return (department != null && !department.isDeleted()) ?
                    Optional.of(department) : Optional.empty();
        } catch (Exception e) {
            log.error("Error finding department by ID {}: {}", id, e.getMessage(), e);
            throw new Exception("خطا در یافتن دپارتمان", e);
        }
    }

    @Override
    public List<Department> findAllWithOrganizationAndBranch() {
        try {
            log.info("Fetching all departments with organization and branch");
            List<Department> departments = entityManager.createQuery(
                    "SELECT DISTINCT d FROM Department d " +
                            "LEFT JOIN FETCH d.organization " +
                            "LEFT JOIN FETCH d.branch " +
                            "WHERE d.deleted = false " +
                            "ORDER BY d.id", Department.class
            ).getResultList();

            log.info("Number of departments found: {}", departments.size());
            return departments;
        } catch (Exception e) {
            log.error("Error fetching departments with organization and branch: {}", e.getMessage(), e);
            throw new RuntimeException("خطا در دریافت دپارتمان‌ها", e);
        }
    }

    @Override
    public Optional<Department> findByName(String name) throws Exception {
        try {
            TypedQuery<Department> query = entityManager.createQuery(
                    "SELECT d FROM Department d WHERE d.name = :name AND d.deleted = false",
                    Department.class);
            query.setParameter("name", name);
            List<Department> result = query.getResultList();
            return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
        } catch (Exception e) {
            log.error("Error finding department by name {}: {}", name, e.getMessage(), e);
            throw new Exception("خطا در یافتن دپارتمان با نام", e);
        }
    }

    public List<Department> findDepartmentsWithHighBudget() throws Exception {
        TypedQuery<Department> query = entityManager.createQuery(
                "SELECT d FROM Department d " +
                        "WHERE d.budget > (SELECT AVG(d2.budget) FROM Department d2) " +
                        "AND d.deleted = false " +
                        "ORDER BY d.budget DESC",
                Department.class
        );
        return query.getResultList();
    }
}