
package org.example.action;

import com.google.gson.Gson;
import org.example.ClientHandler;
import org.example.POJO.Product;
import org.example.dao.ProductDao;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ProductAction {
    ProductDao dao;
    int st;
    Socket clientSocket;

    public ProductAction() {
        clientSocket = ClientHandler.getClientSocket();
        dao = new ProductDao();
    }

    public void insert(String jsonProduct, PrintWriter out) {
        Gson gson = new Gson();
        Product product = gson.fromJson(jsonProduct, Product.class);
        st = dao.insert(product);
        if (st == 1)
            out.println("Продукт успешно добавлен: " + product.getName());
        else
            out.println("Ошибка: не удалось добавить продукт.");
        System.out.println(clientSocket.getInetAddress() + ":" + clientSocket.getPort() +
                " Добавление продукта " + product.getProductID());
    }

    public void update(String jsonProduct, PrintWriter out) {
        Gson gson = new Gson();
        Product product = gson.fromJson(jsonProduct, Product.class);
        st = dao.update(product);
        if (st == 1) {
            out.println("Продукт успешно обновлен: " + product.getName());
        } else
            out.println("Ошибка: не удалось обновить продукт.");
        System.out.println(clientSocket.getInetAddress() + ":" + clientSocket.getPort() +
                " Изменение продукта " + product.getProductID());
    }

    public void delete(String id, PrintWriter out) {
        Product product = dao.fetchById(Integer.parseInt(id));
        st = dao.delete(product);
        if (st == 1) {
            out.println("Продукт успешно удален: " + product.getName());
        } else
            out.println("Не удалось удалить продукт");
        System.out.println(clientSocket.getInetAddress() + ":" + clientSocket.getPort() +
                " Удаление продукта " + id);
    }

    public void fetchAll(PrintWriter out) {
        List<Product> products = dao.fetchAll();
        Gson gson = new Gson();
        products.forEach(product -> {
            String productJson = gson.toJson(product);
            out.println(productJson);
        });
        out.println("end"); // Указываем конец передачи
    }

    public void fetchByCategory(String category, PrintWriter out) {
        List<Product> products;
        if (category.equals("Все"))
            products = dao.fetchAll();
        else
            products = dao.fetchByCategory(category);
        Gson gson = new Gson();
        products.forEach(product -> {
            String productJson = gson.toJson(product);
            out.println(productJson);
        });
        out.println("end"); // Указываем конец передачи
    }

}

