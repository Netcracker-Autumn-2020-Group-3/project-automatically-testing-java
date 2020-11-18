package ua.netcracker.group3.automaticallytesting.entity;

import lombok.*;

public class User {

    private Integer id;

    private String username;

    private String password;

    private String email;

    private String surname;

    private String role;

    private Boolean is_enabled;


    public User(Integer id, String username, String password, String email, String surname, String role, Boolean is_enabled) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.surname = surname;
        this.role = role;
        this.is_enabled = is_enabled;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getIs_enabled() {
        return is_enabled;
    }

    public void setIs_enabled(Boolean is_enabled) {
        this.is_enabled = is_enabled;
    }
}
