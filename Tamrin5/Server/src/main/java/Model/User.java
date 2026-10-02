package model;

import java.util.ArrayList;

public class User {
    private final String token;
    private final String username;
    private final ArrayList<MyFile> files;

    public User(String username, String token) {
        this.token = token;
        this.username = username;
        files = new ArrayList<>();
    }

    public String getToken() {
        return token;
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