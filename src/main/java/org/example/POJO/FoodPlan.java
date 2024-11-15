package org.example.POJO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FoodPlan {
    int planID;
    int userID;
    Nutrients norm;
    ArrayList<Product> products;
    ArrayList<Double> weights;
    String date;
    double totalCal;

    public FoodPlan() {
        planID = 0;
        norm = new Nutrients();
        products = new ArrayList<>();
        weights = new ArrayList<>();
        Date today = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        date = formatter.format(today);
    }

    public FoodPlan(int uid)
    {
        this.userID = uid;
        Date today = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        date = formatter.format(today);
        products = new ArrayList<>();
        weights = new ArrayList<>();
    }

    public int getPlanID() {
        return planID;
    }

    public void setPlanID(int planID) {
        this.planID = planID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public Nutrients getNorm() { return norm; }

    public void setNorm(Nutrients norm) { this.norm = norm; }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getTotalCal() {
        return totalCal;
    }

    public void setTotalCal(double totalCal) {
        this.totalCal = totalCal;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public ArrayList<Double> getWeights() {
        return weights;
    }

    public void setWeights(ArrayList<Double> weights) {
        this.weights = weights;
    }
}
