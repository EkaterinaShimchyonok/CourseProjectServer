package org.example;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Server {
    private static final int PORT = 5000;
    private DatabaseManager dbManager;

    public Server(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.print("Сервер запущен: ");
            System.out.print("IP-адрес " + InetAddress.getLocalHost().getHostAddress());
            System.out.println(" Порт " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Подключился клиент " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());
                new Thread(new ClientHandler(clientSocket, dbManager)).start();
            }
        } catch (SocketException e) {
            System.out.println("Клиент оборвал соединение: " + e.getMessage());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

