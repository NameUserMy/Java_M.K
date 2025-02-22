package com.backend.dal.dao;

import java.sql.SQLException;


import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

@Singleton
public class DataContext {
    private UserDao userDao;
    private AccessTokenDao accessTokenDao;

    @Inject
    public DataContext(Injector injector,AccessTokenDao accessTokenDao) throws SQLException {
        this.userDao = injector.getInstance(UserDao.class);
        this.accessTokenDao=accessTokenDao;

    }

    public UserDao getUserDao() {
        return userDao;
    }
    public AccessTokenDao getAccessTokenDao() {
        return accessTokenDao;
    }

}
