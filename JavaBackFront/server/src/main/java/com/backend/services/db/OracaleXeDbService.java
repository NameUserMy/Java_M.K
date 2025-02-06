package com.backend.services.db;

import java.sql.Connection;
import java.sql.SQLException;

import com.google.inject.Singleton;

@Singleton
public class OracaleXeDbService implements DbService {

    private Connection connection;

    @Override
    public Connection getConnection() throws SQLException {

        if (connection == null) {

            // OracleDataSource ods = new OracleDataSource();
            // ods.setURL( "jdbc:oracle:thin:@localhost/XE" );
            // connection = ods.getConnection( "SYSTEM", "root" );

        }
        return connection;
    }

}
