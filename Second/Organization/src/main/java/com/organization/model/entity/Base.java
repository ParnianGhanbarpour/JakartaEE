package com.organization.model.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
//@SQLDelete(sql = "update my_base set deleted = true")
//@Where(clause = "where deleted = false")

//@Entity
//@Table(name="my_base")
public class Base {
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private LocalDateTime lastAccess;

    @Column(name="deleted")
    private boolean deleted;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        modifiedAt = LocalDateTime.now();
    }

    @PostLoad
    public void postLoad() {
        lastAccess = LocalDateTime.now();
    }
}