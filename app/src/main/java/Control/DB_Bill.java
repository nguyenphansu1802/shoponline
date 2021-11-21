package Control;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import Model.ModelBill;
import Model.ModelCart;
import Model.ModelReport;

public class DB_Bill extends SQLiteOpenHelper {
    public static  final String Bill_table = "Bill";
    public static  final String Column_BillID = "BillId";
    public static  final String Column_UserID = "UserId";
    public static  final String Column_Total = "TotalPrice";
    public DB_Bill(Context context) {
        super(context,"MiniShop.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public boolean InsertBill(ModelBill modelBill){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Column_BillID, modelBill.getBillId());
        cv.put(Column_UserID, modelBill.getUserId());
        cv.put(Column_Total, modelBill.getTotalPrice());
        long insert = db.insert(Bill_table, null ,cv);
        if(insert==-1){
            return false;
        }
        else {
            return true;
        }
    }
    public Cursor getBill(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cs = db.rawQuery("Select * from Bill",null);
        return cs;

    }
    public int MaxBillId(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cs = db.rawQuery("Select Max(BillId) as max from Bill",null);
        if (cs != null)
            cs.moveToFirst();
        else
            return 0;
        return Integer.parseInt(cs.getString(0));
    }
    public List<ModelBill> getListBill() {
        List<ModelBill> billLists = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from Bill", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ModelBill bill = new ModelBill(cursor.getInt(0), cursor.getInt(1),cursor.getDouble(2));
            billLists.add(bill);
            cursor.moveToNext();
        }

        return billLists;
    }
    public boolean DeleteBill(int BillId) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("Select * from Bill where BillId = " + BillId, null);
        if (cursor.getCount() > 0) {
            long delete = db.delete(Bill_table, "BillId =?", new String[]{"" + BillId + ""});
            if (delete == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

}
