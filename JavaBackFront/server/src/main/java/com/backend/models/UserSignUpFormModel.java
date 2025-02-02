package com.backend.models;

import java.util.Date;

public class UserSignUpFormModel {

    private String name;
    private String email;
    private String password;
    private Date dob;
    private String city;
    private String login;

    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }
    public void setDob(Date dob) {
        this.dob = dob;
    }
    public Date getDob() {
        return dob;
    }
}
