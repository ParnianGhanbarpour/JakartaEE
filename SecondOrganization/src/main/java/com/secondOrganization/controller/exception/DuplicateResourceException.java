package com.secondOrganization.controller.exception;

public class DuplicateResourceException extends BusinessException {
    public DuplicateResourceException(String resource, String field, String value) {
        super(ErrorCode.DUPLICATE_ENTRY,
                String.format("%s با %s='%s' قبلاً ثبت شده است", resource, field, value));
    }
}