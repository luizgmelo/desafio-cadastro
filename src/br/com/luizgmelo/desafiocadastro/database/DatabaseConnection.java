package br.com.luizgmelo.desafiocadastro.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection connect() {
        var jdbcUrl = DatabaseConfig.getDbUrl();
        var jdbcUsername = DatabaseConfig.getDbUsername();
        var jdbcPassword = DatabaseConfig.getDbPassword();

        try {
            return DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
