package org.example.CommandPattern;

import org.example.action.CategoryAction;
import org.example.action.ProductAction;
import org.example.action.UserAction;

public class CommandFactory {
    private UserAction userAction;
    private ProductAction productAction;
    private CategoryAction categoryAction;

    public CommandFactory() {
        this.userAction = new UserAction();
        this.productAction = new ProductAction();
        this.categoryAction = new CategoryAction();
    }

    public Command createCommand(String[] parts) {
        String entity = parts[0];
        String method = parts[1];

        switch (entity) {
            case "user":
                return new UserCommand(userAction, method);
            case "product":
                return new ProductCommand(productAction, method);
            case "category":
                return new CategoryCommand(categoryAction, method);
            default:
                return null; // Вернуть null или специальную команду для обработки неизвестных запросов
        }
    }
}
