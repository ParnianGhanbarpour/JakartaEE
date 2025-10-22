package com.secondOrganization.model.entity.enums;

import lombok.Getter;

@Getter
public enum ProjectStatus {
    ACTIVE("فعال"),
    IN_PROGRESS("در حال انجام"),
    COMPLETED("تکمیل شده"),
    CANCELLED("لغو شده");

    private final String persianTitle;

    ProjectStatus(String persianTitle) {
        this.persianTitle = persianTitle;
    }

}
