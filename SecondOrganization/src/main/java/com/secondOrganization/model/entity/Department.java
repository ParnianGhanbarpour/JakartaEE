package com.secondOrganization.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(exclude = {"branch", "organization", "organizationGroups"})
@Entity(name = "departmentEntity")
@Table(name = "department_tbl")
public class Department extends Base {

    @Column(name = "department_name", length = 50, nullable = false)
    private String name;

    @Column(name = "department_field", length = 50, nullable = false)
    private String field;

    @Column(name = "department_duty", length = 100)
    private String duty;

    @Column(name = "phone_number", length = 15)
    private String phoneNumber;

    @Column(name = "budget")
    private Double budget;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrganizationGroup> organizationGroups = new ArrayList<>();

    public void addGroup(OrganizationGroup organizationGroup) {
        organizationGroups.add(organizationGroup);
        organizationGroup.setDepartment(this);
    }

    public void removeGroup(OrganizationGroup organizationGroup) {
        organizationGroups.remove(organizationGroup);
        organizationGroup.setDepartment(null);
    }
}
