package com.secondOrganization.dto;

import com.secondOrganization.model.entity.enums.Gender;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO {

    private Long id;

    @NotBlank(message = "نام الزامی است")
    @Size(min = 3, max = 20, message = "نام باید بین 3 تا 20 کاراکتر باشد")
    @Pattern(regexp = "^[\\p{L}\\s]+$", message = "نام فقط می‌تواند شامل حروف باشد")
    private String name;

    @NotBlank(message = "نام خانوادگی الزامی است")
    @Size(min = 3, max = 20, message = "نام خانوادگی باید بین 3 تا 20 کاراکتر باشد")
    private String family;

    @NotBlank(message = "کد ملی الزامی است")
    private String nationalCode;

    @DecimalMin(value = "0.0", message = "حقوق نمی‌تواند منفی باشد")
    private Double salary;

    @Past(message = "تاریخ تولد باید در گذشته باشد")
    private LocalDate birthdate;

    @NotNull(message = "جنسیت الزامی است")
    private Gender genderValue;


private String username;
    private Boolean isActive;

    private Long groupId;
    private String groupName;
    private String groupSpecialty;

    private String departmentName;
    private String departmentField;

    private Integer projectCount;
}