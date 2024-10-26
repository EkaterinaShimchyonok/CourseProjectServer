package org.example.action;

import org.example.dao.UserDao;
import org.example.POJO.User;
import java.util.List;

public class UserAction {
    UserDao dao = new UserDao();
    int st;

    public String insert(User user) {
        st = dao.insert(user);
        if (st == 1) {
            return "Регистрация прошла успешно. Войдите в аккаунт " + user.getEmail();
        } else if (st == -1) {
            return "Вы уже зарегистрированы в системе. Войдите в аккаунт " + user.getEmail();
        } else {
            return "ошибка Не удалось зарегистрироваться.";
        }
    }

    public void update(User user) {
        st = dao.update(user);
        if (st == 1) {
            System.out.println("Пользователь успешно обновлен");
        } else {
            System.out.println("Не удалось обновить пользователя");
        }
    }

    public void delete(User user) {
        st = dao.delete(user);
        if (st == 1) {
            System.out.println("Пользователь успешно удален");
        } else {
            System.out.println("Запись не найдена");
        }
    }

    public void fetchById(int id) {
        User user = dao.fetchById(id);
        if (user.getUserID() == 0) {
            System.out.println("Запись не найдена");
        } else {
            System.out.println("Данные пользователя:");
            System.out.println(user);
        }
    }

    public void fetchByName(String name) {
        List<User> userList = dao.fetchByName(name);
        if (userList.isEmpty()) {
            System.out.println("Запись не найдена");
        } else {
            System.out.println("Данные пользователей:");
            for (User user : userList) {
                System.out.println(user);
            }
        }
    }

    public User fetchByEmailPassw(String email, String passw) {
        User user = dao.fetchByEmailPassw(email, passw);
        return user;
    }

    public void fetchByAdmin(Boolean admin) {
        List<User> userList = dao.fetchByAdmin(admin);
        if (userList.isEmpty()) {
            System.out.println("Запись не найдена");
        } else {
            System.out.println("Данные пользователей:");
            for (User user : userList) {
                System.out.println(user);
            }
        }
    }

    public List<User> fetchAll() {
        List<User> userList = dao.fetchAll();
        if (userList.isEmpty()) {
            System.out.println("Запись не найдена");
        } else {
            System.out.println("Данные пользователей:");
            for (User user : userList) {
                System.out.println(user);
            }
        }
        return userList;
    }
}
