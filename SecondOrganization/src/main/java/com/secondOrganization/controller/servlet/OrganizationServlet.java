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

@WebServlet(urlPatterns = "/organization.do")
@Slf4j
public class OrganizationServlet extends HttpServlet {

    @Inject
    private OrganizationServiceImpl service;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.getSession().setAttribute("organizationList", service.findAll());
            req.getRequestDispatcher("/jsp/organization.jsp").forward(req, resp);
        } catch (Exception e) {
            log.error("Error in doGet: {}", e.getMessage(), e);
            throw new ServletException("Cannot load organizations", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String name = req.getParameter("name");
            String orgType = req.getParameter("type");

            Organization organization = Organization.builder()
                    .name(name)
                    .organizationType(orgType)
                    .deleted(false)
                    .build();

            service.save(organization);
            log.info("Organization saved: {}", name);

            resp.sendRedirect(req.getContextPath() + "/organization.do");

        } catch (Exception e) {
            log.error("Error in doPost: {}", e.getMessage(), e);
            req.setAttribute("error", "خطا در ذخیره سازمان: " + e.getMessage());
            req.getRequestDispatcher("/jsp/organization.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            long id = Long.parseLong(req.getParameter("id"));
            String name = req.getParameter("name");
            String orgType = req.getParameter("type");

            Organization organization = Organization.builder()
                    .id(id)
                    .name(name)
                    .organizationType(orgType)
                    .build();

            service.edit(organization);
            log.info("Organization updated: {}", id);
            resp.sendRedirect(req.getContextPath() + "/organization.do");

        } catch (Exception e) {
            log.error("Error in doPut: {}", e.getMessage(), e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json; charset=UTF-8");
        try {
            String idParam = req.getParameter("id");
            if (idParam == null || idParam.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\": \"Missing ID parameter\"}");
                return;
            }

            long id = Long.parseLong(idParam);

            var orgOpt = service.findById(id);
            if (orgOpt.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("{\"error\": \"Organization with ID " + id + " not found\"}");
                return;
            }

            service.removeById(id);
            log.info("Organization deleted: {}", id);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("{\"message\": \"Organization deleted successfully\"}");

        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"Invalid ID format\"}");
        } catch (Exception e) {
            log.error("Error in doDelete: {}", e.getMessage(), e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}