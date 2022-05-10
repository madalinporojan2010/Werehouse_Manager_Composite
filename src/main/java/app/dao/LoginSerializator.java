package app.dao;

import app.bll.model.User;

import java.util.ArrayList;
import java.util.List;

public class LoginSerializator extends Serializator {
    private List<User> users;

    public LoginSerializator() {
        users = new ArrayList<>();
        User admin = new User(0, "admin", "123", "administrator");
        User employee1 = new User(1, "radu", "qwerty", "employee");
        User employee2 = new User(2, "ionut", "1", "employee");
        User employee3 = new User(3, "mircea", "321", "employee");

        users.add(admin);
        users.add(employee1);
        users.add(employee2);
        users.add(employee3);
    }

    public void serialize() {
        super.serialize(users, "users.ser");
    }

    public List<User> deserialize() {
        if (super.deserialize("users.ser") != null) {
            users = (List<User>) super.deserialize("users.ser");
            return (List<User>) super.deserialize("users.ser");
        }
        return null;
    }

    public List<User> getUsers() {
        return users;
    }
}
