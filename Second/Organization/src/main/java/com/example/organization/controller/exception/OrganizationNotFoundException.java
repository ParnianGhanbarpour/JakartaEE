package com.example.organization.controller.exception;

public class OrganizationNotFoundException extends Exception {
    public OrganizationNotFoundException() {
        super("organization not found");
    }
}
