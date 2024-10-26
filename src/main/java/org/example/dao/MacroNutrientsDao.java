package org.example.dao;

import org.example.DatabaseManager;
import org.example.POJO.MacroNutrients;

import java.sql.*;

public class MacroNutrientsDao {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    int st; // статус

    public int insert(MacroNutrients macroNutrients) {
        con = DatabaseManager.getInstance();
        Statement stmt = null; // добавляем Statement для выполнения SELECT last_insert_rowid()
        int newId = -1; // Инициализация переменной для возвращаемого ID
        try {
            String query = "insert into MacroNutrients(calories, proteins, fats, carbs) values(?,?,?,?)";
            ps = con.prepareStatement(query);
            ps.setDouble(1, macroNutrients.getCalories());
            ps.setDouble(2, macroNutrients.getProteins());
            ps.setDouble(3, macroNutrients.getFats());
            ps.setDouble(4, macroNutrients.getCarbs());
            ps.executeUpdate();

            // Используем отдельный Statement для получения последнего вставленного идентификатора
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT last_insert_rowid() AS id");
            if (rs.next()) {
                newId = rs.getInt("id");
                macroNutrients.setMacronID(newId); // Устанавливаем сгенерированный идентификатор в поле macroNutrients
            }
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
        return newId;
    }


    public int update(MacroNutrients macroNutrients) {
        con = DatabaseManager.getInstance();
        try {
            String query = "update MacroNutrients set calories=?, proteins=?, fats=?, carbs=? where macron_id=?";
            ps = con.prepareStatement(query);
            ps.setDouble(1, macroNutrients.getCalories());
            ps.setDouble(2, macroNutrients.getProteins());
            ps.setDouble(3, macroNutrients.getFats());
            ps.setDouble(4, macroNutrients.getCarbs());
            ps.setInt(5, macroNutrients.getMacronID());
            st = ps.executeUpdate();
            System.out.println("updated macronutrients info " + st);
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

    public int delete(MacroNutrients macroNutrients) {
        con = DatabaseManager.getInstance();
        try {
            String query = "delete from MacroNutrients where macron_id=?";
            ps = con.prepareStatement(query);
            ps.setInt(1, macroNutrients.getMacronID());
            st = ps.executeUpdate();
            System.out.println("deleted macronutrients info " + st);
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

    public MacroNutrients fetchByIdWithConnection(int id, Connection con) {
        MacroNutrients macroNutrients = new MacroNutrients();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String query = "select * from MacroNutrients where macron_id=?";
            ps = con.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                macroNutrients.setMacronID(rs.getInt("macron_id"));
                macroNutrients.setCalories(rs.getDouble("calories"));
                macroNutrients.setProteins(rs.getDouble("proteins"));
                macroNutrients.setFats(rs.getDouble("fats"));
                macroNutrients.setCarbs(rs.getDouble("carbs"));
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
        return macroNutrients;
    }

}
