package org.secondorganization.model.entity;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/")
public class Api extends Application {
    public Api() {
        System.out.println("Rest App Started");

    }
}
