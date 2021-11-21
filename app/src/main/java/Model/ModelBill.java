package Model;

import java.io.Serializable;

public class ModelBill implements Serializable {
    private int billId;
    private int userId;
    private double totalPrice;

    public ModelBill(){

    }
    public ModelBill(int billId, int userId, double totalPrice){
        this.billId = billId;
        this.userId = userId;
        this.totalPrice = totalPrice;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public int getUserId(){return userId;}

    public  void setUserId(int userId){this.userId = userId;}

    public double getTotalPrice(){return totalPrice;}

    public void setTotalPrice(double totalPrice){this.totalPrice = totalPrice;}
}
