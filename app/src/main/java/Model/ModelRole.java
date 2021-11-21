package Model;

import java.io.Serializable;

public class ModelRole implements Serializable {
    private int user_id;
    private int role;

    public ModelRole(int user_id, int role) {
        this.user_id = user_id;
        this.role = role;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int cart_id) {
        this.role = role;
    }
}