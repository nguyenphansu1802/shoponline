package Model;

import java.io.Serializable;

public class ModelCart implements Serializable {
    private int cartId;
    private  int userid;
    private int itemId;
    private String nameItem;
    private double price;
    private int amount;
    private int condition;

    public ModelCart(){

    }

    public ModelCart(int cartId, int userid, int itemId, String nameItem, double price, int amount, int condition) {
        this.cartId = cartId;
        this.userid = userid;
        this.itemId = itemId;
        this.nameItem = nameItem;
        this.price = price;
        this.amount = amount;
        this.condition = condition;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }


    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getNameItem() {
        return nameItem;
    }

    public void setNameItem(String nameItem) {
        this.nameItem = nameItem;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price =price ;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getCondition() {
        return condition;
    }

    public void setCondition(int condition) {
        this.condition = condition ;
    }

}
