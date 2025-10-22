package com.secondOrganization.controller.exception;

public class DuplicateUsernameException extends Exception {
    public DuplicateUsernameException(String massage) {
        super(massage);
    }
}
