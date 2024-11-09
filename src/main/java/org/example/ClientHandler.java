package org.example;

import com.google.gson.Gson;
import org.example.POJO.Category;
import org.example.POJO.Product;
import org.example.POJO.User;
import org.example.action.CategoryAction;
import org.example.action.ProductAction;
import org.example.action.UserAction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable {
    private final Socket clientSocket; // только для подключения и закрытия соединения
    public UserAction uact = new UserAction();
    CategoryAction cact = new CategoryAction();
    ProductAction pact = new ProductAction();

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void register(String[] req_parts, PrintWriter out) {
        User user = new User(req_parts[1], req_parts[2], false);
        String result = uact.insert(user);
        out.println(result);
        System.out.println(clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " Сообщение: " + result);
    }

    void login(String[] req_parts, PrintWriter out) {
        User user = uact.fetchByEmailPassw(req_parts[1], req_parts[2]);
        if (user.getUserID() != 0) {
            String userJson = new Gson().toJson(user);
            out.println(userJson);
            System.out.println(userJson);
        } else {
            out.println("Не удалось войти в систему. Попробуйте ещё раз");
        }
        System.out.println(clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " Вход в систему: " + req_parts[1]);
    }

    void fetchAllUsers(PrintWriter out) {
        List<User> users = uact.fetchAll();
        Gson gson = new Gson();
        users.forEach(user -> {
            String userJson = gson.toJson(user);
            out.println(userJson);
        });
        out.println("end"); // Указываем конец передачи
    }

    void fetchAllCategories(PrintWriter out) {
        List<Category> cats = cact.fetchAll();
        Gson gson = new Gson();
        cats.forEach(category -> {
            String categoryJson = gson.toJson(category);
            out.println(categoryJson);
        });
        out.println("end"); // Указываем конец передачи
    }

    void fetchCategoryProducts(String category, PrintWriter out) {
        List<Product> products;
        if (category.equals("Все"))
            products = pact.fetchAll();
        else
            products = pact.fetchByCategory(category);
        Gson gson = new Gson();
        products.forEach(product -> {
            String productJson = gson.toJson(product);
            out.println(productJson);
        });
        out.println("end"); // Указываем конец передачи
    }

    void fetchCategoriesNames(PrintWriter out) {
        List<String> categoryNames = cact.fetchCategoryNames();
        categoryNames.forEach(out::println);
        out.println("end");
    }

    void updateProduct(String jsonProduct, PrintWriter out) {
        Gson gson = new Gson();
        Product product = gson.fromJson(jsonProduct, Product.class);
        System.out.println(jsonProduct);
        out.println(pact.update(product));
        System.out.println(clientSocket.getInetAddress() + ":" + clientSocket.getPort() +
                " Изменение продукта " + product.getProductID());
    }

    void updateCategory(String jsonCategory, PrintWriter out) {
        Gson gson = new Gson();
        Category category = gson.fromJson(jsonCategory, Category.class);
        System.out.println(jsonCategory);
        out.println(cact.update(category));
        System.out.println(clientSocket.getInetAddress() + ":" + clientSocket.getPort() +
                " Изменение категории " + category.getCategoryID());
    }

    void updateAdmin(String jsonUser, PrintWriter out) {
        Gson gson = new Gson();
        User user = gson.fromJson(jsonUser, User.class);
        String answer = uact.updateAdmin(user);
        out.println(answer);
        System.out.println(clientSocket.getInetAddress() + ":" + clientSocket.getPort() +
                " Обновление пользовательской информации " + user.getEmail());
    }

    void addProduct(String jsonProduct, PrintWriter out) {
        Gson gson = new Gson();
        Product product = gson.fromJson(jsonProduct, Product.class);
        System.out.println(jsonProduct);
        out.println(pact.insert(product));
        System.out.println(clientSocket.getInetAddress() + ":" + clientSocket.getPort() +
                " Добавление продукта " + product.getProductID());
    }

    void addCategory(String jsonCategory, PrintWriter out) {
        Gson gson = new Gson();
        Category category = gson.fromJson(jsonCategory, Category.class);
        System.out.println(jsonCategory);
        out.println(cact.insert(category));
        System.out.println(clientSocket.getInetAddress() + ":" + clientSocket.getPort() +
                " Добавление категории " + category.getCategoryID());
    }

    void deleteProduct(String id, PrintWriter out) {
        out.println(pact.delete(Integer.parseInt(id)));
        System.out.println(clientSocket.getInetAddress() + ":" + clientSocket.getPort() +
                " Удаление продукта " + id);
    }

    void deleteCategory(String id, PrintWriter out) {
        out.println(cact.delete(Integer.parseInt(id)));
        System.out.println(clientSocket.getInetAddress() + ":" + clientSocket.getPort() +
                " Удаление категории " + id);
    }

    void deleteUser(String id, PrintWriter out) {
        out.println(uact.delete(Integer.parseInt(id)));
        System.out.println(clientSocket.getInetAddress() + ":" + clientSocket.getPort() +
                " Удаление пользователя " + id);
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
                if (parts[0].equals("userfetchall"))
                    fetchAllUsers(out);
                if (parts[0].equals("userupdateadmin"))
                    updateAdmin(parts[1], out);
                if (parts[0].equals("userdelete"))
                    deleteUser(parts[1], out);

                if (parts[0].equals("categoryupdate"))
                    updateCategory(parts[1], out);
                if (parts[0].equals("categorydelete"))
                    deleteCategory(parts[1], out);
                if (parts[0].equals("categoryadd"))
                    addCategory(parts[1], out);
                if (parts[0].equals("categoryfetchall"))
                    fetchAllCategories(out);

                if (parts[0].equals("productfetchcat"))
                    fetchCategoryProducts(parts[1], out);
                if (parts[0].equals("productupdate"))
                    updateProduct(parts[1], out);
                if (parts[0].equals("productdelete"))
                    deleteProduct(parts[1], out);
                if (parts[0].equals("productadd"))
                    addProduct(parts[1], out);
                if (parts[0].equals("categoryfetchnames"))
                    fetchCategoriesNames(out);

            }
        } catch (IOException e) {
            System.out.println("Отключился клиент " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());
        }
    }
}
