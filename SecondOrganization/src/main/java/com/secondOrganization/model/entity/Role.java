package com.secondOrganization.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@ToString


@Entity
@Table(name="role_tbl")
public class Role extends Base implements Serializable {

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_username")
    private User user;

    @Column(name = "role_name", columnDefinition = "NVARCHAR2(10)")
    @Pattern(regexp = "^[a-zA-Z\\s]{3,10}$", message = "Invalid RoleName")
    @Size(min = 3, max = 10, message = "RoleName must be between 3 and 10 characters")
    @NotBlank(message = "Should Not Be Null")
    private String role;

}
