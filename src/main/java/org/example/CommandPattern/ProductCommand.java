package org.example.CommandPattern;

import org.example.action.ProductAction;

import java.io.PrintWriter;

public class ProductCommand implements Command {
    private final ProductAction productAction;
    private final String method;

    public ProductCommand(ProductAction productAction, String method) {
        this.productAction = productAction;
        this.method = method;
    }

    @Override
    public void execute(String param, PrintWriter out) {
        switch (method) {
            case "fetchcat":
                productAction.fetchByCategory(param, out);
                break;
            case "update":
                productAction.update(param, out);
                break;
            case "delete":
                productAction.delete(param, out);
                break;
            case "add":
                productAction.insert(param, out);
                break;
            default:
                out.println("Неизвестный метод продукта");
        }
    }
}