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

@Entity(name="departmentEntity")
@Table(name = "departments")

@Getter
@Setter
@ToString
@NoArgsConstructor
@SuperBuilder

public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(name="name",columnDefinition = "nvarchar2(30)", nullable = false)
    private String name;

    @Column(name="field",columnDefinition = "nvarchar2(30)", nullable = false)
    private String field;

    @Column(name="budget", nullable = false)
    private double budget;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrganizationGroup> organizationGroups = new ArrayList<>();


    public void addGroup(OrganizationGroup organizationGroup) {
        organizationGroups.add(organizationGroup);
        organizationGroup.setDepartment(this);
    }
    public void removeGroup(OrganizationGroup organizationGroup) {
        organizationGroups.remove(organizationGroup);
        organizationGroup.setDepartment(this);
    }

}
