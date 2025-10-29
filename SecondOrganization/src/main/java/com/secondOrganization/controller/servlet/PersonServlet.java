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
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

@Slf4j
@WebServlet(urlPatterns = "/person.do")
public class PersonServlet extends HttpServlet {

    @Inject
    private PersonServiceImpl personService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("personList", personService.findAll());
            req.setAttribute("genders", Arrays.asList(Gender.values()));
            req.getRequestDispatcher("/jsp/person.jsp").forward(req, resp);
        } catch (Exception e) {
            log.error("Error loading persons", e);
            throw new ServletException("Cannot load persons", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("_method");

        if ("delete".equalsIgnoreCase(method)) {
            doDelete(req, resp);
            return;
        }

        try {
            String name = req.getParameter("name");
            String family = req.getParameter("family");
            String nationalCode = req.getParameter("nationalCode");
            Gender gender = Gender.valueOf(req.getParameter("gender"));

            String salaryStr = req.getParameter("salary");
            Double salary = (salaryStr != null && !salaryStr.isEmpty())
                    ? Double.parseDouble(salaryStr) : null;

            String birthdateStr = req.getParameter("birthdate");
            LocalDate birthdate = (birthdateStr != null && !birthdateStr.isEmpty())
                    ? LocalDate.parse(birthdateStr) : null;

            Person person = Person.builder()
                    .name(name)
                    .family(family)
                    .nationalCode(nationalCode)
                    .gender(gender)
                    .salary(salary)
                    .birthdate(birthdate)
                    .deleted(false)
                    .build();

            personService.save(person);
            log.info("Person saved: {} {}", name, family);

            resp.sendRedirect(req.getContextPath() + "/person.do");

        } catch (Exception e) {
            log.error("Error saving person", e);
            req.setAttribute("error", "خطا در ذخیره پرسنل: " + e.getMessage());
            loadDataAndForward(req, resp);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            long id = Long.parseLong(req.getParameter("id"));
            personService.removeById(id);
            log.info("Person deleted: {}", id);
            resp.sendRedirect(req.getContextPath() + "/person.do");
        } catch (Exception e) {
            log.error("Error deleting person", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private void loadDataAndForward(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            req.setAttribute("personList", personService.findAll());
            req.setAttribute("genders", Arrays.asList(Gender.values()));
            req.getRequestDispatcher("/jsp/person.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Cannot load persons", e);
        }
    }
}