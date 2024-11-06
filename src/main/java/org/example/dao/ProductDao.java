package org.example.dao;

import org.example.DatabaseManager;
import org.example.POJO.Product;
import org.example.POJO.Nutrients;
import org.example.POJO.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    int st; // статус
    private final NutrientsDao nutrientsDao = new NutrientsDao();
    private final CategoryDao categoryDao = new CategoryDao();

    public int insert(Product product) {
        con = DatabaseManager.getInstance();
        Statement stmt = null; // добавляем Statement для выполнения SELECT last_insert_rowid()
        try {
            int nutrientsID = nutrientsDao.insert(product.getNutrients());
            product.setCategory(categoryDao.fetchByName(product.getCategory().getName()));
            String query = "insert into Product(name, is_cooked, nutrients_id, category_id) values(?,?,?,?)";
            ps = con.prepareStatement(query);
            ps.setString(1, product.getName());
            ps.setBoolean(2, product.getIsCooked());
            ps.setInt(3, nutrientsID);
            ps.setInt(4, product.getCategory().getCategoryID());
            st = ps.executeUpdate();

            // Используем отдельный Statement для получения последнего вставленного идентификатора
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT last_insert_rowid() AS id");
            if (rs.next()) {
                product.setProductID(rs.getInt("id"));
            }
        } catch (Exception e) {
            st = -2;
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (stmt != null) stmt.close(); // Закрываем Statement
                if (con != null) con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return st;
    }

    public int update(Product product) {
        con = DatabaseManager.getInstance();
        try {
            nutrientsDao.update(product.getNutrients());
            String query = "update Product set name=?, is_cooked=?, nutrients_id=?, category_id=? where product_id=?";
            ps = con.prepareStatement(query);
            ps.setString(1, product.getName());
            ps.setBoolean(2, product.getIsCooked());
            ps.setInt(3, product.getNutrients().getNutrientsID());
            ps.setInt(4, product.getCategory().getCategoryID());
            ps.setInt(5, product.getProductID());
            st = ps.executeUpdate();
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

    public int delete(Product product) {
        con = DatabaseManager.getInstance();
        try {
            nutrientsDao.delete(product.getNutrients());
            String query = "delete from Product where product_id=?";
            ps = con.prepareStatement(query);
            ps.setInt(1, product.getProductID());
            st = ps.executeUpdate();
            System.out.println("deleted product info " + st);
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

    public Product fetchById(int id) {
        Product product = new Product();
        con = DatabaseManager.getInstance();
        try {
            String query = "select * from Product where product_id=?";
            ps = con.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                product.setProductID(rs.getInt("product_id"));
                product.setName(rs.getString("name"));
                product.setIsCooked(rs.getBoolean("is_cooked"));
                product.setNutrients(nutrientsDao.fetchById(rs.getInt("nutrients_id")));
                product.setCategory(categoryDao.fetchById(rs.getInt("category_id")));
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
        return product;
    }

    public List<Product> fetchAll() {
        List<Product> products = new ArrayList<>();
        con = DatabaseManager.getInstance();
        try {
            String query = "select * from Product";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setProductID(rs.getInt("product_id"));
                product.setName(rs.getString("name"));
                product.setIsCooked(rs.getBoolean("is_cooked"));
                product.setNutrients(nutrientsDao.fetchById(rs.getInt("nutrients_id")));
                product.setCategory(categoryDao.fetchById(rs.getInt("category_id")));
                products.add(product);
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
        return products;
    }

    public List<Product> fetchByName(String name) {
        List<Product> products = new ArrayList<>();
        con = DatabaseManager.getInstance();
        try {
            String query = "select * from Product where name = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, name);
            rs = ps.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setProductID(rs.getInt("product_id"));
                product.setCategory(categoryDao.fetchById(rs.getInt("category_id")));
                product.setName(rs.getString("name"));
                product.setIsCooked(rs.getBoolean("is_cooked"));
                product.setNutrients(nutrientsDao.fetchById(rs.getInt("nutrients_id")));
                products.add(product);
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
        return products;
    }

    public List<Product> fetchByCategory(String categoryName) {
        List<Product> products = new ArrayList<>();
        con = DatabaseManager.getInstance();
        try {
            String query = "select * from Product p join Category c on p.category_id = c.category_id where c.name=?";
            ps = con.prepareStatement(query);
            ps.setString(1, categoryName);
            rs = ps.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setProductID(rs.getInt("product_id"));
                product.setName(rs.getString("name"));
                product.setIsCooked(rs.getBoolean("is_cooked"));
                product.setNutrients(nutrientsDao.fetchById(rs.getInt("nutrients_id")));
                product.setCategory(categoryDao.fetchById(rs.getInt("category_id")));
                products.add(product);
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
        return products;
    }

}
