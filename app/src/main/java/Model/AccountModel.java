package Model;

import android.accounts.Account;

public class AccountModel {
    private int id;
    private int user;
    private String password;
    private int role;

    public AccountModel(){

    }

    public AccountModel(int id, int user, String password, int role) {
        this.id = id;
        this.user = user;
        this.password = password;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String id) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
