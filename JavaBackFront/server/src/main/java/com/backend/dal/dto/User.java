package com.backend.dal.dto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class User {

    private UUID userId;
    private String name;
    private String phone;
    private String emil;

    public static User froResulSet(ResultSet rs) throws SQLException {

        User user = new User();

        user.setUserId(UUID.fromString(rs.getString("userId")));
        user.setName(rs.getString("name"));
        user.setEmil(rs.getString("email"));
        user.setPhone(rs.getString("phone"));

        return user;

    }

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
