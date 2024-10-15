package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private DatabaseManager dbManager;

    public ClientHandler(Socket clientSocket, DatabaseManager dbManager) {
        this.clientSocket = clientSocket;
        this.dbManager = dbManager;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                String[] parts = inputLine.split(";");
                if (parts[0].equals("register")) {
                    String email = parts[1];
                    String password = parts[2];
                    String result = dbManager.addUser(email, password);
                    out.println(result);
                    System.out.println(clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " Сообщение: " + result);
                }
            }
        } catch (IOException e) {
            System.out.println("Отключился клиент " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());
        }
    }
}
