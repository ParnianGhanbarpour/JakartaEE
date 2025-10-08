package org.secondorganization.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Person {
    private int id;

    @JsonProperty("نام")
    private String name;

//    @JsonIgnore
    private String family;

    public Person() {
    }

    public Person(int id, String name, String family) {
        this.id = id;
        this.name = name;
        this.family = family;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }
}
