package org.example.dao;

import org.example.DatabaseManager;
import org.example.POJO.Minerals;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MineralsDao {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    int st; // статус

    public int insert(Minerals minerals) {
        con = DatabaseManager.getInstance();
        try {
            String query = "insert into Minerals(ca, fe, mg, zn, cu, se) values(?,?,?,?,?,?)";
            ps = con.prepareStatement(query);
            ps.setDouble(1, minerals.getCa());
            ps.setDouble(2, minerals.getFe());
            ps.setDouble(3, minerals.getMg());
            ps.setDouble(4, minerals.getZn());
            ps.setDouble(5, minerals.getCu());
            ps.setDouble(6, minerals.getSe());
            st = ps.executeUpdate();
            System.out.println("inserted minerals info " + st);
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

    public int update(Minerals minerals) {
        con = DatabaseManager.getInstance();
        try {
            String query = "update Minerals set ca=?, fe=?, mg=?, zn=?, cu=?, se=? where minerals_id=?";
            ps = con.prepareStatement(query);
            ps.setDouble(1, minerals.getCa());
            ps.setDouble(2, minerals.getFe());
            ps.setDouble(3, minerals.getMg());
            ps.setDouble(4, minerals.getZn());
            ps.setDouble(5, minerals.getCu());
            ps.setDouble(6, minerals.getSe());
            ps.setInt(7, minerals.getMineralsID());
            st = ps.executeUpdate();
            System.out.println("updated minerals info " + st);
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

    public int delete(Minerals minerals) {
        con = DatabaseManager.getInstance();
        try {
            String query = "delete from Minerals where minerals_id=?";
            ps = con.prepareStatement(query);
            ps.setInt(1, minerals.getMineralsID());
            st = ps.executeUpdate();
            System.out.println("deleted minerals info " + st);
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

    public Minerals fetchByIdWithConnection(int id, Connection con) {
        Minerals minerals = new Minerals();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String query = "SELECT * FROM Minerals WHERE minerals_id=?";
            ps = con.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                minerals.setMineralsID(rs.getInt("minerals_id"));
                minerals.setCa(rs.getDouble("ca"));
                minerals.setFe(rs.getDouble("fe"));
                minerals.setMg(rs.getDouble("mg"));
                minerals.setZn(rs.getDouble("zn"));
                minerals.setCu(rs.getDouble("cu"));
                minerals.setSe(rs.getDouble("se"));
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
        return minerals;
    }

}
