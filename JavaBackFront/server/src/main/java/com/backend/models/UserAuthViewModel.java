package com.backend.models;

import com.backend.dal.dto.AccessToken;
import com.backend.dal.dto.User;
import com.backend.dal.dto.UserAccess;

public class UserAuthViewModel {

    private User user;
    private UserAccess userAccess;
   
    private AccessToken accessToken;

    public UserAuthViewModel(User user,UserAccess userAccess, AccessToken accessToken) {
        this.user = user;
        this.accessToken = accessToken;
        this.userAccess=userAccess;
    }
    public UserAuthViewModel(){}

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public AccessToken getAccessToken() {
        return accessToken;
    }
    public void setAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }

    public UserAccess getUserAccess() {
        return userAccess;
    }
    public void setUserAccess(UserAccess userAccess) {
        this.userAccess = userAccess;
    }

}
