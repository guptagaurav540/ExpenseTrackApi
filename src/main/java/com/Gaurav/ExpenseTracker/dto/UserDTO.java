package com.Gaurav.ExpenseTracker.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data // Lombok annotation to generate getters, setters, toString, etc.
public class UserDTO {
    private Long id;
    private String name;
    private String email;

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}