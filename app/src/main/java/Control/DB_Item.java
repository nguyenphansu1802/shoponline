package Control;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import Model.ModelItem;

public class DB_Item extends SQLiteOpenHelper {
    public static  final String Item_table = "Item";
    public static  final String Column_ID = "Id";
    public static  final String Column_Name = "Name_Item";
    public static  final String Column_Type = "Type";
    public static  final String Column_Price = "Price";
    public static  final String Column_Total = "Total";
    public static  final String Column_Condition= "Condition";
    public static  final String Column_Image = "Image";

    public DB_Item( Context context) {
        super(context, "MiniShop.db", null, 1);
    }

//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        //String createTableStatement = "CREATE TABLE " + Item_table +"(" + Column_ID + " INTEGER PRIMARY KEY, " + Column_Name + " STRING, " + Column_Type + " STRING, " + Column_Price+ " DOUBLE, " + Column_Total + " INTEGER, " + Column_Amount + " INTEGER, "+ Column_Image + " BLOB)";
//        String createTableStatement = "CREATE TABLE Item ( Id INTEGER PRIMARY KEY)";
//        db.execSQL(createTableStatement);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("drop Table if exists "+ Item_table);
//        onCreate(db);
//    }

//    public boolean InsertItem(ModelItem modelItem){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put(Column_ID, modelItem.getId_item());
////        cv.put(Column_Name, modelItem.getName());
////        cv.put(Column_Type, modelItem.getType());
////        cv.put(Column_Price, modelItem.getPrice());
////        cv.put(Column_Total, modelItem.getTotal());
////        cv.put(Column_Amount, modelItem.getAmount());
////        cv.put(Column_Image, modelItem.getImage());
//        long insert = db.insert(Item_table, null ,cv);
//        if(insert==-1){
//            return false;
//        }
//        else {
//            return true;
//        }
//
//    }
    public boolean InsertItem(ModelItem modelItem){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Column_ID, modelItem.getId_item());
        cv.put(Column_Name,modelItem.getName_item() );
        cv.put(Column_Type, modelItem.getType());
        cv.put(Column_Price, modelItem.getPrice());
        cv.put(Column_Total, modelItem.getTotal());
        cv.put(Column_Image, modelItem.getImage());
        cv.put(Column_Condition, modelItem.getCondition());
        long insert = db.insert(Item_table, null ,cv);
        if(insert==-1){
            return false;
        }
        else {
            return true;
        }

    }
    public boolean UpdateItem(ModelItem modelItem){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
//        cv.put(Column_ID, modelItem.getId_item());
        cv.put(Column_Name,modelItem.getName_item() );
        cv.put(Column_Type, modelItem.getType());
        cv.put(Column_Price, modelItem.getPrice());
        cv.put(Column_Total, modelItem.getTotal());
        cv.put(Column_Image, modelItem.getImage());
        cv.put(Column_Condition, modelItem.getCondition());
        int column = modelItem.getId_item();
        long update = db.update(Item_table,cv,"Id=?", new String[]{"" + column + ""});
        if(update==-1){
            return false;
        }
        else {
            return true;
        }

    }
    public boolean UpdateItemAmount(int iditem, int newamount){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Column_Total,newamount);
        long update = db.update(Item_table,cv,"Id=?", new String[]{"" + iditem + ""});
        if(update==-1){
            return false;
        }
        else {
            return true;
        }

    }
        public boolean DeleteItem(int Id){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("Select * from Item where Id = "+ Id, null );
        if(cursor.getCount()>0)
        {
            long delete = db.delete(Item_table,"Id=?", new String[]{""+Id+""});
            if(delete==-1){
                return false;
            }
            else {
                return true;
            }
        }else
        {
            return false;
        }
    }

//    public void insertdata(ModelItem modelItem){
//        SQLiteDatabase database = getWritableDatabase();
//        String sql = "INSERT INTO Item VALUES (?)";
//        SQLiteStatement statement = database.compileStatement(sql);
//        statement.clearBindings();
//        statement.bindString(0,String.valueOf(modelItem.getId_item()));
//        statement.executeInsert();
//    }
    public Cursor getItem(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from Item",null);
        return cursor;

    }
    public boolean CheckIdItem(int id){
        boolean check = false;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cs = db.rawQuery("Select * from Item Where Id = "+ id,null);
        if (cs != null) {
            check = true;
        }
        return check;
    }
    public boolean checkItem(int id){
        String[] columns = {"Id"};
        SQLiteDatabase db =  getReadableDatabase();
        String selection = "Id=?";
        String[] selectionArgs = { ""+id+"" };
        Cursor cursor = db.query(Item_table,columns,selection,selectionArgs,null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        if (count > 0)
            return true;
        else
            return false;
    }
    public Cursor InformationItemById(int id){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cs = db.rawQuery("Select * from Item Where Id = "+ id,null);
        cs.moveToFirst();
        return cs;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public int getItems1() {
        int check = 0;
        List<ModelItem> ItemList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from Item Where Condition = 1", null);
        cursor.moveToFirst();
         while (!cursor.isAfterLast()) {
            ModelItem item = new ModelItem(cursor.getInt(0), cursor.getString(1),cursor.getString(2), cursor.getDouble(3), cursor.getInt(4), cursor.getBlob(5), cursor.getInt(6));
            ItemList.add(item);
            cursor.moveToNext();
            check = check+1;
        }
//        ModelItem item2 = new ModelItem(1,"1","1",1,1,1);
//        ItemList.add(item2);
        check = ItemList.size();
        return check;
    }
    public List<ModelItem> getItems(String type) {
        List<ModelItem> ItemList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from Item Where Condition = 1 and Type = '"+type+"'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ModelItem item = new ModelItem(cursor.getInt(0), cursor.getString(1),cursor.getString(2), cursor.getDouble(3), cursor.getInt(4), cursor.getBlob(5), cursor.getInt(6));
            ItemList.add(item);
            cursor.moveToNext();
        }

        return ItemList;
    }
    public List<ModelItem> getItemsByTextFind(String text) {
        List<ModelItem> ItemList = new ArrayList<>();
        String script = "Select * from Item Where Condition = 1 and Name_Item LIKE '%" + text + "%'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(script, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            ModelItem item = new ModelItem(cursor.getInt(0), cursor.getString(1),cursor.getString(2), cursor.getDouble(3), cursor.getInt(4), cursor.getBlob(5), cursor.getInt(6));
            ItemList.add(item);
            cursor.moveToNext();
        }

        return ItemList;
    }
    public List<ModelItem> getItemsReport() {
        List<ModelItem> ItemList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from Item Where Condition = 1", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ModelItem item = new ModelItem(cursor.getInt(0), cursor.getString(1),cursor.getString(2), cursor.getDouble(3), cursor.getInt(4),  cursor.getBlob(5), cursor.getInt(6));
            ItemList.add(item);
            cursor.moveToNext();
        }

        return ItemList;
    }
}
