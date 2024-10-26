package org.example.dao;

import org.example.DatabaseManager;
import org.example.POJO.Nutrients;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NutrientsDao {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    int st; // статус

    private final MacroNutrientsDao macroNutrientsDao = new MacroNutrientsDao();
    private final VitaminsDao vitaminsDao = new VitaminsDao();
    private final MineralsDao mineralsDao = new MineralsDao();

    public int insert(Nutrients nutrients) {
        con = DatabaseManager.getInstance();
        try {
            int macronID = macroNutrientsDao.insert(nutrients.getMacroNutrients());
            int vitaminsID = vitaminsDao.insert(nutrients.getVitamins());
            int mineralsID = mineralsDao.insert(nutrients.getMinerals());

            String query = "insert into Nutrients(macron_id, vitamins_id, minerals_id) values(?,?,?)";
            ps = con.prepareStatement(query);
            ps.setInt(1, macronID);
            ps.setInt(2, vitaminsID);
            ps.setInt(3, mineralsID);
            st = ps.executeUpdate();
            System.out.println("inserted nutrients info " + st);
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
