package com.secondOrganization.config;

import com.secondOrganization.model.entity.*;
import com.secondOrganization.model.entity.enums.Gender;
import com.secondOrganization.service.*;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

/**
 * Data Initializer - ایجاد داده‌های اولیه سیستم
 * این کلاس با استفاده از @Startup و @Singleton حتماً اجرا می‌شود
 */
@Slf4j
@Singleton
@Startup
public class DataInitializer {

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

    @PostConstruct
    public void init() {
        log.info("========================================");
        log.info("  Starting Data Initialization...");
        log.info("========================================");

        try {
            // پاک کردن داده‌های قبلی (اختیاری - فقط برای توسعه)
            // clearOldData();

            Organization mainOrg = createOrganizationIfNotExists("مجتمع فنی تهران", "آموزشی");
            Organization secondOrg = createOrganizationIfNotExists("شرکت نرم‌افزاری پارس", "خصوصی");

            Branch mainBranch = createBranchIfNotExists(
                    "شعبه مرکزی",
                    "تهران",
                    "خیابان آزادی، پلاک 100",
                    "مدیر مرکزی",
                    mainOrg
            );

            Branch secondBranch = createBranchIfNotExists(
                    "شعبه غرب تهران",
                    "تهران",
                    "خیابان ستاری، پلاک 50",
                    "مدیر شعبه غرب",
                    mainOrg
            );

            Department itDept = createDepartmentIfNotExists(
                    "فناوری اطلاعات",
                    "IT",
                    "توسعه و نگهداری سیستم‌های نرم‌افزاری",
                    "021-12345678",
                    1_000_000.0,
                    mainOrg,
                    mainBranch
            );

            Department hrDept = createDepartmentIfNotExists(
                    "منابع انسانی",
                    "HR",
                    "مدیریت پرسنل و استخدام",
                    "021-12345679",
                    500_000.0,
                    mainOrg,
                    mainBranch
            );

            log.info(" Creating sample users...");

            User adminUser = createUserIfNotExists("admin", "admin123");
            createRoleIfNotExists(adminUser, "admin");
            createPersonIfNotExists(
                    adminUser,
                    "مدیر",
                    "سیستم",
                    "0000000000",
                    Gender.male,
                    10_000_000.0
            );

            User managerUser = createUserIfNotExists("manager", "manager123");
            createRoleIfNotExists(managerUser, "manager");
            createPersonIfNotExists(
                    managerUser,
                    "مدیر",
                    "واحد",
                    "1111111111",
                    Gender.female,
                    8_000_000.0
            );

            User basicUser = createUserIfNotExists("user", "user123");
            createRoleIfNotExists(basicUser, "user");
            createPersonIfNotExists(
                    basicUser,
                    "کاربر",
                    "عادی",
                    "2222222222",
                    Gender.male,
                    5_000_000.0
            );

            log.info("========================================");
            log.info(" Data Initialization Completed Successfully!");
            log.info("========================================");
            log.info("Demo Accounts:");
            log.info("  - Admin:   username=admin    password=admin123");
            log.info("  - Manager: username=manager  password=manager123");
            log.info("  - User:    username=user     password=user123");
            log.info("========================================");

        } catch (Exception e) {
            log.error(" Error during data initialization: {}", e.getMessage(), e);
        }
    }

    private User createUserIfNotExists(String username, String password) throws Exception {
        Optional<User> userOpt = userService.findByUsername(username);

        if (userOpt.isPresent()) {
            log.info("    User '{}' already exists", username);
            return userOpt.get();
        }

        User user = User.builder()
                .username(username)
                .password(password) // TODO: در production باید hash شود
                .active(true)
                .deleted(false)
                .build();

        userService.save(user);
        log.info("    Created user: {}", username);
        return user;
    }

    private void createRoleIfNotExists(User user, String roleName) throws Exception {
        List<Role> existingRoles = roleService.findByUsernameAndRoleName(user.getUsername(), roleName);

        if (!existingRoles.isEmpty()) {
            log.info("    Role '{}' already exists for user '{}'", roleName, user.getUsername());
            return;
        }

        Role role = Role.builder()
                .role(roleName)
                .user(user)
                .deleted(false)
                .build();

        roleService.save(role);
        log.info("    Created role '{}' for user '{}'", roleName, user.getUsername());
    }

    private void createPersonIfNotExists(User user, String name, String family,
                                         String nationalCode, Gender gender, Double salary) throws Exception {
        Optional<Person> personOpt = personService.findByUsername(user.getUsername());

        if (personOpt.isPresent()) {
            log.info("    Person already exists for user '{}'", user.getUsername());
            return;
        }

        Person person = Person.builder()
                .name(name)
                .family(family)
                .nationalCode(nationalCode)
                .gender(gender)
                .salary(salary)
                .user(user)
                .deleted(false)
                .build();

        personService.save(person);
        log.info("    Created person: {} {} for user '{}'", name, family, user.getUsername());
    }

    private Organization createOrganizationIfNotExists(String name, String type) throws Exception {
        Optional<Organization> orgOpt = organizationService.findByName(name);

        if (orgOpt.isPresent()) {
            log.info("    Organization '{}' already exists", name);
            return orgOpt.get();
        }

        Organization org = Organization.builder()
                .name(name)
                .organizationType(type)
                .deleted(false)
                .build();

        organizationService.save(org);
        log.info("    Created organization: {}", name);
        return org;
    }

    private Branch createBranchIfNotExists(String name, String city, String address,
                                           String manager, Organization org) throws Exception {
        List<Branch> branches = branchService.findByOrganizationId(org.getId());

        for (Branch b : branches) {
            if (b.getName().equals(name)) {
                log.info("    Branch '{}' already exists", name);
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
        log.info("    Created branch: {}", name);
        return branch;
    }

    private Department createDepartmentIfNotExists(String name, String field, String duty,
                                                   String phoneNumber, Double budget,
                                                   Organization org, Branch branch) throws Exception {
        Optional<Department> deptOpt = departmentService.findByName(name);

        if (deptOpt.isPresent()) {
            log.info("    Department '{}' already exists", name);
            return deptOpt.get();
        }

        Department dept = Department.builder()
                .name(name)
                .field(field)
                .duty(duty)
                .phoneNumber(phoneNumber)
                .budget(budget)
                .organization(org)
                .branch(branch)
                .deleted(false)
                .build();

        departmentService.save(dept);
        log.info("   Created department: {}", name);
        return dept;
    }


    private void clearOldData() {
        try {
            log.warn("⚠️  Clearing old data (Development mode only)...");
            // TODO: Implement if needed
            // این بخش را فقط در development استفاده کنید
        } catch (Exception e) {
            log.error("Error clearing old data: {}", e.getMessage());
        }
    }
}