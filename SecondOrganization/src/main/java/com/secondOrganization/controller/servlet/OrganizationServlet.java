package com.secondOrganization.controller.servlet;


import com.secondOrganization.model.entity.Organization;
import com.secondOrganization.service.impl.OrganizationServiceImpl;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@WebServlet(urlPatterns = "/organisation.do")
@Slf4j
public class OrganizationServlet extends HttpServlet {
    @Inject
    private OrganizationServiceImpl service;

    @Inject
    private Organization organization;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.getSession().setAttribute("organisationList", service.findAll());
            req.getRequestDispatcher("/jsp/organisation.jsp").forward(req, resp);
            service.findAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            String name = req.getParameter("name");
            String orgType = req.getParameter("type");

            organization = Organization
                    .builder()
                    .name(name)
                    .organizationType(orgType)
                    .build();

            service.save(organization);
            log.info("Organization Save");
            resp.sendRedirect("/person.do");
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            long id = Long.parseLong(req.getParameter("id"));
            String name = req.getParameter("name");
            String orgType = req.getParameter("type");

            organization = Organization
                    .builder()
                    .name(name)
                    .organizationType(orgType)
                    .build();

           service.edit(organization);
            resp.sendRedirect("/organization.jsp");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=UTF-8");
        try {
            String idParam = req.getParameter("id");
            if (idParam == null || idParam.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\": \"Missing ID parameter\"}");
                return;
            }

            long id = Long.parseLong(idParam);

            var departmentOpt = service.findById(id);
            if (departmentOpt == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("{\"error\": \"Department with ID " + id + " not found\"}");
                return;
            }

            service.removeById(id);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("{\"message\": \"Department deleted successfully\"}");

        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"Invalid ID format\"}");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
            e.printStackTrace();
        }
    }

}



