package com.example.securaudit.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe de gestion d'accès à la base de données.
 */
public class DatabaseAccess implements AutoCloseable {
    private static final String HOST = "jdbc:mysql://localhost:3306/securaudit?connectTimeout=3000&useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "toto1234";
    private static DatabaseAccess instance = null;
    private Connection connection;

    private DatabaseAccess() {
        createConnection();
    }

    /**
     * Récupère une instance de DatabaseAccess (initie une connexion à la base de données si pas déjà connectée).
     * @return Un DatabaseAccess avec une connexion active à la base de données
     */
    public static DatabaseAccess getInstance() {
        if (instance == null) {
            instance = new DatabaseAccess();
        }
        return instance;
    }

    /**
     * Initie une connexion à la base de données.
     */
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

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
