package org.example.dao;

import org.example.DatabaseManager;
import org.example.POJO.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    int st; // статус

    private final CategoryDao categoryDao = new CategoryDao();
    private final NutrientsDao nutrientsDao = new NutrientsDao();

    public int insert(Product product) {
        con = DatabaseManager.getInstance();
        try {
            int categoryID = categoryDao.insert(product.getCategory());
            int nutrientsID = nutrientsDao.insert(product.getNutrients());

            String query = "insert into Product(category_id, name, is_coocked, category_name, nutrients_id) values(?,?,?,?,?)";
            ps = con.prepareStatement(query);
            ps.setInt(1, categoryID);
            ps.setString(2, product.getName());
            ps.setBoolean(3, product.isCoocked());
            ps.setString(4, product.getCategoryName());
            ps.setInt(5, nutrientsID);
            st = ps.executeUpdate();
            System.out.println("inserted product info " + st);
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

    public int update(Product product) {
        con = DatabaseManager.getInstance();
        try {
            categoryDao.update(product.getCategory());
            nutrientsDao.update(product.getNutrients());

            String query = "update Product set category_id=?, name=?, is_coocked=?, category_name=?, nutrients_id=? where product_id=?";
            ps = con.prepareStatement(query);
            ps.setInt(1, product.getCategory().getCategoryID());
            ps.setString(2, product.getName());
            ps.setBoolean(3, product.isCoocked());
            ps.setString(4, product.getCategoryName());
            ps.setInt(5, product.getNutrients().getNutrientsID());
            ps.setInt(6, product.getProductID());
            st = ps.executeUpdate();
            System.out.println("updated product info " + st);
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
            categoryDao.delete(product.getCategory());
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
            while (rs.next()) {
                product.setProductID(rs.getInt("product_id"));
                product.setCategory(categoryDao.fetchById(rs.getInt("category_id")));
                product.setName(rs.getString("name"));
                product.setCoocked(rs.getBoolean("is_coocked"));
                product.setCategoryName(rs.getString("category_name"));
                product.setNutrients(nutrientsDao.fetchById(rs.getInt("nutrients_id")));
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
                product.setCategory(categoryDao.fetchById(rs.getInt("category_id")));
                product.setName(rs.getString("name"));
                product.setCoocked(rs.getBoolean("is_coocked"));
                product.setCategoryName(rs.getString("category_name"));
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
                product.setCoocked(rs.getBoolean("is_coocked"));
                product.setCategoryName(rs.getString("category_name"));
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

    public List<Product> fetchByCategory(int categoryId) {
        List<Product> products = new ArrayList<>();
        con = DatabaseManager.getInstance();
        try {
            String query = "select * from Product where category_id = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, categoryId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setProductID(rs.getInt("product_id"));
                product.setCategory(categoryDao.fetchById(rs.getInt("category_id")));
                product.setName(rs.getString("name"));
                product.setCoocked(rs.getBoolean("is_coocked"));
                product.setCategoryName(rs.getString("category_name"));
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

}
