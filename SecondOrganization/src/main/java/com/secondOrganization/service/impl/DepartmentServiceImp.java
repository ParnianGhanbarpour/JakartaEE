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
        log.info("Section Saved");
        entityManager.persist(department);
    }

    @Transactional
    @Override
    public void edit(Department department) throws Exception {
        entityManager.merge(department);
    }

    @Transactional
    @Override
    public void remove(Department department) throws Exception {
        department.setDeleted(true);
        entityManager.merge(department);
    }

    @Transactional
    @Override
    public void removeById(Long id) throws Exception {
        Department department = entityManager.find(Department.class, id);
        department.setDeleted(true);
        entityManager.merge(department);
    }

    @Transactional
    @Override
    public List<Department> findAll() throws Exception {
        TypedQuery<Department> query = entityManager.createQuery("select oo from Department oo", Department.class);
        return query.getResultList();
    }

    @Transactional
    @Override
    public Optional<Department> findById(Long id) throws Exception {
        return Optional.ofNullable(entityManager.find(Department.class, id));
    }


    @Transactional
    @Override
    public List<Department> findAllWithOrganizationAndBranch() {
        log.info("دریافت تمام دپارتمان‌ها به همراه سازمان و شعبه");

        try {
            List<Department> departments = entityManager.createQuery(
                    "SELECT DISTINCT d FROM Department d " +
                            "LEFT JOIN FETCH d.organization " +
                            "LEFT JOIN FETCH d.branch " +
                            "ORDER BY d.id",
                    Department.class
            ).getResultList();

            log.info("تعداد دپارتمان‌های یافت شده: {}", departments.size());
            return departments;

        } catch (Exception e) {
            log.error("خطا در دریافت دپارتمان‌ها: {}", e.getMessage());
            throw new RuntimeException("خطا در دریافت دپارتمان‌ها", e);
        }
    }


    @Transactional
    @Override
    public Optional<Department> findByName(String name) throws Exception {
        TypedQuery<Department> query = entityManager.createQuery("select oo from Department oo where oo.name=:name", Department.class);
        query.setParameter("name", name);
        List<Department> result = query.getResultList();
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

}
