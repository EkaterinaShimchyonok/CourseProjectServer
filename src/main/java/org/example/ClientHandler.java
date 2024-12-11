package org.example;

import org.example.CommandPattern.Command;
import org.example.CommandPattern.CommandFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    static private Socket clientSocket;
    private CommandFactory commandFactory;

    public ClientHandler(Socket socket) {
        clientSocket = socket;
        this.commandFactory = new CommandFactory();
    }

    static public Socket getClientSocket() {
        return clientSocket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                String[] parts = inputLine.split(";");
                Command command = commandFactory.createCommand(parts);

                if (command != null) {
                    command.execute(parts[2], out);
                } else {
                    out.println("Неизвестная команда");
                }
            }
        } catch (IOException e) {
            System.out.println("Клиент отключился: " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());
        }
    }
}
