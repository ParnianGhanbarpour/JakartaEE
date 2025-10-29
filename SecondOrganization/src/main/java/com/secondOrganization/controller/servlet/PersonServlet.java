package com.secondOrganization.controller.servlet;

import com.secondOrganization.controller.validation.BeanValidator;
import com.secondOrganization.model.entity.Person;
import com.secondOrganization.model.entity.User;
import com.secondOrganization.model.entity.enums.Gender;
import com.secondOrganization.service.impl.PersonServiceImpl;
import com.secondOrganization.service.impl.RoleServiceImpl;
import com.secondOrganization.service.impl.UserServiceImpl;
import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Slf4j
@WebServlet(urlPatterns = "/person.do")
public class PersonServlet extends HttpServlet {

    @Inject
    private UserServiceImpl userService;

    @Inject
    private RoleServiceImpl rolesService;

    @Inject
    private PersonServiceImpl personService;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("PersonServlet - Get");
        try {
            String username = req.getUserPrincipal() != null ? req.getUserPrincipal().getName() : null;

            if (username != null && personService.findByUsername(username).isPresent()) {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/profile.do");
                dispatcher.forward(req, resp);
                return;
            }

            req.getSession().setAttribute("principalUser", username);
            req.getSession().setAttribute("genders", Arrays.asList(Gender.values()));
            req.getRequestDispatcher("/jsp/person.jsp").forward(req, resp);

        } catch (Exception e) {
            log.error("Error in doGet: {}", e.getMessage(), e);
            throw new ServletException("Cannot load person form", e);
        }
    }

    @Valid
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("PersonServlet - post");

        try {
            String name = req.getParameter("name");
            String family = req.getParameter("family");
            String nationalCode = req.getParameter("nationalCode");
            String gender = req.getParameter("gender");

            String username = req.getUserPrincipal() != null ? req.getUserPrincipal().getName() : null;

            if (username != null) {
                Optional<User> user = userService.findByUsername(username);
                if (user.isPresent()) {
                    Person person = Person.builder()
                            .name(name)
                            .family(family)
                            .nationalCode(nationalCode)
                            .gender(Gender.valueOf(gender))
                            .user(user.get())
                            .deleted(false)
                            .build();

                    // Validate
                    BeanValidator<Person> validator = new BeanValidator<>();
                    var errors = validator.validate(person);

                    if (!errors.isEmpty()) {
                        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        resp.getWriter().write(errors.toString());
                        return;
                    }

                    personService.save(person);
                    log.info("Person saved: {} {}", name, family);
                    resp.sendRedirect(req.getContextPath() + "/profile.do");

                } else {
                    log.warn("User not found: {}", username);
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
                }
            } else {
                log.warn("No authenticated user");
                resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Please login first");
            }

        } catch (Exception e) {
            log.error("Error in doPost: {}", e.getMessage(), e);
            throw new ServletException("Cannot save person", e);
        }
    }
}