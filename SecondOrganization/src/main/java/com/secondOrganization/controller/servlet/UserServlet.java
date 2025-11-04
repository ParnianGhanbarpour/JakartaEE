package com.secondOrganization.controller.servlet;

import com.secondOrganization.controller.validation.BeanValidator;
import com.secondOrganization.model.entity.Person;
import com.secondOrganization.model.entity.Role;
import com.secondOrganization.model.entity.User;
import com.secondOrganization.service.PersonService;
import com.secondOrganization.service.impl.RoleServiceImpl;
import com.secondOrganization.service.impl.UserServiceImpl;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@WebServlet(name = "UserServlet", urlPatterns = "/user.do")
public class UserServlet extends HttpServlet {

    @Inject
    private UserServiceImpl userService;

    @Inject
    private RoleServiceImpl rolesService;

    @Inject
    private PersonService personService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("UserServlet - Get");
        try {
            req.setAttribute("userList", userService.findAll());
            req.setAttribute("roleList", rolesService.findAll());
            req.getRequestDispatcher("/jsp/user-form.jsp").forward(req, resp);
        } catch (Exception e) {
            log.error("Error in doGet: {}", e.getMessage(), e);
            req.setAttribute("error", "خطا در بارگذاری داده‌ها: " + e.getMessage());
            req.getRequestDispatcher("/jsp/error.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("UserServlet - Post");
        try {
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            String name = req.getParameter("name");
            String family = req.getParameter("family");
            String nationalCode = req.getParameter("nationalCode");

            try {
                if (username == null || username.trim().isEmpty()) {
                    req.setAttribute("error", "نام کاربری اجباری است");
                    doGet(req, resp);
                    return;
                }

                if (password == null || password.trim().isEmpty()) {
                    req.setAttribute("error", "رمز عبور اجباری است");
                    doGet(req, resp);
                    return;
                }

                if (userService.findByUsername(username).isPresent()) {
                    req.setAttribute("error", "نام کاربری تکراری است");
                    doGet(req, resp);
                    return;
                }

                User user = User.builder()
                        .username(username.trim())
                        .password(password)
                        .active(true)
                        .deleted(false)
                        .build();

                BeanValidator<User> validator = new BeanValidator<>();
                var errors = validator.validate(user);

                if (!errors.isEmpty()) {
                    req.setAttribute("error", "داده‌های کاربر معتبر نیستند: " + errors);
                    doGet(req, resp);
                    return;
                }

                userService.save(user);
                log.info(" User created: {}", username);

                Person person = Person.builder()
                        .name(name != null ? name.trim() : username)
                        .family(family != null ? family.trim() : "نام خانوادگی")
                        .nationalCode(nationalCode != null ? nationalCode.trim() : null)
                        .user(user)
                        .deleted(false)
                        .build();

                personService.save(person);
                log.info(" Person created for user: {}", username);

                Role userRole = Role.builder()
                        .user(user)
                        .role("user")
                        .deleted(false)
                        .build();

                if (rolesService.findByUsernameAndRoleName(username, "user").isEmpty()) {
                    rolesService.save(userRole);
                    log.info(" User role created for: {}", username);
                }

                req.setAttribute("success", "کاربر با موفقیت ایجاد شد");
                resp.sendRedirect(req.getContextPath() + "/user.do?success=true");
            } catch (Exception e) {
                log.error(" Error in UserServlet.doPost: {}", e.getMessage(), e);

                try {
                    Optional<User> createdUser = userService.findByUsername(username);
                    if (createdUser.isPresent()) {
                        userService.remove(createdUser.get());
                        log.info(" Rollback: User deleted due to error");
                    }
                } catch (Exception rollbackEx) {
                    log.error(" Error during rollback: {}", rollbackEx.getMessage());
                }
                req.setAttribute("error", "خطا در ایجاد کاربر: " + e.getMessage());
                doGet(req, resp);
            }
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}