package com.example.organization.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor
@SuperBuilder

@Entity(name="organizationGroupEntity")
@Table(name = "organizationGroups")

public class OrganizationGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(name="name",columnDefinition = "nvarchar2(30)", nullable = false)
    private String name;

    @Column(name="specialty",columnDefinition = "nvarchar2(30)", nullable = false)
    private String specialty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @OneToMany(mappedBy = "organizationGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Employee> employees = new ArrayList<>();


    public void addEmployee(Employee employee) {
        employees.add(employee);
        employee.setOrganizationGroup(this);
    }
    public void removeGroup(Employee employee) {
        employees.remove(employee);
        employee.setOrganizationGroup(this);

    }


}
