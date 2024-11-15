package org.example.dao;

import org.example.DatabaseManager;
import org.example.POJO.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class PlanDao {
    Connection con = DatabaseManager.getInstance();
    PreparedStatement ps = null;
    ResultSet rs = null;

    ProductDao productDao = new ProductDao();
    UserDao userDao = new UserDao();
    UserInfoDao userInfoDao = new UserInfoDao();

    int st; // статус

    public FoodPlan insert(int uid) {
        FoodPlan plan = new FoodPlan(uid);
        Statement stmt = null;
        int newId = -1;
        try {
            String query = "insert into FoodPlan(user_id, date) values(?,?)";
            ps = con.prepareStatement(query);
            ps.setInt(1, plan.getUserID());
            ps.setString(2, plan.getDate());
            ps.executeUpdate();

            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT last_insert_rowid() AS id");
            if (rs.next()) {
                newId = rs.getInt("id");
                plan.setPlanID(newId);
            }
            plan.setPlanID(newId);
            User user = userDao.fetchById(plan.getUserID());
            UserInfo userInfo = userInfoDao.fetchById(user.getUserID());
            plan.setNorm(userInfo.getNorm());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (stmt != null) stmt.close(); // Закрываем Statement
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return plan;
    }


    public FoodPlan fetchByIdDate(int uid, String date) {
        FoodPlan plan = new FoodPlan();
        try {
            String query = "select fp.plan_id as plan_id, total_cal, product_id, weight " +
                    "from FoodPlan as fp left join PlanProducts as pp on fp.plan_id=pp.plan_id " +
                    "where user_id=? and date=?";
            ps = con.prepareStatement(query);
            ps.setInt(1, uid);
            ps.setString(2, date);
            rs = ps.executeQuery();

            plan.setUserID(uid);
            plan.setDate(date);

            while (rs.next()) {
                plan.setPlanID(rs.getInt("plan_id"));
                plan.setTotalCal(rs.getDouble("total_cal"));
                plan.getProducts().add(productDao.fetchById(rs.getInt("product_id")));
                plan.getWeights().add(rs.getDouble("weight"));
            }
            System.out.println(plan.getPlanID());
            if (plan.getPlanID() != 0) {
                User user = userDao.fetchById(plan.getUserID());
                UserInfo userInfo = userInfoDao.fetchById(user.getUserID());
                plan.setNorm(userInfo.getNorm());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return plan;
    }

    public Map<Double, String> fetchCalories(int uid) {
        Map<Double, String> map = new HashMap<Double, String>();
        try {
            String query = "select  total_cal, date from FoodPlan where user_id=?";
            ps = con.prepareStatement(query);
            ps.setInt(1, uid);
            rs = ps.executeQuery();

            while (rs.next())
                map.put(rs.getDouble("total_cal"), rs.getString("date"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return map;
    }

    public int update(FoodPlan plan) {
        try {
            String query = "update FoodPlan set total_cal=? where user_id=? and date=?";
            ps = con.prepareStatement(query);
            ps.setDouble(1, plan.getTotalCal());
            ps.setInt(2, plan.getUserID());
            ps.setString(3, plan.getDate());
            st = ps.executeUpdate();
        } catch (Exception e) {
            st = -2;
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                deletePlanProds(plan.getPlanID());
                insertPlanProds(plan);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return st;
    }

    public int deletePlanProds(int id) {
        try {
            String query = "delete from PlanProducts where plan_id=?";
            ps = con.prepareStatement(query);
            ps.setInt(1, id);
            st = ps.executeUpdate();
        } catch (Exception e) {
            st = -2;
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return st;
    }

    public int insertPlanProds(FoodPlan plan) {
        try {
            for (int i = 0; i < plan.getWeights().size(); i++) {
                String query = "insert into PlanProducts(product_id, plan_id , weight) values(?,?,?)";
                ps = con.prepareStatement(query);
                ps.setInt(1, plan.getProducts().get(i).getProductID());
                ps.setInt(2, plan.getPlanID());
                ps.setDouble(3, plan.getWeights().get(i));
                st = ps.executeUpdate();
            }
        } catch (Exception e) {
            st = -2;
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return st;
    }
}
