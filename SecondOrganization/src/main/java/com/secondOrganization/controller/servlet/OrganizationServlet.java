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
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/organization.do")
@Slf4j
public class OrganizationServlet extends HttpServlet {

    @Inject
    private OrganizationServiceImpl service;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("_method");

        if ("delete".equalsIgnoreCase(method)) {
            doDelete(req, resp);
            return;
        }

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
            String searchName = req.getParameter("searchName");
            String searchType = req.getParameter("searchType");

            List<Organization> list;

            if ((searchName != null && !searchName.trim().isEmpty()) ||
                    (searchType != null && !searchType.trim().isEmpty())) {

                log.info("Searching organizations - name: {}, type: {}", searchName, searchType);
                list = service.findAll();

                if (searchName != null && !searchName.trim().isEmpty()) {
                    final String nameLower = searchName.trim().toLowerCase();
                    list = list.stream()
                            .filter(o -> o.getName() != null &&
                                    o.getName().toLowerCase().contains(nameLower))
                            .collect(Collectors.toList());
                }

                if (searchType != null && !searchType.trim().isEmpty()) {
                    final String typeLower = searchType.trim().toLowerCase();
                    list = list.stream()
                            .filter(o -> o.getOrganizationType() != null &&
                                    o.getOrganizationType().toLowerCase().contains(typeLower))
                            .collect(Collectors.toList());
                }

                log.info("Found {} organizations matching search criteria", list.size());
            } else {
                log.info("Loading all organizations...");
                list = service.findAll();
                log.info("Loaded {} organizations", list.size());
            }

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
        try {
            String idParam = req.getParameter("id");
            if (idParam == null || idParam.isEmpty()) {
                log.error("Delete request without ID");
                resp.sendRedirect(req.getContextPath() + "/organization.do?error=noId");
                return;
            }

            long id = Long.parseLong(idParam);

            service.removeById(id);
            log.info("Organization soft deleted: {}", id);

            resp.sendRedirect(req.getContextPath() + "/organization.do?success=deleted");

        } catch (Exception e) {
            log.error("Error in doDelete: {}", e.getMessage(), e);
            resp.sendRedirect(req.getContextPath() + "/organization.do?error=deleteFailed");
        }
    }
}