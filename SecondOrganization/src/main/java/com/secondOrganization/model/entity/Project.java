package com.secondOrganization.model.entity;

import com.secondOrganization.model.entity.enums.ProjectStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"assignments", "persons"})
@SuperBuilder

@Entity(name = "projectEntity")
@Table(name = "project_tbl")
public class Project extends Base {

    @Column(name = "title", columnDefinition = "NVARCHAR2(30)", nullable = false)
    @Pattern(regexp = "^[a-zA-Zآ-ی\\s]{3,30}$", message = "نام پروژه نامعتبر است (فقط حروف و فاصله مجاز است)")
    @Size(min = 3, max = 30, message = "نام پروژه باید بین ۳ تا ۳۰ کاراکتر باشد")
    @NotBlank(message = "نام پروژه نمی‌تواند خالی باشد")
    private String title;

    @Column(name = "description", columnDefinition = "NVARCHAR2(200)")
    @Size(max = 200, message = "توضیحات پروژه نمی‌تواند بیش از ۲۰۰ کاراکتر باشد")
    private String description;

    @Column(name = "start_date", nullable = false)
    @PastOrPresent(message = "تاریخ شروع نمی‌تواند در آینده باشد")
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    @Future(message = "تاریخ پایان باید در آینده باشد")
    private LocalDateTime endDate;

    @Column(name = "budget", nullable = false, columnDefinition = "NUMBER(12,2)")
    @DecimalMin(value = "0.0", inclusive = false, message = "بودجه باید بیشتر از صفر باشد")
    @Digits(integer = 10, fraction = 2, message = "بودجه باید عددی معتبر با حداکثر ۱۰ رقم صحیح و ۲ رقم اعشار باشد")
    private double budget;

    @Column(name = "status", nullable = false, columnDefinition = "VARCHAR2(20)")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "وضعیت پروژه نمی‌تواند خالی باشد")
    private ProjectStatus status;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<ProjectAssignment> assignments = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "project_persons",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "p_id")
    )
    private Set<Person> persons = new HashSet<>();

    public void addAssignment(ProjectAssignment assignment) {
        assignments.add(assignment);
        assignment.setProject(this);
    }

    public void removeAssignment(ProjectAssignment assignment) {
        assignments.remove(assignment);
        assignment.setProject(null);
    }

    public void addPerson(Person person) {
        persons.add(person);
    }

    public void removePerson(Person person) {
        persons.remove(person);
    }
}