package com.secondOrganization.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@ToString
@SuperBuilder

@Entity(name="branchEntity")
@Table(name = "branch_tbl")
public class Branch extends Base {

    @Column(name="name", length = 30, nullable = false)
    private String name;

    @Column(length = 200)
    private String address;

    @Column(length = 50)
    private String city;

    @Column(name="managerName", length = 30, nullable = false)
    private String manager;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Department> departments = new ArrayList<>();

    public void addDepartment(Department department) {
        departments.add(department);
        department.setBranch(this);
    }
    public void removeDepartment(Department department) {
        departments.remove(department);
        department.setBranch(null);
    }
}