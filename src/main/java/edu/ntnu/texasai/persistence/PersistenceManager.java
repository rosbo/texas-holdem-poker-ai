package edu.ntnu.texasai.persistence;

import java.sql.Connection;
import java.sql.DriverManager;

public class PersistenceManager {
    private final Connection connection;

    public PersistenceManager() {
        connection = createConnection();
    }

    public Connection getConnection() {
        return connection;
    }

    private Connection createConnection() {
        try {
            Class.forName("org.h2.Driver");
            return DriverManager.getConnection("jdbc:h2:data/data", "sa", "");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }
}
