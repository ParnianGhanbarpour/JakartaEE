package com.secondOrganization.controller.servlet;

import com.secondOrganization.model.entity.Department;
import com.secondOrganization.model.entity.OrganizationGroup;
import com.secondOrganization.service.impl.DepartmentServiceImp;
import com.secondOrganization.service.impl.OrganizationGroupServiceImpl;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@WebServlet(urlPatterns = "/organizationGroup.do")
public class OrganizationGroupServlet extends HttpServlet {

    @Inject
    private OrganizationGroupServiceImpl groupService;

    @Inject
    private DepartmentServiceImp departmentService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String name = req.getParameter("name");
            String specialty = req.getParameter("specialty");
            long deptId = Long.parseLong(req.getParameter("departmentId"));

            Optional<Department> optionalDepartment = departmentService.findById(deptId);

            if (optionalDepartment.isPresent()) {
                Department department = optionalDepartment.get();
                OrganizationGroup group = OrganizationGroup.builder()
                        .name(name)
                        .specialty(specialty)
                        .department(department)
                        .deleted(false)
                        .build();

                groupService.save(group);
                resp.sendRedirect("/jsp/organizationGroup.jsp");
            } else {
                log.error("Department not found with id: {}", deptId);
                resp.sendRedirect("/error.jsp");
            }

        } catch (Exception e) {
            log.error("Error saving group", e);
            resp.sendRedirect("/error.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.getSession().setAttribute("organizationGroupList", groupService.findAll());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            req.getSession().setAttribute("departmentList", departmentService.findAll());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        req.getRequestDispatcher("/jsp/organizationGroup.jsp").forward(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            long id = Long.parseLong(req.getParameter("id"));
            groupService.removeById(id);
            resp.sendRedirect("/jsp/organizationGroup.jsp");
        } catch (Exception e) {
            log.error("Error deleting organization group", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
