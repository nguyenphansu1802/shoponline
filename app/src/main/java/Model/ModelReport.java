package Model;

import java.io.Serializable;

public class ModelReport implements Serializable {
    private int report_id;
    private int user_id;
    private String username;
    private int item_id;
    private String itemname;
    private String contents;

    public ModelReport(int report_id, int user_id, int item_id, String contents) {
        this.report_id = report_id;
        this.user_id = user_id;
        this.item_id = item_id;
        this.contents = contents;
    }
    public ModelReport(int report_id, int user_id, String username, int item_id, String itemname, String contents) {
        this.report_id = report_id;
        this.user_id = user_id;
        this.username = username;
        this.item_id = item_id;
        this.itemname = itemname;
        this.contents = contents;
    }
    public  int getReport_id(){return report_id;}

    public void setReport_id(int report_id){this.report_id = report_id;}

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername(){return username;}

    public void setUsername(String username){this.username = username;}

    public String getItemname(){return itemname;}

    public void setItemname(String itemname){this.itemname = itemname;}

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public String getContents(){return contents;}

    public void setContents(String contents){this.contents = contents;}
}
