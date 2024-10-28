
package org.example.action;

import org.example.POJO.Product;
import org.example.dao.ProductDao;

import java.util.List;

public class ProductAction {
    ProductDao dao = new ProductDao();
    int st;

    public String insert(Product product) {
        st = dao.insert(product);
        if (st == 1) {
            return "Продукт успешно добавлен: " + product.getName();
        } else {
            return "Ошибка: не удалось добавить продукт.";
        }
    }

    public void update(Product product) {
        st = dao.update(product);
        if (st == 1) {
            System.out.println("Продукт успешно обновлен: " + product.getName());
        } else {
            System.out.println("Ошибка: не удалось обновить продукт.");
        }
    }

    public void delete(Product product) {
        st = dao.delete(product);
        if (st == 1) {
            System.out.println("Продукт успешно удален: " + product.getName());
        } else {
            System.out.println("Ошибка: запись не найдена.");
        }
    }

    public void fetchById(int id) {
        Product product = dao.fetchById(id);
        if (product.getProductID() == 0) {
            System.out.println("Запись не найдена.");
        } else {
            System.out.println("Данные продукта:");
            System.out.println(product);
        }
    }

    public void fetchByName(String name) {
        List<Product> productList = dao.fetchByName(name);
        if (productList.isEmpty()) {
            System.out.println("Запись не найдена.");
        } else {
            System.out.println("Продукты отправлены");
        }
    }

    public List<Product> fetchAll() {
        List<Product> productList = dao.fetchAll();
        if (productList.isEmpty()) {
            System.out.println("Записи не найдены.");
        } else {
            System.out.println("Данные продуктов:");
            for (Product product : productList) {
                System.out.println(product);
            }
        }
        return productList;
    }
    public List<Product> fetchByCategory(String category) {
        List<Product> productList = dao.fetchByCategory(category);
        if (productList.isEmpty()) {
            System.out.println("Записи не найдены.");
        } else {
            System.out.println("Данные продуктов:");
            for (Product product : productList) {
                System.out.println(product);
            }
        }
        return productList;
    }

}

