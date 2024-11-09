package org.example.CommandPattern;

import org.example.action.UserAction;

import java.io.PrintWriter;

public class UserCommand implements Command {
    private final UserAction userAction;
    private final String method;

    public UserCommand(UserAction userAction, String method) {
        this.userAction = userAction;
        this.method = method;
    }

    @Override
    public void execute(String param, PrintWriter out) {
        switch (method) {
            case "register":
                userAction.register(param, out);
                break;
            case "login":
                userAction.login(param, out);
                break;
            case "fetchall":
                userAction.fetchAll(out);
                break;
            case "updateadmin":
                userAction.updateAdmin(param, out);
                break;
            case "update":
                userAction.update(param, out);
                break;
            case "delete":
                userAction.delete(param, out);
                break;
            default:
                out.println("Неизвестный метод пользователя");
        }
    }
}