package com.secondOrganization.controller.servlet;

import com.secondOrganization.model.entity.User;
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
import java.util.Optional;

@Slf4j
@WebServlet(urlPatterns = {"/signup", "/signup.do"})
public class SignupServlet extends HttpServlet {

    @Inject
    private UserService userService;

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

        log.info("Signup attempt for user: {}", username);

        try {
            // اعتبارسنجی داده‌های ورودی
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
                req.setAttribute("signupError", "نام کاربری قبلاً استفاده شده است");
                req.getRequestDispatcher("/signup.jsp").forward(req, resp);
                return;
            }

            User newUser = User.builder()
                    .username(username.trim())
                    .password(password)
                    .active(true)
                    .deleted(false)
                    .build();

            userService.save(newUser);

            log.info("✓ New user registered successfully: {}", username);

            req.setAttribute("successMessage", "ثبت‌نام با موفقیت انجام شد. اکنون می‌توانید وارد شوید.");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);

        } catch (Exception e) {
            log.error("✗ Signup error for user {}: {}", username, e.getMessage(), e);
            req.setAttribute("signupError", "خطا در ثبت‌نام. لطفاً مجدداً تلاش کنید.");
            req.getRequestDispatcher("/signup.jsp").forward(req, resp);
        }
    }

    @Override
    public void init() throws ServletException {
        super.init();
        log.info("SignupServlet - Initialized");
    }
}