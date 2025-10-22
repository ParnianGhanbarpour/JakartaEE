package com.secondOrganization.dto;

import com.secondOrganization.model.entity.enums.Gender;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class ProfileDTO {
    private Long id;

    private String name;

    private String family;

    private String nationalCode;

    private Gender gender;

    private Long roleId;

    private String username;
}
