package org.example.action;

import org.example.dao.CategoryDao;
import org.example.POJO.Category;

import java.util.List;

public class CategoryAction {
    CategoryDao dao = new CategoryDao();
    int st;

    public void insert(Category category) {
        st = dao.insert(category);
        if (st == 1) {
            System.out.println("Категория успешно добавлена");
        } else if (st == -1) {
            System.out.println("Категория уже существует");
        } else {
            System.out.println("Не удалось добавить категорию");
        }
    }

    public void update(Category category) {
        st = dao.update(category);
        if (st == 1) {
            System.out.println("Категория успешно обновлена");
        } else {
            System.out.println("Не удалось обновить категорию");
        }
    }

    public void delete(Category category) {
        st = dao.delete(category);
        if (st == 1) {
            System.out.println("Категория успешно удалена");
        } else {
            System.out.println("Запись не найдена");
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
        List<Category> categoryList = dao.fetchByName(name);
        if (categoryList.isEmpty()) {
            System.out.println("Запись не найдена");
        } else {
            System.out.println("Данные категорий:");
            for (Category category : categoryList) {
                System.out.println(category);
            }
        }
    }

    public void fetchAll() {
        List<Category> categoryList = dao.fetchAll();
        if (categoryList.isEmpty()) {
            System.out.println("Запись не найдена");
        } else {
            System.out.println("Данные категорий:");
            for (Category category : categoryList) {
                System.out.println(category);
            }
        }
    }

    public List<String> fetchCategoryNames() {
        List<String> categoryNames = dao.fetchCategoryNames();
        if (categoryNames.isEmpty()) {
            System.out.println("Категории не найдены.");
        } else {
            System.out.println("Названия категорий:");
            for (String name : categoryNames) {
                System.out.println(name);
            }
        }
        return categoryNames;
    }
}
