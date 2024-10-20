package org.example;

import java.sql.*;

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
    public String findUser(String email, String password) {
        {
            String sql = "SELECT * FROM User WHERE email = ? AND password = ?";

            try {
                connection.setAutoCommit(false); // Включение транзакции
                Savepoint point1 = connection.setSavepoint("Savepoint1");
                try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                    pstmt.setString(1, email);
                    pstmt.setString(2, password);
                    ResultSet rs = pstmt.executeQuery();

                    if (rs.next()) {
                        // Если пользователь найден, возвращаем данные пользователя (например, email и роль)
                        String foundEmail = rs.getString("email");
                        int role = rs.getInt("admin");
                        connection.commit(); // Коммит транзакции
                        connection.setAutoCommit(true);
                        return "Пользователь найден: " + foundEmail + " с ролью " + role;
                    } else {
                        connection.commit(); // Коммит транзакции
                        connection.setAutoCommit(true);
                        return "Пользователь с email " + email + " и указанным паролем не найден.";
                    }
                } catch (SQLException e) {
                    connection.rollback(point1); // Откат транзакции при ошибке
                    connection.commit();
                    connection.setAutoCommit(true);
                    return "Ошибка при поиске пользователя: " + e.getMessage();
                }
            } catch (SQLException e) {
                return "Ошибка: Не удалось выполнить поиск.";
            }
        }
    }
}
