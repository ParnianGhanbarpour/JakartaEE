package com.secondOrganization.model.entity;

import com.secondOrganization.model.entity.enums.ProjectStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"assignments", "persons"})
@Builder(toBuilder = true)

@Entity
@Table(name = "project_tbl")
public class Project extends Base {

    @Column(name = "title", columnDefinition = "NVARCHAR2(100)", nullable = false)
    @Size(min = 3, max = 100, message = "نام پروژه باید بین ۳ تا ۱۰۰ کاراکتر باشد")
    @NotBlank(message = "نام پروژه نمی‌تواند خالی باشد")
    private String title;

    @Column(name = "description", columnDefinition = "NVARCHAR2(500)")
    @Size(max = 500, message = "توضیحات پروژه نمی‌تواند بیش از ۵۰۰ کاراکتر باشد")
    private String description;

    @Column(name = "start_date", nullable = false)
    @NotNull(message = "تاریخ شروع نمی‌تواند خالی باشد")
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    @NotNull(message = "تاریخ پایان نمی‌تواند خالی باشد")
    private LocalDateTime endDate;

    @Column(name = "budget", nullable = false, precision = 18, scale = 2)
    @DecimalMin(value = "0.01", message = "بودجه باید بیشتر از صفر باشد")
    @Digits(integer = 16, fraction = 2, message = "بودجه باید عددی معتبر باشد")
    @NotNull(message = "بودجه نمی‌تواند خالی باشد")
    private BigDecimal budget;

    @Column(name = "status", nullable = false, columnDefinition = "VARCHAR2(20)")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "وضعیت پروژه نمی‌تواند خالی باشد")
    private ProjectStatus status;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<ProjectAssignment> assignments = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
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
        if (persons == null) {
            persons = new HashSet<>();
        }
        persons.add(person);
    }

    public void removePerson(Person person) {
        if (persons != null) {
            persons.remove(person);
        }
    }

    protected void validateDates() {
        if (endDate != null && startDate != null && endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("تاریخ پایان نمی‌تواند قبل از تاریخ شروع باشد");
        }
    }
}