package com.example.CourseAppJava.models.User;

import java.io.Serializable;

public class User implements Serializable {
    private String id;
    private String name;
    private String email;




    public String getId() {
        return id;
    }

    public void setNumber(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
