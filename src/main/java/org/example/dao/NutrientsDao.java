package org.example.dao;

import org.example.DatabaseManager;
import org.example.POJO.Nutrients;

import java.sql.*;

public class NutrientsDao {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    int st; // статус

    private final MacroNutrientsDao macroNutrientsDao = new MacroNutrientsDao();
    private final VitaminsDao vitaminsDao = new VitaminsDao();
    private final MineralsDao mineralsDao = new MineralsDao();

    public int insertNorm(Nutrients nutrients) {
        con = DatabaseManager.getInstance();
        int id=0; // Инициализация переменной для возвращаемого ID
        Statement stmt = null;
        try {
            int nutrientsID = this.insert(nutrients);
            String query = "insert into Norm(nutrients_id) values(?)";
            ps = con.prepareStatement(query);
            ps.setInt(1, nutrientsID);
            ps.executeUpdate();

            // Используем отдельный Statement для получения последнего вставленного идентификатора
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT last_insert_rowid() AS norm_id");
            if (rs.next()) {
                id = rs.getInt("norm_id");
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
        return id;
    }

    public int insert(Nutrients nutrients) {
        con = DatabaseManager.getInstance();
        int newId = -1; // Инициализация переменной для возвращаемого ID
        Statement stmt = null;
        try {
            int macronID = macroNutrientsDao.insert(nutrients.getMacroNutrients());
            int vitaminsID = vitaminsDao.insert(nutrients.getVitamins());
            int mineralsID = mineralsDao.insert(nutrients.getMinerals());
            String query = "insert into Nutrients(macron_id, vitamins_id, minerals_id) values(?,?,?)";
            ps = con.prepareStatement(query);
            ps.setInt(1, macronID);
            ps.setInt(2, vitaminsID);
            ps.setInt(3, mineralsID);
            ps.executeUpdate();

            // Используем отдельный Statement для получения последнего вставленного идентификатора
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT last_insert_rowid() AS nutrients_id");
            if (rs.next()) {
                newId = rs.getInt("nutrients_id");
                nutrients.setNutrientsID(newId); // Устанавливаем сгенерированный идентификатор в поле nutrients
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



    public int update(Nutrients nutrients) {
        con = DatabaseManager.getInstance();
        try {
            macroNutrientsDao.update(nutrients.getMacroNutrients());
            vitaminsDao.update(nutrients.getVitamins());
            mineralsDao.update(nutrients.getMinerals());
            st = 1;
            System.out.println("updated nutrients info " + st);
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

    public int delete(Nutrients nutrients) {
        con = DatabaseManager.getInstance();
        try {
            macroNutrientsDao.delete(nutrients.getMacroNutrients());
            vitaminsDao.delete(nutrients.getVitamins());
            mineralsDao.delete(nutrients.getMinerals());

            String query = "delete from Nutrients where nutrients_id=?";
            ps = con.prepareStatement(query);
            ps.setInt(1, nutrients.getNutrientsID());
            st = ps.executeUpdate();
            System.out.println("deleted nutrients info " + st);
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

    public Nutrients fetchById(int id) {
        Nutrients nutrients = new Nutrients();
        con = DatabaseManager.getInstance();
        try {
            String query = "select * from Nutrients where nutrients_id=?";
            ps = con.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                nutrients.setNutrientsID(rs.getInt("nutrients_id"));
                nutrients.setMacroNutrients(macroNutrientsDao.fetchByIdWithConnection(rs.getInt("macron_id"), con));
                nutrients.setVitamins(vitaminsDao.fetchByIdWithConnection(rs.getInt("vitamins_id"), con));
                nutrients.setMinerals(mineralsDao.fetchByIdWithConnection(rs.getInt("minerals_id"), con));
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
        return nutrients;
    }

}
