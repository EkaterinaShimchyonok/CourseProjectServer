package org.example.action;

import com.google.gson.Gson;
import org.example.ClientHandler;
import org.example.POJO.Category;
import org.example.dao.CategoryDao;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class CategoryAction {
    CategoryDao dao;
    int st;
    Socket clientSocket;

    public CategoryAction() {
        clientSocket = ClientHandler.getClientSocket();
        dao = new CategoryDao();
    }

    public void insert(String jsonCategory, PrintWriter out) {
        Gson gson = new Gson();
        Category category = gson.fromJson(jsonCategory, Category.class);
        st = dao.insert(category);
        if (st == 1)
            out.println("Категория успешно добавлена: " + category.getName());
        else
            out.println("Ошибка: не удалось добавить категорию.");

        System.out.println(clientSocket.getInetAddress() + ":" + clientSocket.getPort() +
                " Добавление категории " + category.getCategoryID());
    }

    public void update(String jsonCategory, PrintWriter out) {
        Gson gson = new Gson();
        Category category = gson.fromJson(jsonCategory, Category.class);
        System.out.println(jsonCategory);
        st = dao.update(category);
        if (st == 1)
            out.println("Категория успешно обновлена");
        else
            out.println("Не удалось обновить категорию");
        System.out.println(clientSocket.getInetAddress() + ":" + clientSocket.getPort() +
                " Изменение категории " + category.getCategoryID());
    }


    public void delete(String id, PrintWriter out) {
        Category category = dao.fetchById(Integer.parseInt(id));
        st = dao.delete(category);
        if (st == 1)
            out.println("Категория успешно удалена: " + category.getName());
        else
            out.println("Не удалось удалить категорию");
        System.out.println(clientSocket.getInetAddress() + ":" + clientSocket.getPort() +
                " Удаление категории " + id);
    }


    public void fetchAll(PrintWriter out) {
        List<Category> categoryList = dao.fetchAll();
        Gson gson = new Gson();
        categoryList.forEach(category -> {
            String categoryJson = gson.toJson(category);
            out.println(categoryJson);
        });
        out.println("end"); // Указываем конец передачи
    }


    public void fetchNames(PrintWriter out) {
        List<String> categoryNames = dao.fetchCategoryNames();
        categoryNames.forEach(out::println);
        out.println("end");
    }
}
