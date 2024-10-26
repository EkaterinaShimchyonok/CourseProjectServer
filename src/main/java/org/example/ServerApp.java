package org.example;

public class ServerApp {
    public static void main(String[] args) {

        // Проверка соединения с базой данных
        if (DatabaseManager.getInstance() != null) {
            System.out.println("Соединение с базой данных установлено.");
        } else {
            System.out.println("Не удалось подключиться к базе данных.");
        }

        Server server = new Server();
        server.start();

        //DatabaseManager.close();
    }
}