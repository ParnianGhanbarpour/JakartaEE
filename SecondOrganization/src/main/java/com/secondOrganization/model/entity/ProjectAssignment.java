package com.secondOrganization.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@SuperBuilder

@Entity
@Table(name = "projectAssignment_tbl")
public class ProjectAssignment extends Base{

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "p_id", nullable = false)
    private Person person;
}