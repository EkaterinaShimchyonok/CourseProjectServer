package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static Connection connection;

    private DatabaseManager() {
        // Конструктор закрыт, чтобы предотвратить создание новых экземпляров
    }

    public static synchronized Connection getInstance() {
        if (connection == null) {
            connect();
        }
        return connection;
    }

    private static void connect() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:E:\\BSUIR\\0 КУРСОВЫЕ\\КР5 Java\\Products.db");
            if (connection != null) {
                System.out.println("Драйвер jdbc-sqlite определён.");
            } else {
                throw new SQLException("Не удалось установить соединение.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Разорвано соединение с базой данных");
            }
        } catch (SQLException e) {
            System.out.println("Не получилось разорвать соединение с базой данных");
        }
    }
}
