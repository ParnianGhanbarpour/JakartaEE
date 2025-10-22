package com.secondOrganization.config;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

@ApplicationScoped
public class LoggingInitializer {
    private static final Logger logger = LoggerFactory.getLogger(LoggingInitializer.class);
    
    @PostConstruct
    public void init() {
        try {
            // حذف handlers قبلی (جلوگیری از duplicate logs)
            SLF4JBridgeHandler.removeHandlersForRootLogger();
            // نصب bridge
            SLF4JBridgeHandler.install();
            logger.info("SLF4J Bridge installed successfully");
        } catch (Exception e) {
            logger.error("Failed to install SLF4J Bridge", e);
        }
    }
}