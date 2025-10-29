package com.secondOrganization.controller.servlet;

import com.secondOrganization.controller.validation.BeanValidator;
import com.secondOrganization.model.entity.Role;
import com.secondOrganization.model.entity.User;
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

@Slf4j
@WebServlet(name = "UserServlet", urlPatterns = "/user.do")
public class UserServlet extends HttpServlet {

    @Inject
    private UserServiceImpl userService;

    @Inject
    private RoleServiceImpl rolesService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("UserServlet - Get");
        try {
            req.getSession().setAttribute("userList", rolesService.findAll());
            req.getRequestDispatcher("/jsp/user-form.jsp").forward(req, resp);
        } catch (Exception e) {
            log.error("Error in doGet: {}", e.getMessage(), e);
            throw new ServletException("Cannot load users", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("UserServlet - Post");
        try {
            String username = req.getParameter("username");
            String password = req.getParameter("password");

            User user = User.builder()
                    .username(username)
                    .password(password)
                    .active(true)
                    .deleted(false)
                    .build();

            // Validate user
            BeanValidator<User> validator = new BeanValidator<>();
            var errors = validator.validate(user);

            if (!errors.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write(errors.toString());
                return;
            }

            // Check for duplicate username
            if (userService.findByUsername(username).isEmpty()) {
                // Save user
                userService.save(user);
                log.info("User created: {}", username);

                // Create role for user
                Role userRole = Role.builder()
                        .user(user)
                        .role("user")
                        .deleted(false)
                        .build();

                if (rolesService.findByUsernameAndRoleName(username, "user").isEmpty()) {
                    rolesService.save(userRole);
                    log.info("User role created for: {}", username);
                }

                req.getSession().removeAttribute("duplicateUsername");
                resp.sendRedirect(req.getContextPath() + "/user.do");

            } else {
                log.warn("Duplicate username attempt: {}", username);
                String errorMessage = "نام کاربری تکراری است!";
                req.getSession().setAttribute("duplicateUsername", errorMessage);
                resp.sendRedirect(req.getContextPath() + "/user.do");
            }

        } catch (Exception e) {
            log.error("Error in doPost: {}", e.getMessage(), e);
            throw new ServletException("Cannot save user", e);
        }
    }
}