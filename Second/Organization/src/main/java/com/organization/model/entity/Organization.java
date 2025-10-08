package com.organization.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;



import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@ToString

@Entity(name="organizationEntity")
@Table(name="ORGANIZATIONS")
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int id;

    @Column(name="name",columnDefinition = "nvarchar2(30)", nullable = false)
    @JsonProperty("نام")
    @Pattern(regexp = "^[a-zA-Z\\s]{3,30}$", message = "Invalid Name !!!")
    private String name;

    @Column(length = 50)
    private String organizationType ;

    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Branch> branches = new ArrayList<Branch>();


    public void addBranch(Branch branch) {
        branches.add(branch);
        branch.setOrganization(this);
    }
    public void removeBranch(Branch branch) {
        branches.remove(branch);
        branch.setOrganization(null);
    }


}
