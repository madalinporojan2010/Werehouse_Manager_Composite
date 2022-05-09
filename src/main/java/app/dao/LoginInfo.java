package app.dao;

import app.bll.model.User;

public class LoginInfo {
    private static String rights = "client";
    private LoginSerializator loginSerializator;

    public LoginInfo(LoginSerializator loginSerializator) {
        this.loginSerializator = loginSerializator;
    }

    public int getNextID() {
        return loginSerializator.getUsers().get(loginSerializator.getUsers().size() - 1).getId() + 1;
    }

    public boolean isAlreadyRegistered(String username) {
        if (loginSerializator.getUsers().stream().anyMatch(user -> user.getUsername().equals(username))){
            rights = loginSerializator.getUsers().stream().filter(user -> user.getUsername().equals(username)).findFirst().get().getRights();
            return true;
        }
        return false;
    }

    public boolean isPasswordGood(String username, String password) {
        return loginSerializator.getUsers().stream().anyMatch(user -> user.getUsername().equals(username) && user.getPassword().equals(password));
    }

    public void registerUser(User user) {
        loginSerializator.getUsers().add(user);
    }

    public String getRights() {
        return rights;
    }
}
