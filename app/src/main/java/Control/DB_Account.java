package Control;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

import Model.AccountModel;

public class DB_Account extends SQLiteOpenHelper {
    public static  final String Name_table = "Account";
    public static  final String Column_ID = "Id";
    public static  final String Column_Password = "Password";
    public static  final String Column_Username= "Username";
    public static  final String Column_Role = "Role";
    private static final String DATABASE_PATH = "C:/Users/PhanSu/OneDrive/Tài liệu/AndroidStudio/DeviceExplorer/emulator-5554/data/data/hcmute.edu.vn.my_project/databases";
    public DB_Account(Context context) {
        super(context,"MiniShop.db", null, 1);
    }
    private ByteArrayOutputStream objectByteArrayOutputStream;
    private Bitmap imageUser;
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        String createTableStatement = "CREATE TABLE Account(Id INTEGER PRIMARY KEY, Username INTEGER, Password STRING, Role INTEGER)";
//        db.execSQL(createTableStatement);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("drop Table if exists "+Name_table);
//    }
    public void queryData(String sql){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sql);
    }

    public boolean InsertAccount(AccountModel accountModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Column_ID, accountModel.getId());
        cv.put(Column_Username, accountModel.getUser());
        cv.put(Column_Password, accountModel.getPassword());
        cv.put(Column_Role, accountModel.getRole());
        long insert = db.insert(Name_table, null ,cv);
        if(insert==-1){
            return false;
        }
        else {
            return true;
        }

    }
    public int MaxId(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cs = db.rawQuery("Select Max(Id) as max from "+Name_table,null);
        if (cs != null)
            cs.moveToFirst();
        else
            return 0;
        return (Integer.parseInt(cs.getString(0)));
    }

    public Cursor getAccount(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from "+Name_table,null);
        return cursor;

    }
    public boolean checkPhone_or_Username(String username){
        String[] columns = {"Id"};
        SQLiteDatabase db =  getReadableDatabase();
        String selection = "Username=?";
        String[] selectionArgs = { username};
        Cursor cursor = db.query(Name_table,columns,selection,selectionArgs,null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if (count > 0)
            return true;
        else
            return false;
    }
    public boolean checkUser(String username, String password){
        String[] columns = {"Id"};
        SQLiteDatabase db =  getReadableDatabase();
        String selection = "Username=? and Password = ?";
        String[] selectionArgs = { username, password };
        Cursor cursor = db.query(Name_table,columns,selection,selectionArgs,null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if (count > 0)
            return true;
        else
            return false;
    }
    public int  getRoleByUsername(String User){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Account Where Username = " + User,null);
        cursor.moveToFirst();
        int check = -1;
            check = cursor.getInt(3);
        return  check;

    }

//    public int FindIDByUsername(String username){
//
//    }
    private SQLiteDatabase openDatabase(){
        SQLiteDatabase db = this.getWritableDatabase();
        String path = DATABASE_PATH + "MiniShop.db";
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
        return db;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
