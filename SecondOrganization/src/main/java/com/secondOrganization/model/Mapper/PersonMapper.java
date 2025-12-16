package com.secondOrganization.model.Mapper;

import com.secondOrganization.dto.PersonDTO;
import com.secondOrganization.model.entity.Person;
import com.secondOrganization.model.entity.enums.Gender;

import java.util.List;
import java.util.stream.Collectors;


public class PersonMapper {


    public static PersonDTO toDTO(Person person) {
        if (person == null) {
            return null;
        }

        PersonDTO.PersonDTOBuilder builder = PersonDTO.builder()
                .id(person.getId())
                .name(person.getName())
                .family(person.getFamily())
                .nationalCode(person.getNationalCode())
                .salary(person.getSalary())
                .birthdate(person.getBirthdate());

        if (person.getGender() != null) {
            builder.genderValue(person.getGender())
                    .genderValue(Gender.valueOf(person.getGender().getTitle()));
        }

        if (person.getUser() != null) {
            builder.username(person.getUser().getUsername())
                    .isActive(person.getUser().isActive());
        }

        if (person.getOrganizationGroup() != null) {
            builder.groupId(person.getOrganizationGroup().getId())
                    .groupName(person.getOrganizationGroup().getName())
                    .groupSpecialty(person.getOrganizationGroup().getSpecialty());

            if (person.getOrganizationGroup().getDepartment() != null) {
                builder.departmentName(person.getOrganizationGroup().getDepartment().getName())
                        .departmentField(person.getOrganizationGroup().getDepartment().getField());
            }
        }

        if (person.getProjects() != null) {
            builder.projectCount(person.getProjects().size());
        }

        return builder.build();
    }


    public static List<PersonDTO> toDTOList(List<Person> persons) {
        if (persons == null) {
            return null;
        }
        return persons.stream()
                .map(PersonMapper::toDTO)
                .collect(Collectors.toList());
    }


    public static void updateEntityFromDTO(Person person, PersonDTO dto) {
        if (dto.getName() != null) {
            person.setName(dto.getName());
        }
        if (dto.getFamily() != null) {
            person.setFamily(dto.getFamily());
        }
        if (dto.getNationalCode() != null) {
            person.setNationalCode(dto.getNationalCode());
        }
        if (dto.getSalary() != null) {
            person.setSalary(dto.getSalary());
        }
        if (dto.getBirthdate() != null) {
            person.setBirthdate(dto.getBirthdate());
        }
        if (dto.getGenderValue() != null) {
            person.setGender(dto.getGenderValue());
        }
    }
}