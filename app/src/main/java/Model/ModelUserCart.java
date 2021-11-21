package Model;

import java.io.Serializable;

public class ModelUserCart implements Serializable {
    private int user_id;
    private int cart_id;

    public ModelUserCart(int user_id, int cart_id) {
        this.user_id = user_id;
        this.cart_id = cart_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getCart_id() {
        return cart_id;
    }

    public void setCart_id(int cart_id) {
        this.cart_id = cart_id;
    }
}