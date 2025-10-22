package com.secondOrganization.controller.servlet;

import com.secondOrganization.model.entity.Branch;
import com.secondOrganization.model.entity.Organization;
import com.secondOrganization.service.impl.BranchServiceImpl;
import com.secondOrganization.service.impl.OrganizationServiceImpl;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@WebServlet(urlPatterns = "/branch.do")
public class BranchServlet extends HttpServlet {

    @Inject
    private BranchServiceImpl branchService;

    @Inject
    private OrganizationServiceImpl organizationService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String name = req.getParameter("name");
            String address = req.getParameter("address");
            String city = req.getParameter("city");
            String manager = req.getParameter("manager");
            long orgId = Long.parseLong(req.getParameter("organizationId"));

            Optional<Organization> optionalOrg = organizationService.findById(orgId);

            if (optionalOrg.isPresent()) {
                Organization organization = optionalOrg.get();

                Branch branch = Branch.builder()
                        .name(name)
                        .address(address)
                        .city(city)
                        .manager(manager)
                        .organization(organization)
                        .deleted(false)
                        .build();

                branchService.save(branch);
                log.info("Branch created: {}", branch);
                resp.sendRedirect("/jsp/branch.jsp");
            } else {
                log.error("Organization not found with id: {}", orgId);
                resp.sendRedirect("/error.jsp");
            }

        } catch (Exception e) {
            log.error("Error saving branch", e);
            resp.sendRedirect("/error.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.getSession().setAttribute("branchList", branchService.findAll());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            req.getSession().setAttribute("organizationList", organizationService.findAll());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        req.getRequestDispatcher("/jsp/branch.jsp").forward(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            long id = Long.parseLong(req.getParameter("id"));
            branchService.removeById((int) id);
            resp.sendRedirect("/jsp/branch.jsp");
        } catch (Exception e) {
            log.error("Error deleting branch", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
