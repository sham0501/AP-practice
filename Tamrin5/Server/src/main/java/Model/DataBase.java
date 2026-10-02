package model;

import java.util.ArrayList;

public class DataBase {
    private final ArrayList<User> users;

    public DataBase() {
        users = new ArrayList<>();
    }

    public void addUser(User user) {
        users.add(user);
    }

    public ArrayList<User> getUsers() {
        return users;
    }
}