package org.example;

import org.example.POJO.*;

import java.sql.*;

public class DatabaseManager {
    public static Connection connection = null;

    public void connect() throws SQLException {

        connection = DriverManager
                .getConnection("jdbc:sqlite:E:\\BSUIR\\0 КУРСОВЫЕ\\КР5 Java\\Products.db");
        if (connection == null){
            throw new SQLException();
        }
        else
            System.out.println("Драйвер jdbc-sqlite определён.");
    }


    public static void close() {
        try {
            if(connection != null) {
                connection.close();
                System.out.println("Разорвано соединение с базой данных");
            }
        } catch (SQLException e) {
            System.out.println("Не получилось разорвать соединение с базой данных");
        }
    }

    public Connection getConnection() {
        return connection;
    }

    // Дополнительные методы для выполнения SQL-запросов
    public String addUser(String email, String password) {
        String sql = "INSERT INTO User(email, password, admin) VALUES(?, ?, 0)";
        try {
            connection.setAutoCommit(false); // Включение транзакции
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, email);
                pstmt.setString(2, password);
                pstmt.executeUpdate();
                connection.commit(); //Коммит транзакции
                connection.setAutoCommit(true);
                return "Регистрация прошла успешно. Войдите в аккаунт " + email;
            } catch (SQLException e) {
                connection.rollback(); // Откат транзакции при ошибке
                connection.commit();
                connection.setAutoCommit(true);
                return "Вы уже зарегистрированы в системе. Войдите в аккаунт " + email;
            }
        } catch (SQLException e) {
            return "ошибка Не удалось зарегистрироваться.";
        }
    }
    public User findUser(String email, String password) {
        String sql = "SELECT * FROM User WHERE email = ? AND password = ?";
        try {
            connection.setAutoCommit(false); // Включение транзакции
            Savepoint point1 = connection.setSavepoint("Savepoint1");
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, email);
                pstmt.setString(2, password);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    int userID = rs.getInt("user_id");
                    boolean admin = rs.getBoolean("admin");
                    int uinfoID = rs.getInt("uinfo_id");
                    UserInfo info = findUserInfo(uinfoID); // Метод для получения UserInfo
                    User user = new User(userID, email, password, admin, info);

                    connection.commit(); // Коммит транзакции
                    connection.setAutoCommit(true);
                    return user;
                } else {
                    connection.commit(); // Коммит транзакции
                    connection.setAutoCommit(true);
                    return null;
                }
            } catch (SQLException e) {
                connection.rollback(point1); // Откат транзакции при ошибке
                connection.commit();
                connection.setAutoCommit(true);
                throw new RuntimeException("Ошибка при поиске пользователя", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка: Не удалось выполнить поиск", e);
        }
    }

    public UserInfo findUserInfo(int uinfoID) {
        String sql = "SELECT * FROM UserInfo WHERE uinfo_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, uinfoID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("uinfo_id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                boolean male = rs.getBoolean("male");
                double height = rs.getDouble("height");
                double weight = rs.getDouble("weight");
                int activity_level = rs.getInt("activity_level");
                double goal = rs.getDouble("goal");
                int normID = rs.getInt("norm_id");
                Nutrients norm = findNorm(normID); // Метод для получения Nutrients

                return new UserInfo(id, name, age, male, height, weight, activity_level, goal, norm);
            } else
                return new UserInfo(); // Возвращаем null, если запись не найдена
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске информации о пользователе", e);
        }
    }

    public Nutrients findNorm(int normID) {
        String sql = "SELECT nutrients_id FROM Norm WHERE norm_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, normID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int nutrientsID = rs.getInt("nutrients_id");
                return findNutrients(nutrientsID); // Метод для получения Nutrients
            } else
                return new Nutrients(); // Возвращаем null, если запись не найдена
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске информации о норме нутриентов пользователя", e);
        }
    }

    public Nutrients findNutrients(int nutrientsID) {
        String sql = "SELECT * FROM Nutrients WHERE nutrients_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, nutrientsID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int vitaminsID = rs.getInt("vitamins_id");
                int mineralsID = rs.getInt("minerals_id");
                int macronID = rs.getInt("macron_id");
                Vitamins v = findVitamins(vitaminsID);
                Minerals mins = findMinerals(mineralsID);
                MacroNutrients macrs = findMacroNutrients(macronID);
                return new Nutrients(nutrientsID, macrs, v, mins);
            } else
                return new Nutrients(); // Возвращаем null, если запись не найдена
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске информации о Nutrients", e);
        }
    }

    public Vitamins findVitamins(int vitaminsID) {
        String sql = "SELECT * FROM Vitamins WHERE vitamins_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, vitaminsID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                double a = rs.getInt("a");
                double d = rs.getInt("d");
                double e = rs.getInt("e");
                double k = rs.getInt("k");
                double c = rs.getInt("c");
                double b12 = rs.getInt("b12");
                return new Vitamins(vitaminsID, a, d, e, k, c, b12);
            } else
                return new Vitamins(); // Возвращаем null, если запись не найдена
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске информации о Vitamins", e);
        }
    }

    public Minerals findMinerals(int mineralsID) {
        String sql = "SELECT * FROM Minerals WHERE minerals_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, mineralsID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                double ca = rs.getInt("ca");
                double fe = rs.getInt("fe");
                double mg = rs.getInt("mg");
                double zn = rs.getInt("zn");
                double cu = rs.getInt("cu");
                double se = rs.getInt("se");
                return new Minerals(mineralsID, ca, fe, mg, zn, cu, se);
            } else
                return new Minerals(); // Возвращаем null, если запись не найдена
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске информации о Minerals", e);
        }
    }

    public MacroNutrients findMacroNutrients(int macronID) {
        String sql = "SELECT * FROM MacroNutrients WHERE macron_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, macronID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                double cal = rs.getInt("calories");
                double p = rs.getInt("proteins");
                double f = rs.getInt("fats");
                double c = rs.getInt("carbs");
                return new MacroNutrients(macronID, cal, p, f, c);
            } else
                return new MacroNutrients(); // Возвращаем null, если запись не найдена
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске информации MacroNutrients", e);
        }
    }


}
