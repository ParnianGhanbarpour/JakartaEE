package com.secondOrganization.model.entity;

import com.secondOrganization.model.entity.enums.Gender;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@ToString

@Entity(name = "personEntity")
@Table(name = "person_tbl")
public class Person extends Base{

    @Column(name = "p_name", length = 20)
    @Pattern(regexp = "^[a-zA-Zآ-ی\\s]{3,20}$", message = "نام نامعتبر است!")
    @Size(min = 3, max = 20, message = "اسم باید بین 3 تا 20 حرف باشد")
    @NotBlank(message = "فیلد نباید خالی باشد!")
    private String name;

    @Column(name = "p_family", length = 20)
    @Pattern(regexp = "^[a-zA-Zآ-ی\\s]{3,20}$", message = "نام خانوادگی نامعتبر است")
    @Size(min = 3, max = 20, message = "نام خانوادگی باید بین 3 تا 20 حرف باشد")
    @NotBlank(message = "فیلد نباید خالی باشد.")
    private String family;

    @Column(name = "p_nationalCode", length = 10)
    @Pattern(regexp = "^[0-9]{10}$", message = "کد ملی باید فقط شامل ۱۰ رقم عددی باشد")
    @Size(min = 10, max = 10, message = "کد ملی باید دقیقاً ۱۰ رقم باشد")
    @NotBlank(message = "کد ملی نمی‌تواند خالی باشد")
    private String nationalCode;

    @Column(name = "p_salary")
    @DecimalMin(value = "0.0", inclusive = true, message = "حقوق نمی‌تواند منفی باشد")
    @Digits(integer = 12, fraction = 2, message = "حقوق باید عددی معتبر با حداکثر 12 رقم صحیح و ۲ رقم اعشار باشد")
    @NotNull(message = "حقوق نمی‌تواند خالی باشد")
    private Double salary;

    @Column(name = "p_birthdate")
    @NotNull(message = "تاریخ تولد نمی‌تواند خالی باشد")
    private LocalDate birthdate;

    @Enumerated(EnumType.ORDINAL)
    private Gender gender;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_username", nullable = false, unique = true)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id", nullable = false)
    private OrganizationGroup organizationGroup;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<ProjectAssignment> assignments = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "PERSON_PROJECT",
            joinColumns = @JoinColumn(name = "p_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private Set<Project> projects = new HashSet<>();

    public void addProject(Project project) {
        projects.add(project);
        project.getPersons().add(this);
    }
    public void removeProject(Project project) {
        projects.remove(project);
        project.getPersons().remove(this);
    }
}