package com.example.organization.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@ToString
@SuperBuilder

@Entity(name="employeeEntity")
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(unique = true, nullable = false, length = 10)
    private String nationalId;

    @Column(length = 50)
    private String position;

    @Column
    private Double salary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private OrganizationGroup organizationGroup;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProjectAssignment> assignments = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "EMPLOYEE_PROJECT",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private Set<Project> projects = new HashSet<>();

    public void addProject(Project project) {
        projects.add(project);
        project.setEmployees((Set<Employee>) this);
    }
    public void removeGroup(Project project) {
        projects.remove(project);
        project.setEmployees((Set<Employee>) this);

    }





}
