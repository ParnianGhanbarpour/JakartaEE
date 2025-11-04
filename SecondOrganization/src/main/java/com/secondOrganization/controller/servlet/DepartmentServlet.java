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

            Optional<Organization> optionalOrg = organizationService.findByName(orgName);
            if (optionalOrg.isEmpty()) {
                log.warn("Organization '{}' not found!", orgName);
                req.setAttribute("error", "سازمان مورد نظر یافت نشد!");
                req.setAttribute("departmentList", departmentService.findAll());
                req.setAttribute("organizationList", organizationService.findAll());
                req.getRequestDispatcher("/jsp/department.jsp").forward(req, resp);
                return;
            }

            Organization organization = optionalOrg.get();


            List<Branch> branches = branchService.findByOrganizationId(organization.getId());
            Branch branch = null;

            if (!branches.isEmpty()) {
                branch = branches.get(0);
                log.info("Using existing branch: {}", branch.getName());

            } else {

                branch = Branch.builder()
                        .name( "شعبه مرکزی"  + organization.getName())
                        .address("آدرس پیش‌فرض")
                        .city("تهران")
                        .manager("مدیر پیش‌فرض")
                        .organization(organization)
                        .deleted(false)
                        .build();
                branchService.save(branch);
                log.info("Default branch created for organization: {}", organization.getName());
            }

            Department department = Department.builder()
                    .name(name)
                    .field(field)
                    .duty(duty)
                    .phoneNumber(phoneNumber)
                    .organization(organization)
                    .branch(branch)
                    .deleted(false)
                    .build();

            departmentService.save(department);
            log.info("Department saved successfully: {}", department);

            resp.sendRedirect(req.getContextPath() + "/department.do");

        } catch (Exception e) {
            log.error("Error in DepartmentServlet.doPost: {}", e.getMessage(), e);
            req.setAttribute("error", "خطایی در ذخیره دپارتمان رخ داد: " + e.getMessage());
            try {
                req.setAttribute("departmentList", departmentService.findAll());
                req.setAttribute("organizationList", organizationService.findAll());
            } catch (Exception ex) {
                log.error("Error loading lists", ex);
            }
            req.getRequestDispatcher("/jsp/department.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("departmentList", departmentService.findAll());
            req.setAttribute("organizationList", organizationService.findAll());
            req.getRequestDispatcher("/jsp/department.jsp").forward(req, resp);
        } catch (Exception e) {
            log.error("Error in DepartmentServlet.doGet: {}", e.getMessage(), e);
            throw new ServletException("Cannot load departments", e);
        }
    }
}