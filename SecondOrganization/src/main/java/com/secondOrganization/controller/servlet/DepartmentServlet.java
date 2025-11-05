package com.secondOrganization.controller.servlet;

import com.secondOrganization.model.entity.Branch;
import com.secondOrganization.model.entity.Department;
import com.secondOrganization.model.entity.Organization;
import com.secondOrganization.service.impl.BranchServiceImpl;
import com.secondOrganization.service.impl.DepartmentServiceImp;
import com.secondOrganization.service.impl.OrganizationServiceImpl;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@WebServlet(urlPatterns = "/department.do")
public class DepartmentServlet extends HttpServlet {

    @Inject
    private DepartmentServiceImp departmentService;

    @Inject
    private OrganizationServiceImpl organizationService;

    @Inject
    private BranchServiceImpl branchService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String name = req.getParameter("name");
            String field = req.getParameter("field");
            String duty = req.getParameter("duty");
            String phoneNumber = req.getParameter("phoneNumber");
            String orgName = req.getParameter("organizationName");
            String branchIdStr = req.getParameter("branchId");

            log.info("Received parameters - Name: {}, Field: {}, Org: {}, BranchId: {}",
                    name, field, orgName, branchIdStr);

            List<String> validationErrors = new ArrayList<>();

            if (name == null || name.trim().isEmpty()) {
                validationErrors.add("نام دپارتمان اجباری است");
            }
            if (field == null || field.trim().isEmpty()) {
                validationErrors.add("زمینه فعالیت اجباری است");
            }
            if (orgName == null || orgName.trim().isEmpty()) {
                validationErrors.add("انتخاب سازمان اجباری است");
            }
            if (branchIdStr == null || branchIdStr.trim().isEmpty()) {
                validationErrors.add("انتخاب شعبه اجباری است");
            }

            if (!validationErrors.isEmpty()) {
                req.setAttribute("error", String.join(" - ", validationErrors));
                loadDataAndForward(req, resp);
                return;
            }

            name = name.trim();
            field = field.trim();
            orgName = orgName.trim();

            Optional<Organization> optionalOrg = organizationService.findByName(orgName);
            if (optionalOrg.isEmpty()) {
                req.setAttribute("error", "سازمان مورد نظر یافت نشد!");
                loadDataAndForward(req, resp);
                return;
            }

            Organization organization = optionalOrg.get();

            Long branchId = Long.parseLong(branchIdStr);
            Optional<Branch> optionalBranch = branchService.findById(branchId);

            if (optionalBranch.isEmpty()) {
                req.setAttribute("error", "شعبه انتخاب شده یافت نشد!");
                loadDataAndForward(req, resp);
                return;
            }

            Branch branch = optionalBranch.get();

            // بررسی تعلق شعبه به سازمان
            if (!branch.getOrganization().getId().equals(organization.getId())) {
                req.setAttribute("error", "شعبه انتخاب شده متعلق به سازمان انتخاب شده نیست!");
                loadDataAndForward(req, resp);
                return;
            }

            Optional<Department> existingDept = departmentService.findByName(name);
            if (existingDept.isPresent()) {
                req.setAttribute("error", "دپارتمان با نام '" + name + "' قبلاً ثبت شده است!");
                loadDataAndForward(req, resp);
                return;
            }

            Department department = Department.builder()
                    .name(name)
                    .field(field)
                    .duty(duty != null ? duty.trim() : null)
                    .phoneNumber(phoneNumber != null ? phoneNumber.trim() : null)
                    .organization(organization)
                    .branch(branch)
                    .deleted(false)
                    .build();

            departmentService.save(department);
            log.info("Department saved successfully: {}", department.getName());

            req.setAttribute("success", "دپارتمان با موفقیت ثبت شد");

        } catch (NumberFormatException e) {
            log.error("Invalid branch ID format: {}", e.getMessage());
            req.setAttribute("error", "شناسه شعبه نامعتبر است");
        } catch (Exception e) {
            log.error("Error in DepartmentServlet.doPost: {}", e.getMessage(), e);
            req.setAttribute("error", "خطایی در ذخیره دپارتمان رخ داد: " +
                    (e.getCause() != null ? e.getCause().getMessage() : e.getMessage()));
        }

        loadDataAndForward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        loadDataAndForward(req, resp);
    }

    private void loadDataAndForward(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            List<Department> departments = departmentService.findAllWithOrganizationAndBranch();
            List<Organization> organizations = organizationService.findAll();
            List<Branch> allBranches = branchService.findAll();

            req.setAttribute("departmentList", departments);
            req.setAttribute("organizationList", organizations);
            req.setAttribute("allBranches", allBranches);

            log.info("Loaded {} departments, {} organizations, and {} branches",
                    departments.size(), organizations.size(), allBranches.size());

        } catch (Exception e) {
            log.error("Error loading data in DepartmentServlet: {}", e.getMessage(), e);
            req.setAttribute("error", "خطا در بارگذاری داده‌ها: " + e.getMessage());
        }

        req.getRequestDispatcher("/jsp/department.jsp").forward(req, resp);
    }
}