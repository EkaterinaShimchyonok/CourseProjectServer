package org.example.dao;

import org.example.DatabaseManager;
import org.example.POJO.Vitamins;

import java.sql.*;

public class VitaminsDao {
    Connection con = DatabaseManager.getInstance();
    PreparedStatement ps = null;
    ResultSet rs = null;
    int st; // статус

    public int insert(Vitamins vitamins) {
        Statement stmt = null; // добавляем Statement для выполнения SELECT last_insert_rowid()
        int newId = -1; // Инициализация переменной для возвращаемого ID
        try {
            String query = "INSERT INTO Vitamins(a, d, e, k, c, b12) VALUES(?,?,?,?,?,?)";
            ps = con.prepareStatement(query);
            ps.setDouble(1, vitamins.getA());
            ps.setDouble(2, vitamins.getD());
            ps.setDouble(3, vitamins.getE());
            ps.setDouble(4, vitamins.getK());
            ps.setDouble(5, vitamins.getC());
            ps.setDouble(6, vitamins.getB12());
            ps.executeUpdate();

            // Используем отдельный Statement для получения последнего вставленного идентификатора
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT last_insert_rowid() AS id");
            if (rs.next()) {
                newId = rs.getInt("id");
                vitamins.setVitaminsID(newId); // Устанавливаем сгенерированный идентификатор в поле vitamins
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



    public int update(Vitamins vitamins) {
        try {
            String query = "UPDATE Vitamins SET a=?, d=?, e=?, k=?, c=?, b12=? WHERE vitamins_id=?";
            ps = con.prepareStatement(query);
            ps.setDouble(1, vitamins.getA());
            ps.setDouble(2, vitamins.getD());
            ps.setDouble(3, vitamins.getE());
            ps.setDouble(4, vitamins.getK());
            ps.setDouble(5, vitamins.getC());
            ps.setDouble(6, vitamins.getB12());
            ps.setInt(7, vitamins.getVitaminsID());
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

    public int delete(Vitamins vitamins) {
        try {
            String query = "DELETE FROM Vitamins WHERE vitamins_id=?";
            ps = con.prepareStatement(query);
            ps.setInt(1, vitamins.getVitaminsID());
            st = ps.executeUpdate();
            System.out.println("Deleted vitamins info: " + st);
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

    public Vitamins fetchByIdWithConnection(int id, Connection con) {
        Vitamins vitamins = new Vitamins();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String query = "SELECT * FROM Vitamins WHERE vitamins_id=?";
            ps = con.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                vitamins.setVitaminsID(rs.getInt("vitamins_id"));
                vitamins.setA(rs.getDouble("a"));
                vitamins.setD(rs.getDouble("d"));
                vitamins.setE(rs.getDouble("e"));
                vitamins.setK(rs.getDouble("k"));
                vitamins.setC(rs.getDouble("c"));
                vitamins.setB12(rs.getDouble("b12"));
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
        return vitamins;
    }

}
