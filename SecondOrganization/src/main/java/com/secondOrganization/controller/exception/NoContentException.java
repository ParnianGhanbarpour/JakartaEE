package com.secondOrganization.controller.exception;

public class NoContentException extends MyException{
    public NoContentException(String message) {
        super(message);
        setStatusCode(404);
    }
}
