package org.example.CommandPattern;

import org.example.action.CategoryAction;

import java.io.PrintWriter;

public class CategoryCommand implements Command {
    private final CategoryAction categoryAction;
    private final String method;

    public CategoryCommand(CategoryAction categoryAction, String method) {
        this.categoryAction = categoryAction;
        this.method = method;
    }

    @Override
    public void execute(String param, PrintWriter out) {
        switch (method) {
            case "update":
                categoryAction.update(param, out);
                break;
            case "delete":
                categoryAction.delete(param, out);
                break;
            case "add":
                categoryAction.insert(param, out);
                break;
            case "fetchall":
                categoryAction.fetchAll(out);
                break;
            case "fetchnames":
                categoryAction.fetchNames(out);
                break;
            default:
                out.println("Неизвестный метод категории");
        }
    }
}