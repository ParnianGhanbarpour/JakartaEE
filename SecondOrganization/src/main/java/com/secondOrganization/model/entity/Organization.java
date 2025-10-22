package com.secondOrganization.model.entity;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@ToString

@Entity(name="organizationEntity")
@Table(name="organization_tbl")
public class Organization extends Base {

    @Column(name = "org_name", length = 30)
    @JsonbProperty("نام سازمان")
    @Pattern(regexp = "^[a-zA-Zآ-ی\\s]{3,20}$", message = "نام سازمان معتبر نیست !")
    @Size(min = 3, max = 20, message = "اسم باید بین سه تا بیست حرف باشد .")
    @NotBlank(message = "فیلد را پر کنید ! ")
    private String name;

    @Column(length = 50)
    private String organizationType ;

    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL)
    private List<Department> department;


    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Branch> branches = new ArrayList<>();

    public void addBranch(Branch branch) {
        branches.add(branch);
        branch.setOrganization(this);
    }
    public void removeBranch(Branch branch) {
        branches.remove(branch);
        branch.setOrganization(null);
    }
}