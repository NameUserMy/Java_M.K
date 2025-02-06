package com.backend.dal.dto;

import java.util.UUID;

public class User {

    private UUID userId;
    private String name;
    private String phone;
    private String emil;
    public UUID getUserId() {
        return userId;
    }
    public void setUserId(UUID userId) {
        this.userId = userId;
    }
   
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
   
    public String getEmil() {
        return emil;
    }
    public void setEmil(String emil) {
        this.emil = emil;
    }
    
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

}
