package app.bll.model;

import app.dao.Serializator;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String username;
    private String password;
    private String rights;

    public User(int id, String username, String password, String rights) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.rights = rights;
    }

    public User() {
        this(-1, "", "", "client");
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRights() {
        return rights;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRights(String rights) {
        this.rights = rights;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", rights='" + rights + '\'' +
                '}';
    }
}
