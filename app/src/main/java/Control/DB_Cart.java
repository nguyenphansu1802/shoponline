package Control;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import Model.ModelCart;
import Model.ModelUserCart;

public class DB_Cart extends SQLiteOpenHelper {
    public static  final String Cart_table = "Cart";
    public static  final String Column_CartID = "CartId";
    public static  final String Column_UserID = "UserId";
    public static  final String Column_ItemID = "ItemId";
    public static  final String Column_ItemName = "NameItem";
    public static  final String Column_Price = "Price";
    public static  final String Column_Amount = "Amount";
    public static  final String Column_Condition = "Condition";

    public DB_Cart(Context context) {
        super(context,"MiniShop.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public boolean InsertCart(ModelCart modelCart){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Column_CartID, modelCart.getCartId());
        cv.put(Column_UserID, modelCart.getUserid());
        cv.put(Column_ItemID,modelCart.getItemId() );
        cv.put(Column_ItemName, modelCart.getNameItem());
        cv.put(Column_Price, modelCart.getPrice());
        cv.put(Column_Amount,modelCart.getAmount());
        cv.put(Column_Condition, modelCart.getCondition());
        long insert = db.insert(Cart_table, null ,cv);
        if(insert==-1){
            return false;
        }
        else {
            return true;
        }

    }
    public boolean InsertUserCart(ModelUserCart modelUserCart){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Column_UserID, modelUserCart.getUser_id());
        cv.put(Column_CartID,modelUserCart.getCart_id() );
        long insert = db.insert("UserCart", null ,cv);
        if(insert==-1){
            return false;
        }
        else {
            return true;
        }

    }
    public boolean UpdateAddCart(int userid, int itemid, int amountupdate){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Column_Amount,amountupdate);
        long update = db.update(Cart_table,cv,"UserId=? and ItemId=?", new String[]{"" + userid + "", "" + itemid + ""});
        if(update==-1){
            return false;
        }
        else {
            return true;
        }

    }
    public boolean UpdateaAfterPay(int userid, int itemid){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Column_Condition,1);
        long update = db.update(Cart_table,cv,"UserId=? and ItemId=?", new String[]{"" + userid + "", "" + itemid + ""});
        if(update==-1){
            return false;
        }
        else {
            return true;
        }

    }
    public boolean DeleteCart(int Id) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("Select * from Cart where CartId = " + Id, null);
        if (cursor.getCount() > 0) {
            long delete = db.delete(Cart_table, "CartId =?", new String[]{"" + Id + ""});
            if (delete == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public boolean DeleteCartByUser(int UserId) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("Select * from Cart where UserId = " + UserId, null);
        if (cursor.getCount() > 0) {
            long delete = db.delete(Cart_table, "UserId =?", new String[]{"" + UserId + ""});
            if (delete == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }


    public String checkItemID(int idItem){
        String check = "Khong co";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from Cart Where ItemId = " + idItem, null);
        cursor.moveToFirst();
        if(cursor!=null){
            return check = "co";
        }
        return  check;

    }
    public boolean getCart(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Count(*) FROM Cart",null);
        cursor.moveToFirst();
        int check = cursor.getInt(0);
        if(check!=0){
            return true;
        }
        return  false;

    }
    public boolean Check_User_Item(int userid, int itemid){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Count(*) FROM Cart WHERE UserId ="+ userid +" and ItemId = "+ itemid,null);
        cursor.moveToFirst();
        int check = cursor.getInt(0);
        if(check!=0){
            return true;
        }else {
            return false;
        }

    }
    public int getAmountByUserId(int userid, int itemid){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Cart WHERE UserId ="+ userid +" and ItemId = "+ itemid,null);
        cursor.moveToFirst();
        int check = 0;
        check = cursor.getInt(5);
        return check;

    }
    public int MaxCartId(){
        int check = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cs = db.rawQuery("SELECT Max(CartId) as max FROM Cart",null);
        if (cs != null){
            cs.moveToFirst();
            check = cs.getInt(0);
        }
        return  check;
    }
    public List<ModelCart> getCartsByIdUser(int iduser) {
        List<ModelCart> carts = new ArrayList<>();
        String script = "Select * from Cart Where Condition = 1  AND UserId = " + iduser;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(script, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            ModelCart cart = new ModelCart(cursor.getInt(0),cursor.getInt(1),  cursor.getInt(2), cursor.getString(3), cursor.getDouble(4), cursor.getInt(5),cursor.getInt(6));
            carts.add(cart);
            cursor.moveToNext();
        }
        return carts;
    }

}
