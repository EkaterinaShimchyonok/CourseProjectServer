package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseManager {
    public static Connection connection = null;

    public void connect() throws SQLException {

        connection = DriverManager
                .getConnection("jdbc:sqlite:E:\\BSUIR\\0 КУРСОВЫЕ\\КР5 Java\\Products.db");
        if (connection == null){
            throw new SQLException();
        }
        else
            System.out.println("Драйвер jdbc-sqlite определён.");
    }


    public static void close() {
        try {
            if(connection != null) {
                connection.close();
                System.out.println("Разорвано соединение с базой данных");
            }
        } catch (SQLException e) {
            System.out.println("Не получилось разорвать соединение с базой данных");
        }
    }

    public Connection getConnection() {
        return connection;
    }

    // Дополнительные методы для выполнения SQL-запросов
    public String addUser(String email, String password) {
        String sql = "INSERT INTO User(email, password, admin) VALUES(?, ?, 0)";
        try {
            connection.setAutoCommit(false); // Включение транзакции
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, email);
                pstmt.setString(2, password);
                pstmt.executeUpdate();
                connection.commit(); //Коммит транзакции
                connection.setAutoCommit(true);
                return "Регистрация прошла успешно. Войдите в аккаунт " + email;
            } catch (SQLException e) {
                connection.rollback(); // Откат транзакции при ошибке
                connection.commit();
                connection.setAutoCommit(true);
                return "Вы уже зарегистрированы в системе. Войдите в аккаунт " + email;
            }
        } catch (SQLException e) {
            return "ошибка Не удалось зарегистрироваться.";
        }
    }
}
