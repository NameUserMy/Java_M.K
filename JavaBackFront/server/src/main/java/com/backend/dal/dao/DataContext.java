package com.backend.dal.dao;

import java.sql.SQLException;


import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

@Singleton
public class DataContext {
    private UserDao userDao;

    @Inject
    public DataContext(Injector injector) throws SQLException {
        userDao = injector.getInstance(UserDao.class);

    }

    public UserDao getUserDao() {
        return userDao;
    }

}
