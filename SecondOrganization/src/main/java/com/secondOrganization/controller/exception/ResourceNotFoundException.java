package com.secondOrganization.controller.exception;

public class ResourceNotFoundException extends BusinessException {
    public ResourceNotFoundException(String resource, Long id) {
        super(ErrorCode.NOT_FOUND,
                String.format("%s با شناسه %d یافت نشد", resource, id));
    }

    public ResourceNotFoundException(String message) {
        super(ErrorCode.NOT_FOUND, message);
    }
}
