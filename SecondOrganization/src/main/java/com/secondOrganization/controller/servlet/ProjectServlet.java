package com.secondOrganization.controller.servlet;

import com.secondOrganization.model.entity.Person;
import com.secondOrganization.model.entity.Project;
import com.secondOrganization.model.entity.enums.ProjectStatus;
import com.secondOrganization.service.impl.PersonServiceImpl;
import com.secondOrganization.service.impl.ProjectServiceImpl;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@WebServlet(urlPatterns = "/project.do")
public class ProjectServlet extends HttpServlet {

    @Inject
    private ProjectServiceImpl projectService;

    @Inject
    private PersonServiceImpl personService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("_method");
        String action = req.getParameter("action");

        if ("delete".equalsIgnoreCase(method)) {
            doDelete(req, resp);
            return;
        }

        if ("addMembers".equalsIgnoreCase(action)) {
            addMembersToProject(req, resp);
            return;
        }

        if ("removeMember".equalsIgnoreCase(action)) {
            removeMemberFromProject(req, resp);
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
            log.info("Project soft deleted: {}", id);

            resp.sendRedirect(req.getContextPath() + "/project.do");
        } catch (Exception e) {
            log.error("Error deleting project", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private void addMembersToProject(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            long projectId = Long.parseLong(req.getParameter("projectId"));
            String[] personIds = req.getParameterValues("personIds");

            if (personIds == null || personIds.length == 0) {
                log.warn("No persons selected for project {}", projectId);
                resp.sendRedirect(req.getContextPath() + "/project.do");
                return;
            }

            Optional<Project> projectOpt = projectService.findById(projectId);
            if (projectOpt.isEmpty()) {
                log.error("Project not found: {}", projectId);
                resp.sendRedirect(req.getContextPath() + "/project.do?error=projectNotFound");
                return;
            }

            Project project = projectOpt.get();
            int addedCount = 0;

            for (String personIdStr : personIds) {
                try {
                    long personId = Long.parseLong(personIdStr);
                    Optional<Person> personOpt = personService.findById(personId);

                    if (personOpt.isPresent()) {
                        Person person = personOpt.get();

                        // Add person to project
                        project.addPerson(person);
                        addedCount++;

                        log.info("Added person {} to project {}", person.getName(), project.getTitle());
                    }
                } catch (Exception e) {
                    log.error("Error adding person {} to project", personIdStr, e);
                }
            }

            projectService.edit(project);

            log.info("Added {} members to project {}", addedCount, project.getTitle());
            resp.sendRedirect(req.getContextPath() + "/project.do?success=membersAdded");

        } catch (Exception e) {
            log.error("Error adding members to project", e);
            resp.sendRedirect(req.getContextPath() + "/project.do?error=addMembersFailed");
        }
    }

    private void removeMemberFromProject(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            long projectId = Long.parseLong(req.getParameter("projectId"));
            long personId = Long.parseLong(req.getParameter("personId"));

            Optional<Project> projectOpt = projectService.findById(projectId);
            Optional<Person> personOpt = personService.findById(personId);

            if (projectOpt.isEmpty() || personOpt.isEmpty()) {
                log.error("Project or Person not found - Project: {}, Person: {}", projectId, personId);
                resp.sendRedirect(req.getContextPath() + "/project.do?error=notFound");
                return;
            }

            Project project = projectOpt.get();
            Person person = personOpt.get();

            project.removePerson(person);
            projectService.edit(project);

            log.info("Removed person {} from project {}", person.getName(), project.getTitle());
            resp.sendRedirect(req.getContextPath() + "/project.do?success=memberRemoved");

        } catch (Exception e) {
            log.error("Error removing member from project", e);
            resp.sendRedirect(req.getContextPath() + "/project.do?error=removeMemberFailed");
        }
    }

    private void loadDataAndForward(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            List<Project> projects = projectService.findAll();
            req.setAttribute("projectList", projects);

            List<Person> persons = personService.findAll();
            req.setAttribute("personList", persons);

            log.info("Loaded {} projects and {} persons", projects.size(), persons.size());

            req.getRequestDispatcher("/jsp/project.jsp").forward(req, resp);
        } catch (Exception e) {
            log.error("Error loading projects", e);
            throw new ServletException("Cannot load projects", e);
        }
    }
}