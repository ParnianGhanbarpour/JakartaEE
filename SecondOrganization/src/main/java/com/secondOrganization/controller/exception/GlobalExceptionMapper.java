package com.secondOrganization.controller.exception;


import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Provider
class GlobalExceptionMapper implements ExceptionMapper<Exception> {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionMapper.class);

    @Override
    public Response toResponse(Exception exception) {
        ErrorResponse error;
        Response.Status status;

        if (exception instanceof BusinessException) {
            BusinessException be = (BusinessException) exception;
            error = ErrorResponse.builder()
                    .errorCode(be.getErrorCode().getCode())
                    .message(be.getMessage())
                    .timestamp(LocalDateTime.now())
                    .status(be.getErrorCode().getHttpStatus())
                    .build();
            status = Response.Status.fromStatusCode(be.getErrorCode().getHttpStatus());

            log.warn("Business exception: {} - {}", be.getErrorCode(), be.getMessage());

        } else if (exception instanceof ConstraintViolationException) {
            ConstraintViolationException cve = (ConstraintViolationException) exception;
            String violations = cve.getConstraintViolations().stream()
                    .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                    .collect(Collectors.joining(", "));

            error = ErrorResponse.builder()
                    .errorCode(ErrorCode.VALIDATION_ERROR.getCode())
                    .message("خطای اعتبارسنجی: " + violations)
                    .timestamp(LocalDateTime.now())
                    .status(400)
                    .build();
            status = Response.Status.BAD_REQUEST;

            log.warn("Validation error: {}", violations);

        } else {
            log.error("Unexpected error", exception);
            error = ErrorResponse.builder()
                    .errorCode(ErrorCode.INTERNAL_ERROR.getCode())
                    .message("خطای غیرمنتظره رخ داد")
                    .timestamp(LocalDateTime.now())
                    .status(500)
                    .build();
            status = Response.Status.INTERNAL_SERVER_ERROR;
        }

        return Response.status(status).entity(error).build();
    }
}