package pl.kubag5.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    static String dbUrl = "jdbc:mysql://localhost:3306/kolejapk";
    static String dbUser = "root";
    static String dbPassword = "";

    static Connection connection;
    static Statement statement;

    static {
        try {
            connection = DriverManager.getConnection(Database.dbUrl, Database.dbUser, Database.dbPassword);
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // TODO: static add static connection here
}
