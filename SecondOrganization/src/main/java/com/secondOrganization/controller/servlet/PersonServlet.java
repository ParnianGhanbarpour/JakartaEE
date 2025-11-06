package com.secondOrganization.controller.servlet;

import com.secondOrganization.model.entity.OrganizationGroup;
import com.secondOrganization.model.entity.Person;
import com.secondOrganization.model.entity.User;
import com.secondOrganization.model.entity.enums.Gender;
import com.secondOrganization.service.OrganizationGroupService;
import com.secondOrganization.service.UserService;
import com.secondOrganization.service.impl.PersonServiceImpl;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@WebServlet(urlPatterns = "/person.do")
public class PersonServlet extends HttpServlet {

    @Inject
    private PersonServiceImpl personService;

    @Inject
    private UserService userService;

    @Inject
    private OrganizationGroupService organizationGroupService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String searchName = req.getParameter("searchName");
            String searchFamily = req.getParameter("searchFamily");
            String searchNationalCode = req.getParameter("searchNationalCode");

            List<Person> personList;

            if ((searchName != null && !searchName.trim().isEmpty()) ||
                    (searchFamily != null && !searchFamily.trim().isEmpty()) ||
                    (searchNationalCode != null && !searchNationalCode.trim().isEmpty())) {

                log.info("Searching persons - name: {}, family: {}, nationalCode: {}",
                        searchName, searchFamily, searchNationalCode);

                personList = personService.findAll();

                if (searchName != null && !searchName.trim().isEmpty()) {
                    final String nameLower = searchName.trim().toLowerCase();
                    personList = personList.stream()
                            .filter(p -> p.getName() != null &&
                                    p.getName().toLowerCase().contains(nameLower))
                            .collect(Collectors.toList());
                }

                if (searchFamily != null && !searchFamily.trim().isEmpty()) {
                    final String familyLower = searchFamily.trim().toLowerCase();
                    personList = personList.stream()
                            .filter(p -> p.getFamily() != null &&
                                    p.getFamily().toLowerCase().contains(familyLower))
                            .collect(Collectors.toList());
                }

                if (searchNationalCode != null && !searchNationalCode.trim().isEmpty()) {
                    final String code = searchNationalCode.trim();
                    personList = personList.stream()
                            .filter(p -> p.getNationalCode() != null &&
                                    p.getNationalCode().contains(code))
                            .collect(Collectors.toList());
                }

                log.info("Found {} persons matching search criteria", personList.size());
            } else {
                personList = personService.findAll();
                log.info("Loaded all {} persons", personList.size());
            }

            req.setAttribute("personList", personList);
            req.setAttribute("genders", Arrays.asList(Gender.values()));
            req.setAttribute("organizationGroupList", organizationGroupService.findAll());

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
            String username = req.getParameter("username");
            String groupIdStr = req.getParameter("organizationGroupId");

            if (name == null || name.trim().isEmpty()) {
                req.setAttribute("error", "نام الزامی است");
                loadDataAndForward(req, resp);
                return;
            }

            if (family == null || family.trim().isEmpty()) {
                req.setAttribute("error", "نام خانوادگی الزامی است");
                loadDataAndForward(req, resp);
                return;
            }

            if (nationalCode == null || nationalCode.trim().isEmpty()) {
                req.setAttribute("error", "کد ملی الزامی است");
                loadDataAndForward(req, resp);
                return;
            }

            Optional<Person> existingPerson = personService.findByNationalCode(nationalCode);
            if (existingPerson.isPresent()) {
                req.setAttribute("error", "کد ملی تکراری است!");
                loadDataAndForward(req, resp);
                return;
            }

            String salaryStr = req.getParameter("salary");
            Double salary = null;
            if (salaryStr != null && !salaryStr.trim().isEmpty()) {
                try {
                    salary = Double.parseDouble(salaryStr.replace(",", ""));
                } catch (NumberFormatException e) {
                    req.setAttribute("error", "مقدار حقوق نامعتبر است");
                    loadDataAndForward(req, resp);
                    return;
                }
            }

            String birthdateStr = req.getParameter("birthdate");
            LocalDate birthdate = null;
            if (birthdateStr != null && !birthdateStr.trim().isEmpty()) {
                birthdate = LocalDate.parse(birthdateStr);
            }

            OrganizationGroup group = null;
            if (groupIdStr != null && !groupIdStr.trim().isEmpty()) {
                Long groupId = Long.parseLong(groupIdStr);
                Optional<OrganizationGroup> groupOpt = organizationGroupService.findById(groupId);
                if (groupOpt.isPresent()) {
                    group = groupOpt.get();
                }
            }

            User user = null;
            if (username != null && !username.trim().isEmpty()) {
                Optional<User> userOpt = userService.findByUsername(username);
                if (userOpt.isPresent()) {
                    user = userOpt.get();

                    Optional<Person> existingUserPerson = personService.findByUsername(username);
                    if (existingUserPerson.isPresent()) {
                        req.setAttribute("error", "این کاربر قبلاً در سیستم ثبت شده است");
                        loadDataAndForward(req, resp);
                        return;
                    }
                } else {
                    user = User.builder()
                            .username(username)
                            .password(username + "123")
                            .active(true)
                            .deleted(false)
                            .build();
                    userService.save(user);
                    log.info("User created: {}", username);
                }
            } else {
                username = "user_" + nationalCode;

                int counter = 1;
                String finalUsername = username;
                while (userService.findByUsername(finalUsername).isPresent()) {
                    finalUsername = username + "_" + counter;
                    counter++;
                }

                user = User.builder()
                        .username(finalUsername)
                        .password(nationalCode)
                        .active(true)
                        .deleted(false)
                        .build();
                userService.save(user);
                log.info("Auto-created User: {}", finalUsername);
            }

            Person person = Person.builder()
                    .name(name.trim())
                    .family(family.trim())
                    .nationalCode(nationalCode.trim())
                    .gender(gender)
                    .salary(salary)
                    .birthdate(birthdate)
                    .user(user)
                    .organizationGroup(group)
                    .deleted(false)
                    .build();

            personService.save(person);
            log.info("Person saved: {} {} (National Code: {})", name, family, nationalCode);

            resp.sendRedirect(req.getContextPath() + "/person.do?success=true");

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
            log.info("Person soft deleted: {}", id);

            resp.sendRedirect(req.getContextPath() + "/person.do?deleted=true");
        } catch (Exception e) {
            log.error("Error deleting person", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private void loadDataAndForward(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            List<Person> personList = personService.findAll();

            req.setAttribute("personList", personList);
            req.setAttribute("genders", Arrays.asList(Gender.values()));
            req.setAttribute("organizationGroupList", organizationGroupService.findAll());

            req.getRequestDispatcher("/jsp/person.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Cannot load persons", e);
        }
    }
}