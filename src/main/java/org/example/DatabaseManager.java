package org.example;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseManager {
    private static HikariDataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:sqlite:E:\\BSUIR\\0 КУРСОВЫЕ\\КР5 Java\\Products.db");
        config.setMaximumPoolSize(10); // Установи нужное количество соединений в пуле
        dataSource = new HikariDataSource(config);
    }

    private DatabaseManager() {
        // Конструктор закрыт, чтобы предотвратить создание новых экземпляров
    }

    public static synchronized Connection getInstance() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void close() {
        if (dataSource != null) {
            dataSource.close();
            System.out.println("Разорвано соединение с базой данных");
        }
    }
}
