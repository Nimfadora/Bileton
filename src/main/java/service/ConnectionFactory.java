package service;

import java.sql.*;

public class ConnectionFactory {
    //static reference to itself
    private static ConnectionFactory instance = new ConnectionFactory();
    public static final String URL = "jdbc:mariadb://localhost/theatre";
    public static final String LOGIN = "root";
    public static final String PASSWORD = "123456";
    public static final String DRIVER_CLASS = "org.mariadb.jdbc.Driver";

    //private constructor
    private ConnectionFactory() {}

    private Connection createConnection() {
        Connection connection = null;
        try {
            Class.forName(DRIVER_CLASS);
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
        } catch (SQLException e) {
            System.out.println("ERROR: Unable to Connect to Database.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static Connection getConnection() {
        return instance.createConnection();
    }
}
