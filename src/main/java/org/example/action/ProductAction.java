package org.example.action;

import org.example.dao.ProductDao;
import org.example.POJO.Product;

import java.util.List;

public class ProductAction {
    ProductDao dao = new ProductDao();
    int st;

    public void insert(Product product) {
        st = dao.insert(product);
        if (st == 1) {
            System.out.println("Продукт успешно добавлен");
        } else if (st == -1) {
            System.out.println("Продукт уже существует");
        } else {
            System.out.println("Не удалось добавить продукт");
        }
    }

    public void update(Product product) {
        st = dao.update(product);
        if (st == 1) {
            System.out.println("Продукт успешно обновлен");
        } else {
            System.out.println("Не удалось обновить продукт");
        }
    }

    public void delete(Product product) {
        st = dao.delete(product);
        if (st == 1) {
            System.out.println("Продукт успешно удален");
        } else {
            System.out.println("Запись не найдена");
        }
    }

    public void fetchById(int id) {
        Product product = dao.fetchById(id);
        if (product.getProductID() == 0) {
            System.out.println("Запись не найдена");
        } else {
            System.out.println("Данные продукта:");
            System.out.println(product);
        }
    }

    public void fetchByName(String name) {
        List<Product> productList = dao.fetchByName(name);
        if (productList.isEmpty()) {
            System.out.println("Запись не найдена");
        } else {
            System.out.println("Данные продуктов:");
            for (Product product : productList) {
                System.out.println(product);
            }
        }
    }

    public void fetchByCategory(int categoryId) {
        List<Product> productList = dao.fetchByCategory(categoryId);
        if (productList.isEmpty()) {
            System.out.println("Запись не найдена");
        } else {
            System.out.println("Данные продуктов:");
            for (Product product : productList) {
                System.out.println(product);
            }
        }
    }

    public void fetchAll() {
        List<Product> productList = dao.fetchAll();
        if (productList.isEmpty()) {
            System.out.println("Запись не найдена");
        } else {
            System.out.println("Данные продуктов:");
            for (Product product : productList) {
                System.out.println(product);
            }
        }
    }
}
