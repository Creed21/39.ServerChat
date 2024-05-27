package service;

import dbManager.DBManager;
import model.User;

import java.sql.SQLException;

public class LoginService {

    private DBManager dbManager;

    public LoginService() {
        this.dbManager = DBManager.getInstance();
    }

    public synchronized User login(String username, String password) {
        try {
            return dbManager.login(username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
