package com.secondOrganization.controller.servlet;

import com.secondOrganization.model.entity.Project;
import com.secondOrganization.model.entity.enums.ProjectStatus;
import com.secondOrganization.service.impl.ProjectServiceImpl;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@WebServlet(urlPatterns = "/project.do")
public class ProjectServlet extends HttpServlet {

    @Inject
    private ProjectServiceImpl projectService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("_method");

        if ("delete".equalsIgnoreCase(method)) {
            doDelete(req, resp);
            return;
        }

        try {
            String title = req.getParameter("title");
            String description = req.getParameter("description");
            LocalDateTime startDate = LocalDateTime.parse(req.getParameter("startDate"));
            LocalDateTime endDate = LocalDateTime.parse(req.getParameter("endDate"));
            double budget = Double.parseDouble(req.getParameter("budget"));
            ProjectStatus status = ProjectStatus.valueOf(req.getParameter("status"));

            Project project = Project.builder()
                    .title(title)
                    .description(description)
                    .startDate(startDate)
                    .endDate(endDate)
                    .budget(budget)
                    .status(status)
                    .deleted(false)
                    .build();

            projectService.save(project);
            log.info("Project created: {}", project.getTitle());

            resp.sendRedirect(req.getContextPath() + "/project.do");

        } catch (Exception e) {
            log.error("Error saving project", e);
            req.setAttribute("error", "خطا در ذخیره پروژه: " + e.getMessage());
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
            projectService.removeById(id);
            log.info("Project deleted: {}", id);
            resp.sendRedirect(req.getContextPath() + "/project.do");
        } catch (Exception e) {
            log.error("Error deleting project", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private void loadDataAndForward(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            req.setAttribute("projectList", projectService.findAll());
            req.getRequestDispatcher("/jsp/project.jsp").forward(req, resp);
        } catch (Exception e) {
            log.error("Error loading projects", e);
            throw new ServletException("Cannot load projects", e);
        }
    }
}
