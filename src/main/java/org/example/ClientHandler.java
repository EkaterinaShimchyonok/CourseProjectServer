package org.example;

import com.google.gson.Gson;
import org.example.POJO.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final DatabaseManager dbManager;

    public ClientHandler(Socket clientSocket, DatabaseManager dbManager) {
        this.clientSocket = clientSocket;
        this.dbManager = dbManager;
    }

    void register(String[] req_parts, PrintWriter out) {
        String email = req_parts[1];
        String password = req_parts[2];
        String result = dbManager.addUser(email, password);
        out.println(result);
        System.out.println(clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " Сообщение: " + result);
    }

    void login(String[] req_parts, PrintWriter out) {
        String email = req_parts[1];
        String password = req_parts[2];
        User user = dbManager.findUser(email, password);
        if(user!=null) {
            String userJson = new Gson().toJson(user);
            out.println(userJson);
        }
        else
            out.println("Не удалось войти в систему. Попробуйте ещё раз");
        System.out.println(clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " Вход в систему: " + email);
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                String[] parts = inputLine.split(";");
                if (parts[0].equals("register"))
                    register(parts, out);
                if (parts[0].equals("login"))
                    login(parts, out);
            }
        } catch (IOException e) {
            System.out.println("Отключился клиент " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());
        }
    }
}
