package Control;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {

    public SQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void queryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

//    public int getItems() {
//        int check = 0;
//        List<ModelItem> ItemList = new ArrayList<>();
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery("Select * from Item", null);
//        cursor.moveToFirst();
//       // while (!cursor.isAfterLast()) {
//            //ModelItem item = new ModelItem(cursor.getInt(0), cursor.getString(1),cursor.getString(2), cursor.getDouble(3), cursor.getInt(4), cursor.getInt(5), cursor.getBlob(6));
//            //ItemList.add(item);
////            cursor.moveToNext();
////        }
//        ModelItem item2 = new ModelItem(1,"1","1",1,1,1);
//        ItemList.add(item2);
//        check = ItemList.size();
//        return check;
//    }
    public int getItems() {
        int check = 0;
//        List<ModelItem> ItemList = new ArrayList<>();
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery("Select * from Item", null);
//        cursor.moveToFirst();
//        // while (!cursor.isAfterLast()) {
//        //ModelItem item = new ModelItem(cursor.getInt(0), cursor.getString(1),cursor.getString(2), cursor.getDouble(3), cursor.getInt(4), cursor.getInt(5), cursor.getBlob(6));
//        //ItemList.add(item);
////            cursor.moveToNext();
////        }
//        ModelItem item2 = new ModelItem(1,"1","1",1,1,1);
//        ItemList.add(item2);
//        check = ItemList.size();
        return check;
    }
    public Cursor getCart(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from Cart",null);
        return cursor;

    }
    public int MaxCartId(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cs = db.rawQuery("Select Max(CartId) as max from Cart",null);
        if (cs != null)
            cs.moveToFirst();
        else
            return 0;
        return (Integer.parseInt(cs.getString(0)));
    }

}
