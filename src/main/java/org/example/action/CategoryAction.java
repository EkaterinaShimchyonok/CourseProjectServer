package org.example.action;
import org.example.POJO.Product;
import org.example.dao.CategoryDao;
import org.example.POJO.Category;

import java.util.List;

public class CategoryAction {
    CategoryDao dao = new CategoryDao();
    int st;

    public String insert(Category category) {
        st = dao.insert(category);
        if (st == 1) {
            return "Категория успешно добавлена: " + category.getName();
        } else {
            return "Ошибка: не удалось добавить категорию.";
        }
    }

    public String update(Category category) {
        st = dao.update(category);
        if (st == 1) {
            return "Категория успешно обновлена";
        } else {
            return "Не удалось обновить категорию";
        }
    }

    public String delete(int categoryID) {
        Category category = dao.fetchById(categoryID);
        st = dao.delete(category);
        if (st == 1) {
            return "Категория успешно удалена: " + category.getName();
        } else {
            return "Не удалось удалить категорию";
        }
    }

    public void fetchById(int id) {
        Category category = dao.fetchById(id);
        if (category.getCategoryID() == 0) {
            System.out.println("Запись не найдена");
        } else {
            System.out.println("Данные категории:");
            System.out.println(category);
        }
    }

    public void fetchByName(String name) {
        Category category = dao.fetchByName(name);
        if (category == null) {
            System.out.println("Запись не найдена");
        }
    }

    public List<Category> fetchAll() {
        List<Category> categoryList = dao.fetchAll();
        if (categoryList.isEmpty()) {
            System.out.println("Запись не найдена");
        }
        return categoryList;
    }

    public List<String> fetchCategoryNames() {
        List<String> categoryNames = dao.fetchCategoryNames();
        if (categoryNames.isEmpty()) {
            System.out.println("Категории не найдены.");
        }
        return categoryNames;
    }
}
