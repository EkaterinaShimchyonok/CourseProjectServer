package org.example.CommandPattern;

import org.example.action.PlanAction;

import java.io.PrintWriter;

public class PlanCommand implements Command {
    private final PlanAction planAction;
    private final String method;

    public PlanCommand(PlanAction planAction, String method) {
        this.planAction = planAction;
        this.method = method;
    }

    @Override
    public void execute(String param, PrintWriter out) {
        switch (method) {
            case "fetch":
                planAction.fetch(param, out);
                break;
//            case "fetchall":
//                planAction.fetchAll(out);
//                break;
            case "update":
                planAction.update(param, out);
                break;
            default:
                out.println("Неизвестный метод продукта");
        }
    }
}