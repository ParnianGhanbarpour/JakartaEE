package com.secondOrganization.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor
@SuperBuilder

@Entity(name="organizationGroupEntity")
@Table(name = "organization_group_tbl")
public class OrganizationGroup extends Base {

    @Column(name="name", length = 30, nullable = false)
    private String name;

    @Column(name="specialty", length = 30, nullable = false)
    private String specialty;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @OneToMany(mappedBy = "organizationGroup", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Person> persons = new ArrayList<>();

    public void addPerson(Person person) {
        persons.add(person);
        person.setOrganizationGroup(this);
    }
    public void removePerson(Person person) {
        persons.remove(person);
        person.setOrganizationGroup(null);
    }
}