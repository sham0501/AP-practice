package model;

import java.util.ArrayList;

public class User {
    private final String username;
    private final String password;
    private final ArrayList<MyFile> files;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.files = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public ArrayList<MyFile> getFiles() {
        return files;
    }

    public void addFile(MyFile file) {
        files.add(file);
    }
}