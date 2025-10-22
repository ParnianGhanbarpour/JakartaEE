package com.secondOrganization.model.entity;

import jakarta.json.bind.annotation.JsonbTransient;
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
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder

@Entity(name="userEntity")
@Table(name="user_tbl")
public class User extends Base implements Serializable {

    @Column(name="user_username", length = 50, unique = true)
    @Pattern(
            regexp = "^[A-Za-z][A-Za-z0-9_]{4,19}$",
            message = "نام کاربری باید با حرف شروع شود و فقط شامل حروف، اعداد و _ باشد"
    )
    @Size(min = 5, max = 20, message = "نام کاربری باید بین ۵ تا ۲۰ کاراکتر باشد")
    @NotBlank(message = "نام کاربری نمی‌تواند خالی باشد")
    private String username;

    @JsonbTransient
    @Column(name = "user_password", length = 100, nullable = false)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{5,20}$", message = "حداقل باید 5 کاراکتر داشته باشد و یک حرف و یک عدد باید وجود داشته باشد!")
    @Size(min = 5, max = 20, message = "پسورد باید بین 5 تا 20 کاراکتر باشد")
    @NotBlank(message = "پسورد نباید خالی باشد")
    private String password;

    @Column(name = "user_active")
    private boolean active;

    @ElementCollection(targetClass = com.secondOrganization.model.entity.enums.Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_username"))
    @Enumerated(EnumType.STRING)
    private List<com.secondOrganization.model.entity.enums.Role> roleList = new ArrayList<>();

    public void addRole(com.secondOrganization.model.entity.enums.Role role) {
        if (roleList == null) {
            roleList = new ArrayList<>();
        }
        roleList.add(role);
    }

    @OneToOne(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private Person person;
}