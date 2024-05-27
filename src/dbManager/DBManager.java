package dbManager;

import model.User;

import java.sql.*;

public class DBManager {

    private static DBManager INSTANCE;
    private Connection dbConnection;

    private DBManager() {
    }

    public static DBManager getInstance() {
        if(INSTANCE == null)
            INSTANCE = new DBManager();

        return INSTANCE;
    }

    public void connect() {
        String url = "jdbc:postgresql://localhost:5432/final_p";
        String username = "postgres";
        String password = "$@postgres$@";
        try {
            dbConnection = DriverManager.getConnection(url, username, password);
            dbConnection.setSchema("client_server_chat"); // ako radite sa postgresom u nekoj novoj semi koja nije public
            dbConnection.setAutoCommit(false);
        } catch (SQLException e) {
            System.out.println("Failed DB Connection");
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            dbConnection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void commit() {
        try {
            dbConnection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void rollback() {
        try {
            dbConnection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public synchronized User login(String username, String password) throws SQLException {
        connect();

        String query = """
                select firstName, lastName, username, isAdmin
                from users
                where username = ? and password = ?;""";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setFirstName(resultSet.getString("firstName"));
                user.setLastName(resultSet.getString("lastName"));
                user.setUsername(resultSet.getString("username"));
                user.setAdmin(resultSet.getBoolean("isAdmin"));

                closeConnection();

                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
        return null;
    }

}
