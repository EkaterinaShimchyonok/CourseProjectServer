package org.example.POJO;

public class User {
    int userID;
    String email;
    String password;
    boolean admin;
    UserInfo info;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public User(){}
    public User(int userID, String email, String password, boolean admin, UserInfo info) {
        this.userID = userID;
        this.email = email;
        this.password = password;
        this.admin = admin;
        this.info = info;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public UserInfo getInfo() {
        return info;
    }

    public void setInfo(UserInfo info) {
        this.info = info;
    }
}
