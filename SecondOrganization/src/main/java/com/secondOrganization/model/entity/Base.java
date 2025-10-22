package com.secondOrganization.model.entity;

import com.google.gson.Gson;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor

public abstract class Base {
    @Id
    @SequenceGenerator(
            name = "baseSeq",
            sequenceName = "base_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "baseSeq")
    private Long id;


    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Column(name = "last_access")
    private LocalDateTime lastAccess;

    @JsonbTransient
    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        modifiedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedAt = LocalDateTime.now();
    }

    @PostLoad
    protected void onLoad() {
        lastAccess = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }



}
