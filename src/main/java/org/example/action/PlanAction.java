package org.example.action;

import com.google.gson.Gson;
import org.example.ClientHandler;
import org.example.POJO.FoodPlan;
import org.example.POJO.Product;
import org.example.dao.PlanDao;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

public class PlanAction {
    PlanDao dao;
    int st;
    Socket clientSocket;

    public PlanAction() {
        clientSocket = ClientHandler.getClientSocket();
        dao = new PlanDao();
    }

    public void fetch(String req, PrintWriter out) {
        String[] req_parts = req.split(",");
        FoodPlan plan = dao.fetchByIdDate(Integer.parseInt(req_parts[0]), req_parts[1]);
        String planJson = new Gson().toJson(plan);
        out.println(planJson);
        System.out.println(planJson);
        System.out.println(clientSocket.getInetAddress() + ":" + clientSocket.getPort() +
                " Получение плана питания пользователя: " + req_parts[0]);
    }

    public void insert(String req, PrintWriter out) {
        FoodPlan plan = dao.insert(Integer.parseInt(req));
        String planJson = new Gson().toJson(plan);
        out.println(planJson);
        System.out.println(planJson);
        System.out.println(clientSocket.getInetAddress() + ":" + clientSocket.getPort() +
                " Добавление актуального плана питания пользователя: " + req);
    }

    public void fetchCalories(String req, PrintWriter out) {
        Map<Double, String> map = dao.fetchCalories(Integer.parseInt(req));
        System.out.println(map);
        // Отправка калорий
        for (Map.Entry<Double, String> entry : map.entrySet()) {
            out.println(entry.getKey());
        }
        out.println("cal end");

        // Отправка дат
        for (Map.Entry<Double, String> entry : map.entrySet()) {
            out.println(entry.getValue());
        }
        out.println("dat end");

        System.out.println(clientSocket.getInetAddress() + ":" + clientSocket.getPort() +
                " Получение общей калорийности по дням пользователя " + req);
    }

    public void update(String jsonPlan, PrintWriter out) {
        Gson gson = new Gson();
        FoodPlan plan = gson.fromJson(jsonPlan, FoodPlan.class);
        st = dao.update(plan);
        if (st > -1) {
            out.println("План сохранён" );
        } else
            out.println("Ошибка: не удалось сохранить изменения.");
        System.out.println(clientSocket.getInetAddress() + ":" + clientSocket.getPort() +
                " Сохранение плана питания " + plan.getPlanID());
    }

}
