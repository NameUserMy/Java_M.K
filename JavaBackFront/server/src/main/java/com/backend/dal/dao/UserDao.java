package com.backend.dal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;
import java.util.logging.Logger;

import com.backend.dal.dto.User;
import com.backend.models.UserSignUpFormModel;
import com.backend.services.db.DbService;
import com.backend.services.kdf.KdfService;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class UserDao {

    private final Connection connection;
    private final Logger logger;
    private final KdfService kdfService;

    @Inject
    public UserDao(KdfService kdfService, DbService dbService, Logger logger) throws SQLException {
        this.connection = dbService.getConnection();
        this.logger = logger;
        this.kdfService=kdfService;
    }

    public boolean installTables() {

        return installUsers() && installUsersAccess() && installRole();

    }

    public User addUser(UserSignUpFormModel userModel)  {

        User user = new User();
        user.setUserId(UUID.randomUUID());
        user.setEmil(userModel.getEmail());
        user.setName(userModel.getName());
        user.setPhone(userModel.getPhone());

        String sql = "INSERT INTO users (userId, name, email, phone)" +

                "VALUES(?, ?, ?, ?)";
                
        try (PreparedStatement prep = this.connection.prepareStatement(sql)) {
            this.connection.setAutoCommit(false);
            prep.setString(1, user.getUserId().toString());
            prep.setString(2, user.getName());
            prep.setString(3, user.getEmil());
            prep.setString(4, user.getPhone());
            
            prep.executeUpdate();

        } catch (SQLException ex) {

            logger.warning("UserDao::addUser 1 "+ex.getMessage());
            try {
                this.connection.rollback();
            } catch (SQLException e) {
                
                e.printStackTrace();
            }
            return null;

        }

        sql = "INSERT INTO user_access (user_access_id, user_id, role_id, login, salt, dk )" +

                "VALUES(UUID(),?,'guest', ?, ?, ?)";
        try (PreparedStatement prep = this.connection.prepareStatement(sql)) {

            prep.setString(1, user.getUserId().toString());
            prep.setString(2, user.getEmil());
            String salt=UUID.randomUUID().toString().substring(0,16);
            prep.setString(3, salt);
            prep.setString(4, kdfService.dk(userModel.getPassword(), salt));
            prep.executeUpdate();
            this.connection.commit();

        } catch (SQLException ex) {

            logger.warning("UserDao::addUser 2 "+ex.getMessage());
            try {
                this.connection.rollback();
            } catch (SQLException e) {
               
                e.printStackTrace();
            }
            return null;

        }

        return user;
    }

    private boolean installRole() {

        String sql = "CREATE TABLE IF NOT EXISTS users_roles("
                + "rolesId  CHAR(36)     PRIMARY KEY DEFAULT( UUID() ),"
                + "user_id  CHAR(36)       NOT NULL,"
                + "role   VARCHAR(16)      NOT NULL,"
                + "canCreate INT DEFAULT(false),"
                + "canRead   INT DEFAULT(false),"
                + "canUpdate INT DEFAULT(false),"
                + "canDelete INT DEFAULT(false),"
                + "description   VARCHAR(256)     NULL"
                + ") Engine = InnoDB, DEFAULT CHARSET = utf8mb4";
        try (Statement statement = connection.createStatement()) {

            statement.executeUpdate(sql);
            logger.info("installRole Ok");
            return true;

        } catch (SQLException ex) {

            logger.warning("UserDao::installRole " + ex.getCause());
            return false;

        }
    }

    private boolean installUsersAccess() {

        String sql = "CREATE TABLE IF NOT EXISTS user_access("
                + "user_access_id  CHAR(36)     PRIMARY KEY DEFAULT( UUID() ),"
                + "user_id  CHAR(36)       NOT NULL,"
                + "login    VARCHAR(128)   NOT NULL,"
                + "salt   VARCHAR(16)      NOT  NULL,"
                + "role_id   VARCHAR(16)   NOT  NULL,"
                + "dk   VARCHAR(20)        NOT NULL,"
                + "UNIQUE(login)"
                + ") Engine = InnoDB, DEFAULT CHARSET = utf8mb4";

        try (Statement statement = connection.createStatement()) {

            statement.executeUpdate(sql);
            logger.info("installUsersAccess Ok");
            return true;

        } catch (SQLException ex) {

            logger.warning("UserDao::installUsersAccess " + ex.getCause());
            return false;

        }

    }

    private boolean installUsers() {

        String sql = "CREATE TABLE IF NOT EXISTS users("
                + "userId  CHAR(36)     PRIMARY KEY DEFAULT( UUID() ),"
                + "name    VARCHAR(128) NOT NULL,"
                + "email   VARCHAR(256)     NULL,"
                + "phone   VARCHAR(32)      NULL"
                + ") Engine = InnoDB, DEFAULT CHARSET = utf8mb4";

        try (Statement statement = connection.createStatement()) {

            statement.executeUpdate(sql);
            logger.info("installUsers Ok");
            return true;

        } catch (SQLException ex) {

            logger.warning("UserDao::installUsers " + ex.getCause());
            return false;

        }

    }

}
