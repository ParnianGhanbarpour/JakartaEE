package com.secondOrganization.controller.servlet;

import com.secondOrganization.model.entity.Person;
import com.secondOrganization.model.entity.Project;
import com.secondOrganization.model.entity.ProjectAssignment;
import com.secondOrganization.service.impl.PersonServiceImpl;
import com.secondOrganization.service.impl.ProjectAssignmentServiceImpl;
import com.secondOrganization.service.impl.ProjectServiceImpl;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@WebServlet(urlPatterns = "/projectAssignment.do")
public class ProjectAssignmentServlet extends HttpServlet {

    @Inject
    private ProjectAssignmentServiceImpl assignmentService;

    @Inject
    private ProjectServiceImpl projectService;

    @Inject
    private PersonServiceImpl personService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            long projectId = Long.parseLong(req.getParameter("projectId"));
            long personId = Long.parseLong(req.getParameter("personId"));

            Project project = projectService.findById(projectId)
                    .orElseThrow(() -> new IllegalArgumentException("Project not found with id: " + projectId));

            Person person = personService.findById(personId)
                    .orElseThrow(() -> new IllegalArgumentException("Person not found with id: " + personId));

            ProjectAssignment assignment = ProjectAssignment.builder()
                    .project(project)
                    .person(person)
                    .deleted(false)
                    .build();

            assignmentService.save(assignment);
            log.info("Assignment created: {}", assignment);
            resp.sendRedirect("/jsp/projectAssignment.jsp");

        } catch (Exception e) {
            log.error("Error saving assignment", e);
            resp.sendRedirect("/error.jsp");
        }
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.getSession().setAttribute("assignmentList", assignmentService.findAll());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            req.getSession().setAttribute("projectList", projectService.findAll());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            req.getSession().setAttribute("personList", personService.findAll());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        req.getRequestDispatcher("/jsp/projectAssignment.jsp").forward(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            long id = Long.parseLong(req.getParameter("id"));
            assignmentService.removeById(id);
            resp.sendRedirect("/jsp/projectAssignment.jsp");
        } catch (Exception e) {
            log.error("Error deleting assignment", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
