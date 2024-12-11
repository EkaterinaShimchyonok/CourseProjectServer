package org.example.action;

import com.google.gson.Gson;
import org.example.ClientHandler;
import org.example.dao.UserDao;
import org.example.POJO.User;
import org.example.dao.UserInfoDao;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class UserAction {
    UserDao dao;
    int st;
    Socket clientSocket;

    public UserAction(){
        clientSocket = ClientHandler.getClientSocket();
        dao = new UserDao();
    }

    public void register(String req, PrintWriter out) {
        String[] req_parts = req.split(",");
        User user = new User(req_parts[0], req_parts[1], false);
        String result = this.insert(user);
        out.println(result);
        System.out.println(clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " Сообщение: " + result);
    }

    public void login(String req, PrintWriter out) {
        String[] req_parts = req.split(",");
        User user = dao.fetchByEmailPassword(req_parts[0], req_parts[1]);
        if (user.getUserID() != 0) {
            String userJson = new Gson().toJson(user);
            out.println(userJson);
            System.out.println(userJson);
        } else {
            out.println("Не удалось войти в систему. Попробуйте ещё раз");
        }
        System.out.println(clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " Вход в систему: " + req_parts[1]);
    }

    public String insert(User user) {
        st = dao.insert(user);
        if (st == 1) {
            return "Регистрация прошла успешно" ;
        } else if (st == -1) {
            return "Аккаунт " + user.getEmail()+ " уже существует.";
        } else {
            return "Не удалось зарегистрироваться.";
        }
    }

    public void update(String jsonUser, PrintWriter out) {
        Gson gson = new Gson();
        User user = gson.fromJson(jsonUser, User.class);
        System.out.println(jsonUser);

        UserInfoDao dao = new UserInfoDao();
        st = dao.update(user.getInfo());
        if (st == 1)
            out.println("Изменения успешно применены");
        else
            out.println("Не удалось обновить информацию");
        System.out.println(clientSocket.getInetAddress() + ":" + clientSocket.getPort() +
                " Обновление пользовательской информации " + user.getEmail());

    }

    public void updateAdmin(String jsonUser, PrintWriter out) {
        Gson gson = new Gson();
        User user = gson.fromJson(jsonUser, User.class);
        st = dao.updateAdmin(user);
        if (st == 1)
            out.println("Изменения успешно применены");
        else
            out.println("Не удалось обновить информацию");
        System.out.println(clientSocket.getInetAddress() + ":" + clientSocket.getPort() +
                " Обновление пользовательской информации " + user.getEmail());
    }

    public void delete(String id, PrintWriter out) {
        User user = dao.fetchById(Integer.parseInt(id));
        st = dao.delete(user);
        if (st == 1) {
            out.println("Пользователь успешно удален: " + user.getEmail());
        } else {
            out.println("Не удалось удалить пользователя");
        }
        System.out.println(clientSocket.getInetAddress() + ":" + clientSocket.getPort() +
                " Удаление пользователя " + id);
    }

    public void fetchAll(PrintWriter out) {
        List<User> userList = dao.fetchAll();
        if (userList.isEmpty()) {
            System.out.println("Запись не найдена");
        }
        Gson gson = new Gson();
        userList.forEach(user -> {
            String userJson = gson.toJson(user);
            out.println(userJson);
        });
        out.println("end"); // Указываем конец передачи
    }
}
