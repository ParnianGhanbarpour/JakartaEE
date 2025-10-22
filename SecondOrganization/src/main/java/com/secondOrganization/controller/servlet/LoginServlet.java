package com.secondOrganization.controller.servlet;

import com.secondOrganization.model.entity.*;
import com.secondOrganization.model.entity.enums.Gender;
import com.secondOrganization.service.*;
import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;


@Slf4j
@WebServlet(urlPatterns = {"/login", "/login.do"})
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Inject
    private OrganizationService organizationService;

    @Inject
    private DepartmentService departmentService;

    @Inject
    private UserService userService;

    @Inject
    private RoleService roleService;

    @Inject
    private PersonService personService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("LoginServlet - GET {}", req.getRequestURI());
        try {
            if (req.getUserPrincipal() != null) {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/home.do");
                dispatcher.forward(req, resp);
                return;
            }
            // show login page
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        } catch (Exception e) {
            log.error("Error in GET /login: {}", e.getMessage(), e);
            throw new ServletException(e);
        }
    }

    @Override
    public void init() throws ServletException {
        super.init();
        log.info("LoginServlet - init: preparing sample data");

        try {
            // avoid creating duplicates: check organization by name
            String orgName = "مجتمع فنی تهران";
            Optional<Organization> existingOrg = organizationService.findByName(orgName);
            Organization organization;
            if (existingOrg.isPresent()) {
                organization = existingOrg.get();
                log.info("Sample organization already exists: {}", organization.getName());
            } else {
                organization = Organization.builder()
                        .name(orgName)
                        .organizationType("آموزشی")
                        .build();
                organizationService.save(organization);
                log.info("Created sample organization: {}", organization);
            }

            // Create departments if not present (by name)
            Department itDept;
            Optional<Department> maybeIt = departmentService.findByTitle("فناوری اطلاعات و ارتباطات");
            if (maybeIt.isPresent()) {
                itDept = maybeIt.get();
            } else {
                itDept = Department.builder()
                        .name("فناوری اطلاعات و ارتباطات")
                        .field("آموزشی")
                        .budget(1_000_000.0)
                        .organization(organization)
                        .build();
                departmentService.save(itDept);
                log.info("Created department: {}", itDept);
            }

            Department eeDept;
            Optional<Department> maybeEe = departmentService.findByTitle("برق و الکترونیک");
            if (maybeEe.isPresent()) {
                eeDept = maybeEe.get();
            } else {
                eeDept = Department.builder()
                        .name("برق و الکترونیک")
                        .field("آموزشی")
                        .budget(800_000.0)
                        .organization(organization)
                        .build();
                departmentService.save(eeDept);
                log.info("Created department: {}", eeDept);
            }

            OrganizationGroup itGroup;
            Optional<OrganizationGroup> maybeGroup = organizationService.findById(organization.getId())
                    .flatMap(org -> {
                        try {
                            // try find group by name through OrganizationGroupService absence -> fallback: create new
                            return java.util.Optional.empty();
                        } catch (Exception ex) {
                            return java.util.Optional.empty();
                        }
                    });
            itGroup = OrganizationGroup.builder()
                    .name("گروه توسعه")
                    .specialty("نرم‌افزار")
                    .department(itDept)
                    .build();
            try {
                organizationService.findById(organization.getId());
                itDept.addGroup(itGroup);
                departmentService.edit(itDept);
                log.info("Created organization group: {} under dept {}", itGroup.getName(), itDept.getName());
            } catch (Exception e) {
                log.warn("Could not save organization group via department cascade: {}", e.getMessage());
                try {

                } catch (Exception ex) {
                    log.warn("Fallback error while attempting to persist organization group: {}", ex.getMessage());
                }
            }

            Optional<com.secondOrganization.model.entity.User> maybeAdmin = userService.findByUsername("admin");
            com.secondOrganization.model.entity.User adminUser;
            if (maybeAdmin.isPresent()) {
                adminUser = maybeAdmin.get();
            } else {
                adminUser = com.secondOrganization.model.entity.User.builder()
                        .username("admin")
                        .password("admin123")
                        .active(true)
                        .build();
                userService.save(adminUser);
                log.info("Created admin user: {}", adminUser.getUsername());
            }

            Optional<com.secondOrganization.model.entity.User> maybeUser = userService.findByUsername("user");
            com.secondOrganization.model.entity.User basicUser;
            if (maybeUser.isPresent()) {
                basicUser = maybeUser.get();
            } else {
                basicUser = com.secondOrganization.model.entity.User.builder()
                        .username("user")
                        .password("user123")
                        .active(true)
                        .build();
                userService.save(basicUser);
                log.info("Created user: {}", basicUser.getUsername());
            }


            boolean adminRoleExists = roleService.findByRoleName("admin").stream().anyMatch(r -> r.getRole().equals("admin"));
            if (!adminRoleExists) {
                Role adminRole = Role.builder()
                        .role("admin")
                        .user(adminUser)
                        .build();
                roleService.save(adminRole);
                // add role to user if the user object has addRole method for entity Role (if not, ignore)
                log.info("Created admin role for user {}", adminUser.getUsername());
            }

            boolean userRoleExists = roleService.findByRoleName("user").stream().anyMatch(r -> r.getRole().equals("user"));
            if (!userRoleExists) {
                Role userRole = Role.builder()
                        .role("user")
                        .user(basicUser)
                        .build();
                roleService.save(userRole);
                log.info("Created user role for user {}", basicUser.getUsername());
            }

            Optional<Person> maybePerson1 = personService.findByUsername(adminUser.getUsername());
            if (!maybePerson1.isPresent()) {
                Person person1 = Person.builder()
                        .name("Parnian")
                        .family("Qan")
                        .gender(Gender.female)
                        .nationalCode("0000000000")
                        .salary(1000000.0)
                        .user(adminUser)
                        .organizationGroup(itGroup) // may be null if group wasn't persisted; if null, builder will set null - ensure DB constraints
                        .build();
                personService.save(person1);
                log.info("Created sample person: {} linked to user {}", person1.getName(), adminUser.getUsername());
            }

            Optional<Person> maybePerson2 = personService.findByUsername(basicUser.getUsername());
            if (!maybePerson2.isPresent()) {
                Person person2 = Person.builder()
                        .name("Ali")
                        .family("Mohamadi")
                        .gender(Gender.male)
                        .nationalCode("2222222222")
                        .salary(900000.0)
                        .user(basicUser)
                        .organizationGroup(itGroup)
                        .build();
                personService.save(person2);
                log.info("Created sample person: {} linked to user {}", person2.getName(), basicUser.getUsername());
            }

        } catch (Exception e) {
            log.error("Error during sample data init: {}", e.getMessage(), e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        try {
            Optional<com.secondOrganization.model.entity.User> auth = userService.findByUsernameAndPassword(username, password);
            if (auth.isPresent()) {
                HttpSession session = req.getSession(true);
                session.setAttribute("username", username);
                session.setAttribute("userId", auth.get().getId());
                // redirect to home
                resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath() + "/home.do"));
            } else {
                req.setAttribute("loginError", "نام کاربری یا رمز عبور اشتباه است");
                // forward back to login page with error
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            log.error("Login error for user {}: {}", username, e.getMessage(), e);
            req.setAttribute("loginError", "خطا در ورود به سیستم");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }
}