package com.example.securaudit.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseAccess implements AutoCloseable {
    private static final String HOST = "jdbc:mysql://localhost:3306/securaudit?connectTimeout=3000&useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "Ifen76srfc35";
    private Connection connection;

    private static DatabaseAccess instance = null;


    private DatabaseAccess() {
        createConnection();
    }

    private void createConnection() {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            connection = DriverManager.getConnection(HOST, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Connection getConnection() {
        return connection;
    }

    public static DatabaseAccess getInstance() {
        if (instance == null) {
            instance = new DatabaseAccess();
        }
        return instance;
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
