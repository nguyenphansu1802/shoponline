package Model;

import java.io.Serializable;

public class ModelItem implements Serializable {
    private int id_item;
    private String name_item;
    private String type;
    private double price;
    private int total;
    private int condition;
    private byte[] image;

    public ModelItem (){}
    public ModelItem(int id_item, String name_item, String type, double price, int total){
        this.id_item = id_item;
        this.name_item = name_item;
        this.type = type;
        this.price = price;
        this.total = total;

    }
    public ModelItem(int id_item, String name_item, String type, double price, int total,  byte[] image, int condition){
        this.id_item = id_item;
        this.name_item = name_item;
        this.type = type;
        this.price = price;
        this.total = total;
        this.image = image;
        this.condition = condition;
    }

    public int getId_item() {
        return id_item;
    }

    public void setId_item(int id_item) {
        this.id_item = id_item;
    }

    public String getName_item() {
        return name_item;
    }

    public void setName_item(String name_item) {
        this.name_item = this.name_item;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = this.type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public byte[] getImage(){
        return image;
    }

    public void setImage(byte[] image){
        this.image = image;
    }

    public int getCondition() {
        return condition;
    }

    public void setCondition(int condition) {
        this.condition = condition;
    }
}
