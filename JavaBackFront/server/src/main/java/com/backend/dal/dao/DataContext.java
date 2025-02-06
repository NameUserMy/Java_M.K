package com.backend.dal.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

import com.backend.services.db.DbService;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class DataContext {
    private final Connection connection;
    private UserDao userDao;
    @Inject
    public DataContext(DbService dbService,Logger logger) throws SQLException {
        this.connection = dbService.getConnection();
        userDao = new UserDao(connection,logger);
    }

    public UserDao getUserDao() {
        return userDao;
    }

}
