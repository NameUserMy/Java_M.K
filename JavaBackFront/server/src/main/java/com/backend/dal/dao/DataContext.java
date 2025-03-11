package com.backend.dal.dao;

import java.sql.SQLException;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

@Singleton
public class DataContext {
    private UserDao userDao;
    private AccessTokenDao accessTokenDao;
    private CategoryDao categoryDao;
    private ProductDao productDao;

    @Inject
    public DataContext(ProductDao productDao, CategoryDao categoryDao, Injector injector, AccessTokenDao accessTokenDao)
            throws SQLException {
        this.userDao = injector.getInstance(UserDao.class);
        this.accessTokenDao = accessTokenDao;
        this.categoryDao = categoryDao;
        this.productDao = productDao;

    }

    public UserDao getUserDao() {
        return userDao;
    }

    public AccessTokenDao getAccessTokenDao() {
        return accessTokenDao;
    }

    public CategoryDao getCategoryDao() {
        return categoryDao;
    }

    public ProductDao getProductDao() {
        return productDao;
    }

}
