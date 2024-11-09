package org.example.dao;

import org.example.DatabaseManager;
import org.example.POJO.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    int st; // статус

    private final UserInfoDao userInfoDao = new UserInfoDao();

    public int insert(User user) {
        con = DatabaseManager.getInstance();
        Statement stmt = null; // добавляем Statement для выполнения SELECT last_insert_rowid()
        try {
            int userInfoID = userInfoDao.insert(user.getInfo());
            String query = "insert into User(email, password, is_admin, uinfo_id) values(?,?,?,?)";
            ps = con.prepareStatement(query);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setBoolean(3, user.isAdmin());
            ps.setInt(4, userInfoID);
            st = ps.executeUpdate();

            // Используем отдельный Statement для получения последнего вставленного идентификатора
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT last_insert_rowid() AS id");
            if (rs.next()) {
                user.setUserID(rs.getInt("id"));
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


    public int updateAdmin(User user) {
        con = DatabaseManager.getInstance();
        try {
            String query = "update User set is_admin=? where user_id=?";
            ps = con.prepareStatement(query);
            ps.setBoolean(1, user.isAdmin());
            ps.setInt(2, user.getUserID());
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

    public int update(User user) {
        userInfoDao.update(user.getInfo());
        con = DatabaseManager.getInstance();
        try {
            String query = "update User set is_admin=?, uinfo_id=? where user_id=?";
            ps = con.prepareStatement(query);
            ps.setBoolean(1, user.isAdmin());
            ps.setInt(2, user.getInfo().getUinfoID());
            ps.setInt(3, user.getUserID());
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

    public int delete(User user) {
        con = DatabaseManager.getInstance();
        try {
            userInfoDao.delete(user.getInfo());

            String query = "delete from User where user_id=?";
            ps = con.prepareStatement(query);
            ps.setInt(1, user.getUserID());
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

    public User fetchById(int id) {
        User user = new User();
        con = DatabaseManager.getInstance();
        try {
            String query = "select * from User where user_id=?";
            ps = con.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                user.setUserID(rs.getInt("user_id"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setAdmin(rs.getBoolean("is_admin"));
                user.setInfo(userInfoDao.fetchById(rs.getInt("uinfo_id")));
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

    public List<User> fetchByName(String name) {
        List<User> users = new ArrayList<>();
        con = DatabaseManager.getInstance();
        try {
            String query = "select * from User u join UserInfo ui on u.uinfo_id = ui.uinfo_id where ui.name = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, name);
            rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUserID(rs.getInt("user_id"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setAdmin(rs.getBoolean("is_admin"));
                user.setInfo(userInfoDao.fetchById(rs.getInt("uinfo_id")));
                users.add(user);
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
        return users;
    }

    public List<User> fetchByAdmin(boolean isAdmin) {
        List<User> users = new ArrayList<>();
        con = DatabaseManager.getInstance();
        try {
            String query = "select * from User where is_admin=?";
            ps = con.prepareStatement(query);
            ps.setBoolean(1, isAdmin);
            rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUserID(rs.getInt("user_id"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setAdmin(rs.getBoolean("is_admin"));
                user.setInfo(userInfoDao.fetchById(rs.getInt("uinfo_id")));
                users.add(user);
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
        return users;
    }

    public User fetchByEmailPassw(String email, String password) {
        User user = new User();
        con = DatabaseManager.getInstance();
        try {
            String query = "select * from User where email=? and password=?";
            ps = con.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs.next()) {
                user.setUserID(rs.getInt("user_id"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setAdmin(rs.getBoolean("is_admin"));
                user.setInfo(userInfoDao.fetchById(rs.getInt("uinfo_id")));
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

    public List<User> fetchAll() {
        List<User> users = new ArrayList<>();
        con = DatabaseManager.getInstance();
        try {
            String query = "select * from User";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUserID(rs.getInt("user_id"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setAdmin(rs.getBoolean("is_admin"));
                user.setInfo(userInfoDao.fetchById(rs.getInt("uinfo_id")));
                users.add(user);
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
        return users;
    }
}
