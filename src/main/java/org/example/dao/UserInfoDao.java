package org.example.dao;

import org.example.DatabaseManager;
import org.example.POJO.UserInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserInfoDao {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    int st; // статус
    NutrientsDao nuDao = new NutrientsDao();

    public int insert(UserInfo user) {
        con = DatabaseManager.getInstance();
        try {
            String query = "insert into UserInfo(name, age, is_male, height, weight, activity_level, goal) " +
                    "values(?,?,?,?,?,?,?)";
            ps = con.prepareStatement(query);
            ps.setString(1, user.getName());
            ps.setInt(2, user.getAge());
            ps.setBoolean(3, user.isMale());
            ps.setDouble(4, user.getHeight());
            ps.setDouble(5, user.getWeight());
            ps.setInt(6, user.getActivityLevel());
            ps.setDouble(7, user.getGoal());
            st = ps.executeUpdate();
            System.out.println("inserted user info " + st);
        } catch (NoClassDefFoundError e) {
            st = -1;
            e.printStackTrace();
        } catch (Exception e) {
            st = -2;
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return st;
    }

    public int update(UserInfo user) {
        con = DatabaseManager.getInstance();
        try {
            String query = "update UserInfo set name=?, age=?, is_male=?, height=?, weight=?, activity_level=?, goal=? " +
                    "where uinfo_id=?";
            ps = con.prepareStatement(query);
            ps.setString(1, user.getName());
            ps.setInt(2, user.getAge());
            ps.setBoolean(3, user.isMale());
            ps.setDouble(4, user.getHeight());
            ps.setDouble(5, user.getWeight());
            ps.setInt(6, user.getActivityLevel());
            ps.setDouble(7, user.getGoal());
            ps.setInt(8, user.getUinfoID());
            System.out.println("updated user " + st);
        } catch (Exception e) {
            st = -2;
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return st;
    }

    public int delete(UserInfo user) {
        con = DatabaseManager.getInstance();
        try {
            String query = "delete from UserInfo where uinfo_id=? ";
            ps = con.prepareStatement(query);
            ps.setInt(1, user.getUinfoID());
            st = ps.executeUpdate();
            System.out.println("deleted user info " + st);
        } catch (Exception e) {
            st = -2;
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return st;
    }

    public UserInfo fetchById(int id) {
        UserInfo user = new UserInfo();
        con = DatabaseManager.getInstance();
        try {
            String query = "select * from UserInfo where uinfo_id=?";
            ps = con.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                user.setUinfoID(rs.getInt("uinfo_id"));
                user.setName(rs.getString("name"));
                user.setAge(rs.getInt("age"));
                user.setMale(rs.getBoolean("is_male"));
                user.setHeight(rs.getDouble("height"));
                user.setWeight(rs.getDouble("weight"));
                user.setActivityLevel(rs.getInt("activity_level"));
                user.setGoal(rs.getDouble("goal"));
                user.setNorm(nuDao.fetchById(rs.getInt("norm_id")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return user;
    }
}
