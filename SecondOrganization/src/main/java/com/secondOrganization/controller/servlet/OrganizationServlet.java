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
import java.util.List;

@WebServlet(urlPatterns = "/organization.do")
@Slf4j
public class OrganizationServlet extends HttpServlet {

    @Inject
    private OrganizationServiceImpl service;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String name = req.getParameter("name");
            String orgType = req.getParameter("type");

            log.info("Saving organization: name={}, type={}", name, orgType);

            Organization organization = Organization.builder()
                    .name(name)
                    .organizationType(orgType)
                    .deleted(false)
                    .build();

            service.save(organization);
            log.info("Organization saved successfully with ID: {}", organization.getId());

            resp.sendRedirect(req.getContextPath() + "/organization.do");

        } catch (Exception e) {
            log.error("Error in doPost: {}", e.getMessage(), e);
            req.setAttribute("error", "خطا در ذخیره: " + e.getMessage());

            try {
                req.setAttribute("organizationList", service.findAll());
            } catch (Exception ex) {
                log.error("Error loading list after save failure", ex);
            }
            req.getRequestDispatcher("/jsp/organization.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            log.info("Loading organizations...");
            List<Organization> list = service.findAll();
            log.info("Loaded {} organizations", list.size());

            req.setAttribute("organizationList", list);
            req.getRequestDispatcher("/jsp/organization.jsp").forward(req, resp);
        } catch (Exception e) {
            log.error("Error in doGet: {}", e.getMessage(), e);
            req.setAttribute("error", "خطا در بارگذاری لیست: " + e.getMessage());
            req.getRequestDispatcher("/jsp/organization.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json; charset=UTF-8");
        try {
            String idParam = req.getParameter("id");
            if (idParam == null || idParam.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\": \"ID not provided\"}");
                return;
            }

            long id = Long.parseLong(idParam);
            service.removeById(id);
            log.info("Organization deleted: {}", id);

            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("{\"message\": \"Deleted successfully\"}");

        } catch (Exception e) {
            log.error("Error in doDelete: {}", e.getMessage(), e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}