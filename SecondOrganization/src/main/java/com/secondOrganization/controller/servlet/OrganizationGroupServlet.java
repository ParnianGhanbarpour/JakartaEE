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
        String method = req.getParameter("_method");

        if ("delete".equalsIgnoreCase(method)) {
            doDelete(req, resp);
            return;
        }

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
                log.info("Organization group saved: {}", group.getName());

                resp.sendRedirect(req.getContextPath() + "/organizationGroup.do");
            } else {
                log.error("Department not found with id: {}", deptId);
                req.setAttribute("error", "دپارتمان مورد نظر یافت نشد!");
                loadDataAndForward(req, resp);
            }

        } catch (Exception e) {
            log.error("Error saving group", e);
            req.setAttribute("error", "خطا در ذخیره گروه: " + e.getMessage());
            loadDataAndForward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        loadDataAndForward(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            long id = Long.parseLong(req.getParameter("id"));
            groupService.removeById(id);
            log.info("Organization group deleted: {}", id);
            resp.sendRedirect(req.getContextPath() + "/organizationGroup.do");
        } catch (Exception e) {
            log.error("Error deleting organization group", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private void loadDataAndForward(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            req.setAttribute("organizationGroupList", groupService.findAll());
            req.setAttribute("departmentList", departmentService.findAll());
            req.getRequestDispatcher("/jsp/organizationGroup.jsp").forward(req, resp);
        } catch (Exception e) {
            log.error("Error loading data", e);
            throw new ServletException("Cannot load organization groups", e);
        }
    }
}