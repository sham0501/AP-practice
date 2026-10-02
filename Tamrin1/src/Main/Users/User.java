package Main.Users;

import Main.Dars;

import java.util.ArrayList;

public abstract class User {
    protected String username;
    protected long password;
    public ArrayList<Dars> doroosAkhzShode;

    public User(String username, long password) {
        this.username = username;
        this.password = password;
    }

    //Getter
    public String getUsername() {
        return username;
    }

    public long getPassword() {
        return password;
    }
}