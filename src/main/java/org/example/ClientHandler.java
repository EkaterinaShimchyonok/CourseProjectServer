package org.example;

import com.google.gson.Gson;
import org.example.POJO.User;
import org.example.action.CategoryAction;
import org.example.action.ProductAction;
import org.example.action.UserAction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket clientSocket; //только для подключения и закрытия соединения

    UserAction uact = new UserAction();
    CategoryAction cact = new CategoryAction();
    ProductAction pact = new ProductAction();

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    void register(String[] req_parts, PrintWriter out) {
        User user = new User(req_parts[1], req_parts[2], false);
        String result = uact.insert(user);
        out.println(result);
        System.out.println(clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " Сообщение: " + result);
    }

    void login(String[] req_parts, PrintWriter out) {
        User user = uact.fetchByEmailPassw(req_parts[1], req_parts[2]);
        if (user != null) {
            String userJson = new Gson().toJson(user);
            out.println(userJson);
        } else
            out.println("Не удалось войти в систему. Попробуйте ещё раз");
        System.out.println(clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " Вход в систему: " + req_parts[1]);
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
