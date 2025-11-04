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

            log.info("Creating new branch - Name: {}, Organization ID: {}", name, orgId);

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

                resp.sendRedirect(req.getContextPath() + "/branch.do?success=true");

            } else {
                log.error("Organization not found with id: {}", orgId);
                req.setAttribute("error", "سازمان مورد نظر یافت نشد!");
                doGet(req, resp);
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
            // مدیریت پیام موفقیت
            String success = req.getParameter("success");
            if ("true".equals(success)) {
                req.setAttribute("success", "شعبه با موفقیت ذخیره شد");
            }

            // قرار دادن داده‌ها در request به جای session
            req.setAttribute("branchList", branchService.findAll());
            req.setAttribute("organizationList", organizationService.findAll());

            log.info("Loaded {} branches and {} organizations for display",
                    req.getAttribute("branchList") != null ? ((java.util.List<?>)req.getAttribute("branchList")).size() : 0,
                    req.getAttribute("organizationList") != null ? ((java.util.List<?>)req.getAttribute("organizationList")).size() : 0);

            req.getRequestDispatcher("/jsp/branch.jsp").forward(req, resp);

        } catch (Exception e) {
            log.error("Error in BranchServlet.doGet", e);
            req.setAttribute("error", "خطا در بارگذاری داده‌ها: " + e.getMessage());
            req.getRequestDispatcher("/jsp/error.jsp").forward(req, resp);
        }
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            long id = Long.parseLong(req.getParameter("id"));
            branchService.removeById(id);

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write("{\"success\": true, \"message\": \"شعبه با موفقیت حذف شد\"}");

        } catch (Exception e) {
            log.error("Error deleting branch", e);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write("{\"success\": false, \"message\": \"خطا در حذف شعبه: " + e.getMessage() + "\"}");
        }
    }
}
