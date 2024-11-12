package org.example.dao;

import org.example.DatabaseManager;
import org.example.POJO.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao {
    Connection con = DatabaseManager.getInstance();
    PreparedStatement ps = null;
    ResultSet rs = null;
    int st; // статус

    public int insert(Category category) {

        try {
            String query = "insert into Category(name, image) values(?,?)";
            ps = con.prepareStatement(query);
            ps.setString(1, category.getName());
            ps.setString(2, category.getImage());
            st = ps.executeUpdate();
            System.out.println("inserted category info " + st);
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

    public int update(Category category) {
        try {
            String query = "update Category set name=?, image=? where category_id=?";
            ps = con.prepareStatement(query);
            ps.setString(1, category.getName());
            ps.setString(2, category.getImage());
            ps.setInt(3, category.getCategoryID());
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

    public int delete(Category category) {
        try {
            String query = "delete from Product where category_id=?";
            ps = con.prepareStatement(query);
            ps.setInt(1, category.getCategoryID());
            st = ps.executeUpdate();
            System.out.println("deleted category products " + st);

            query = "delete from Category where category_id=?";
            ps = con.prepareStatement(query);
            ps.setInt(1, category.getCategoryID());
            st = ps.executeUpdate();
            System.out.println("deleted category info " + st);
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

    public Category fetchById(int id) {
        Category category = new Category();
        try {
            String query = "select * from Category where category_id=?";
            ps = con.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                category.setCategoryID(rs.getInt("category_id"));
                category.setName(rs.getString("name"));
                category.setImage(rs.getString("image"));
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
        return category;
    }

    public List<Category> fetchAll() {
        List<Category> categories = new ArrayList<>();
        try {
            String query = "select * from Category";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Category category = new Category();
                category.setCategoryID(rs.getInt("category_id"));
                category.setName(rs.getString("name"));
                category.setImage(rs.getString("image"));
                categories.add(category);
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
        return categories;
    }
    public List<String> fetchCategoryNames() {
        List<String> categoryNames = new ArrayList<>();
        try {
            String query = "select name from Category";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                categoryNames.add(rs.getString("name"));
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
        return categoryNames;
    }

    public Category fetchByName(String name) {
        Category category = new Category();
        try {
            String query = "select * from Category where name = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, name);
            rs = ps.executeQuery();
            if (rs.next()) {
                category.setCategoryID(rs.getInt("category_id"));
                category.setName(rs.getString("name"));
                category.setImage(rs.getString("image"));
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
        return category;
    }
}
