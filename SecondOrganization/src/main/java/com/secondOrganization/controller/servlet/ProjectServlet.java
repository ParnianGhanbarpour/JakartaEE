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
            log.info("Project created: {}", project);
            resp.sendRedirect("/jsp/project.jsp");

        } catch (Exception e) {
            log.error("Error saving project", e);
            resp.sendRedirect("/error.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.getSession().setAttribute("projectList", projectService.findAll());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        req.getRequestDispatcher("/jsp/project.jsp").forward(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            long id = Long.parseLong(req.getParameter("id"));
            projectService.removeById(id);
            resp.sendRedirect("/jsp/project.jsp");
        } catch (Exception e) {
            log.error("Error deleting project", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
