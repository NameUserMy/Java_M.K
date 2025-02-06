package com.backend.services.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.google.inject.Singleton;

@Singleton
public class MySqlDbService implements DbService {

    private Connection connection;

    @Override
    public Connection getConnection() throws SQLException {

        if (connection == null) {

            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            String connectionString = "jdbc:mysql://localhost:3308/javaServlet";
            connection = DriverManager.getConnection(connectionString, "userJavaServlet", "java");

        }
        return connection;
    }

}
