package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:E:\\BSUIR\\0 КУРСОВЫЕ\\КР5 Java\\Products.db";
    static Connection connection;

    private DatabaseManager() {
        // Конструктор закрыт, чтобы предотвратить создание новых экземпляров
    }

    public static synchronized Connection getInstance() {
        try {
            connection = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void close() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Разорвано соединение с базой данных");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
