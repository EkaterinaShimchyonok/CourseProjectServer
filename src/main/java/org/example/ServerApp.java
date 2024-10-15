package org.example;

import java.sql.SQLException;

public class ServerApp {
    public static void main(String[] args) {
        DatabaseManager dbManager = new DatabaseManager();

        try {
            dbManager.connect();
        } catch (SQLException e) {
            System.out.println("Не удалось подключиться к базе данных");
            return;
        }

        // Проверка соединения с базой данных
        if (dbManager.getConnection() != null) {
            System.out.println("Соединение с базой данных установлено.");
        } else {
            System.out.println("Не удалось подключиться к базе данных.");
        }

        Server server = new Server(dbManager);
        server.start();

        DatabaseManager.close();
    }
}