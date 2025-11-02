package com.secondOrganization.controller.servlet;

import com.secondOrganization.model.entity.*;
import com.secondOrganization.model.entity.enums.Gender;
import com.secondOrganization.service.*;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@WebServlet(urlPatterns = {"/login", "/login.do"})
public class LoginServlet extends HttpServlet {

    @Inject
    private UserService userService;

    @Inject
    private RoleService roleService;

    @Inject
    private PersonService personService;

    @Inject
    private OrganizationService organizationService;

    @Inject
    private DepartmentService departmentService;

    @Inject
    private BranchService branchService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("LoginServlet - GET {}", req.getRequestURI());

        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("username") != null) {
            resp.sendRedirect(req.getContextPath() + "/dashboard.jsp");
            return;
        }

        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    public void init() throws ServletException {
        super.init();
        log.info("LoginServlet - Initializing sample data");

        try {
            String orgName = "مجتمع فنی تهران";
            Optional<Organization> existingOrg = organizationService.findByName(orgName);
            Organization organization;

            if (existingOrg.isPresent()) {
                organization = existingOrg.get();
                log.info("Sample organization exists: {}", organization.getName());
            } else {
                organization = Organization.builder()
                        .name(orgName)
                        .organizationType("آموزشی")
                        .deleted(false)
                        .build();
                organizationService.save(organization);
                log.info("Created sample organization: {}", organization.getName());
            }

            Branch mainBranch = createBranchIfNotExists(
                    "شعبه مرکزی",
                    "تهران",
                    "خیابان آزادی",
                    "مدیر مرکزی",
                    organization
            );

            Department itDept = createDepartmentIfNotExists(
                    "فناوری اطلاعات",
                    "آموزشی",
                    1_000_000.0,
                    organization,
                    mainBranch
            );

            Department eeDept = createDepartmentIfNotExists(
                    "برق و الکترونیک",
                    "آموزشی",
                    800_000.0,
                    organization,
                    mainBranch
            );

            User adminUser = createUserIfNotExists("admin", "admin123");
            createRoleIfNotExists(adminUser, "admin");
            createPersonIfNotExists(adminUser, "مدیر", "سیستم", "0000000000", Gender.male);

            User managerUser = createUserIfNotExists("manager", "manager123");
            createRoleIfNotExists(managerUser, "manager");
            createPersonIfNotExists(managerUser, "مدیر", "واحد", "1111111111", Gender.female);

            User basicUser = createUserIfNotExists("user", "user123");
            createRoleIfNotExists(basicUser, "user");
            createPersonIfNotExists(basicUser, "کاربر", "عادی", "2222222222", Gender.male);

            log.info(" Sample data initialization completed successfully");

        } catch (Exception e) {
            log.error(" Error during sample data initialization: {}", e.getMessage(), e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        log.info("Login attempt for user: {}", username);

        try {
            if (username == null || username.trim().isEmpty() ||
                    password == null || password.trim().isEmpty()) {
                req.setAttribute("loginError", "نام کاربری و رمز عبور نمی‌تواند خالی باشد");
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
                return;
            }

            Optional<User> userOpt = userService.findByUsernameAndPassword(username, password);

            if (userOpt.isPresent()) {
                User user = userOpt.get();

                if (!user.isActive()) {
                    req.setAttribute("loginError", "حساب کاربری شما غیرفعال است");
                    req.getRequestDispatcher("/login.jsp").forward(req, resp);
                    return;
                }

                List<Role> roles = roleService.findByUser(username);
                String userRole = roles.isEmpty() ? "user" : roles.get(0).getRole();

                HttpSession session = req.getSession(true);
                session.setAttribute("username", username);
                session.setAttribute("userId", user.getId());
                session.setAttribute("userRole", userRole);
                session.setAttribute("isAdmin", "admin".equals(userRole));
                session.setAttribute("isManager", "manager".equals(userRole));

                Optional<Person> personOpt = personService.findByUsername(username);
                personOpt.ifPresent(person -> {
                    session.setAttribute("personName", person.getName() + " " + person.getFamily());
                    session.setAttribute("personId", person.getId());
                });

                log.info(" User {} logged in successfully with role: {}", username, userRole);
                resp.sendRedirect(req.getContextPath() + "/dashboard.jsp");

            } else {
                log.warn(" Failed login attempt for user: {}", username);
                req.setAttribute("loginError", "نام کاربری یا رمز عبور اشتباه است");
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
            }

        } catch (Exception e) {
            log.error(" Login error for user {}: {}", username, e.getMessage(), e);
            req.setAttribute("loginError", "خطا در ورود به سیستم");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }


    private User createUserIfNotExists(String username, String password) throws Exception {
        Optional<User> userOpt = userService.findByUsername(username);

        if (userOpt.isPresent()) {
            log.info("✓ User {} already exists", username);
            return userOpt.get();
        }

        User user = User.builder()
                .username(username)
                .password(password)
                .active(true)
                .deleted(false)
                .build();
        userService.save(user);
        log.info("✓ Created user: {}", username);
        return user;
    }

    private void createRoleIfNotExists(User user, String roleName) throws Exception {
        List<Role> existingRoles = roleService.findByUsernameAndRoleName(user.getUsername(), roleName);

        if (existingRoles.isEmpty()) {
            Role role = Role.builder()
                    .role(roleName)
                    .user(user)
                    .deleted(false)
                    .build();
            roleService.save(role);
            log.info("✓ Created role {} for user {}", roleName, user.getUsername());
        }
    }

    private void createPersonIfNotExists(User user, String name, String family,
                                         String nationalCode, Gender gender) throws Exception {
        Optional<Person> personOpt = personService.findByUsername(user.getUsername());

        if (personOpt.isEmpty()) {
            Person person = Person.builder()
                    .name(name)
                    .family(family)
                    .nationalCode(nationalCode)
                    .gender(gender)
                    .salary(5000000.0)
                    .birthdate(LocalDate.of(1990, 1, 1))
                    .user(user)
                    .deleted(false)
                    .build();
            personService.save(person);
            log.info("✓ Created person for user {}", user.getUsername());
        }
    }

    private Branch createBranchIfNotExists(String name, String city, String address,
                                           String manager, Organization org) throws Exception {
        List<Branch> branches = branchService.findByOrganizationId(org.getId());

        for (Branch b : branches) {
            if (b.getName().equals(name)) {
                log.info("✓ Branch {} already exists", name);
                return b;
            }
        }

        Branch branch = Branch.builder()
                .name(name)
                .city(city)
                .address(address)
                .manager(manager)
                .organization(org)
                .deleted(false)
                .build();
        branchService.save(branch);
        log.info("✓ Created branch: {}", name);
        return branch;
    }

    private Department createDepartmentIfNotExists(String name, String field,
                                                   Double budget, Organization org,
                                                   Branch branch) throws Exception {
        Optional<Department> deptOpt = departmentService.findByName(name);

        if (deptOpt.isPresent()) {
            log.info("✓ Department {} already exists", name);
            return deptOpt.get();
        }

        Department dept = Department.builder()
                .name(name)
                .field(field)
                .budget(budget)
                .organization(org)
                .branch(branch)
                .deleted(false)
                .build();
        departmentService.save(dept);
        log.info("✓ Created department: {}", name);
        return dept;
    }
}