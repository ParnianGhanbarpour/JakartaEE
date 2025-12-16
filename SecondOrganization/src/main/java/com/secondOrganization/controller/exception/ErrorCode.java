package com.secondOrganization.controller.exception;

import lombok.*;

@Getter
@AllArgsConstructor
enum ErrorCode {
    NOT_FOUND("NOT_FOUND", 404),
    DUPLICATE_ENTRY("DUPLICATE", 409),
    VALIDATION_ERROR("VALIDATION", 400),
    INTERNAL_ERROR("INTERNAL", 500),
    UNAUTHORIZED("UNAUTHORIZED", 401),
    FORBIDDEN("FORBIDDEN", 403);

    private final String code;
    private final int httpStatus;
}