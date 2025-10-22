package com.secondOrganization.utils;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;

@ApplicationScoped
public class SecurityUtils {

    @Inject
    private SecurityContext securityContext;

    public String getCurrentUsername() {
        return securityContext.getCallerPrincipal() != null ? securityContext.getCallerPrincipal().getName() : null;
    }

    public boolean hasRole(String role) {
        return securityContext.isCallerInRole(role);
    }

    public boolean hasAuthority(String authority) {
        // Custom: از roles user fetch و check authorities
        // برای granular، implement در service
        return false; // Placeholder
    }
}