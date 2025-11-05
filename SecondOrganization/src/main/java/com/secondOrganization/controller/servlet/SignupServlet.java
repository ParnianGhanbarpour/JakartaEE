package com.secondOrganization.controller.servlet;

import com.secondOrganization.model.entity.OrganizationGroup;
import com.secondOrganization.model.entity.Person;
import com.secondOrganization.model.entity.Role;
import com.secondOrganization.model.entity.User;
import com.secondOrganization.model.entity.enums.Gender;
import com.secondOrganization.service.OrganizationGroupService;
import com.secondOrganization.service.PersonService;
import com.secondOrganization.service.RoleService;
import com.secondOrganization.service.UserService;
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
@WebServlet(urlPatterns = {"/signup", "/signup.do"})
public class SignupServlet extends HttpServlet {

    @Inject
    private UserService userService;

    @Inject
    private PersonService personService;

    @Inject
    private RoleService roleService;

    @Inject
    private OrganizationGroupService organizationGroupService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("SignupServlet - GET {}", req.getRequestURI());

        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("username") != null) {
            resp.sendRedirect(req.getContextPath() + "/dashboard.jsp");
            return;
        }

        req.getRequestDispatcher("/signup.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        String email = req.getParameter("email");
        String name = req.getParameter("name");
        String family = req.getParameter("family");
        String nationalCode = req.getParameter("nationalCode");
        String salaryStr = req.getParameter("salary");
        String birthdate = req.getParameter("birthdate");
        String gender = req.getParameter("gender");

        log.info("Signup attempt for user: {}", username);

        try {
            if (username == null || username.trim().isEmpty()) {
                req.setAttribute("signupError", "نام کاربری نمی‌تواند خالی باشد");
                req.getRequestDispatcher("/signup.jsp").forward(req, resp);
                return;
            }

            if (password == null || password.trim().isEmpty()) {
                req.setAttribute("signupError", "رمز عبور نمی‌تواند خالی باشد");
                req.getRequestDispatcher("/signup.jsp").forward(req, resp);
                return;
            }

            if (password.length() < 6) {
                req.setAttribute("signupError", "رمز عبور باید حداقل ۶ کاراکتر باشد");
                req.getRequestDispatcher("/signup.jsp").forward(req, resp);
                return;
            }

            if (!password.equals(confirmPassword)) {
                req.setAttribute("signupError", "رمز عبور و تأیید رمز عبور مطابقت ندارند");
                req.getRequestDispatcher("/signup.jsp").forward(req, resp);
                return;
            }

            Optional<User> existingUser = userService.findByUsername(username);
            if (existingUser.isPresent()) {
                log.warn(" Username already exists: {}", username);
                req.setAttribute("signupError", "نام کاربری قبلاً استفاده شده است");
                req.getRequestDispatcher("/signup.jsp").forward(req, resp);
                return;
            }

            if (nationalCode != null && !nationalCode.trim().isEmpty()) {
                Optional<Person> existingPerson = personService.findByNationalCode(nationalCode);
                if (existingPerson.isPresent()) {
                    log.warn(" National code already exists: {}", nationalCode);
                    req.setAttribute("signupError", "کد ملی قبلاً ثبت شده است");
                    req.getRequestDispatcher("/signup.jsp").forward(req, resp);
                    return;
                }
            }

            User newUser = User.builder()
                    .username(username.trim())
                    .password(password)
                    .active(true)
                    .deleted(false)
                    .build();

            userService.save(newUser);
            log.info(" User created successfully: {}", username);

            Role userRole = Role.builder()
                    .user(newUser)
                    .role("user")
                    .deleted(false)
                    .build();
            roleService.save(userRole);
            log.info(" Role 'user' assigned to: {}", username);

            OrganizationGroup defaultGroup = null;
            try {
                List<OrganizationGroup> groups = organizationGroupService.findByName("گروه پیش‌فرض");
                if (!groups.isEmpty()) {
                    defaultGroup = groups.get(0);
                    log.info(" Default group found: {}", defaultGroup.getName());
                }
            } catch (Exception e) {
                log.warn(" No default group found, continuing without it");
            }

            Double salary = null;
            if (salaryStr != null && !salaryStr.trim().isEmpty()) {
                try {
                    salary = Double.parseDouble(salaryStr.replace(",", ""));
                    log.debug(" Salary parsed: {}", salary);
                } catch (NumberFormatException e) {
                    log.warn("Invalid salary value: {}", salaryStr);
                }
            }

            LocalDate parsedBirthdate = null;
            if (birthdate != null && !birthdate.trim().isEmpty()) {
                try {
                    parsedBirthdate = LocalDate.parse(birthdate);
                    log.debug(" Birthdate parsed: {}", parsedBirthdate);
                } catch (Exception e) {
                    log.warn("Invalid birthdate: {}", birthdate);
                }
            }

            Gender personGender = Gender.male;
            if (gender != null && !gender.trim().isEmpty()) {
                try {
                    personGender = Gender.valueOf(gender);
                    log.debug(" Gender set to: {}", personGender);
                } catch (IllegalArgumentException e) {
                    log.warn(" Invalid gender: {}, using default (male)", gender);
                }
            }

            Person newPerson = Person.builder()
                    .name(name != null && !name.trim().isEmpty() ? name.trim() : username)
                    .family(family != null && !family.trim().isEmpty() ? family.trim() : "کاربر")
                    .nationalCode(nationalCode != null && !nationalCode.trim().isEmpty() ? nationalCode.trim() : null)
                    .salary(salary)
                    .birthdate(parsedBirthdate)
                    .gender(personGender)
                    .user(newUser)
                    .organizationGroup(defaultGroup)
                    .deleted(false)
                    .build();

            personService.save(newPerson);
            log.info(" Person created successfully for user: {}", username);

            log.info(" Signup completed successfully for user: {}", username);
            req.setAttribute("successMessage", "ثبت‌نام با موفقیت انجام شد. اکنون می‌توانید وارد شوید.");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);

        } catch (Exception e) {
            log.error(" Signup error for user {}: {}", username, e.getMessage(), e);
            e.printStackTrace();

            try {
                Optional<User> createdUser = userService.findByUsername(username);
                if (createdUser.isPresent()) {
                    userService.remove(createdUser.get());
                    log.info(" Rollback: User deleted due to error");
                }
            } catch (Exception rollbackEx) {
                log.error(" Error during rollback: {}", rollbackEx.getMessage());
            }

            String errorMessage = "خطا در ثبت‌نام: " + e.getMessage();
            if (e.getCause() != null) {
                errorMessage += "\nعلت: " + e.getCause().getMessage();
            }

            req.setAttribute("signupError", errorMessage);
            req.getRequestDispatcher("/signup.jsp").forward(req, resp);
        }
    }

    @Override
    public void init() throws ServletException {
        super.init();
        log.info(" SignupServlet initialized successfully");
    }
}