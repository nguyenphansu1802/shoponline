package Control;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.os.strictmode.SqliteObjectLeakedViolation;

import java.io.ByteArrayOutputStream;

import Model.UserModel;

public class DB_Usertable extends SQLiteOpenHelper {
    public static  final String User = "User";
    public static  final String Column_ID = "Id";
    public static  final String Column_Name = "Name";
    public static  final String Column_Gender = "Gender";
    public static  final String Column_Password = "Password";
    public static  final String Column_Email = "Email";
    public static  final String Column_Phone = "Phone";
    public static  final String Column_Image = "Image";
    public DB_Usertable(Context context) {
        super(context,"MiniShop.db", null, 1);
    }
    private ByteArrayOutputStream objectByteArrayOutputStream;
    private Bitmap imageUser;
    @Override
    public void onCreate(SQLiteDatabase db) {
        //String createTableStatement = "CREATE TABLE " + User_table +"(" + Column_ID + " INTEGER PRIMARY KEY, " + Column_Name + " STRING, " + Column_Gender+ " STRING, " + Column_Email + " STRING, " + Column_Phone + " INTEGER, "+ Column_Image + " BLOB)";
        //String createTableStatementAccount = "CREATE TABLE Account ( Id INTEGER PRIMARY KEY, Username INTEGER, Password STRING, Role INTEGER)";
        //db.execSQL(createTableStatement);
        //db.execSQL(createTableStatementAccount);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("drop Table if exists User_table");
        //db.execSQL("drop Table if exists Account");
        //onCreate(db);
    }
    public void queryData(String sql){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sql);
    }
    public void insertDataUser(UserModel userModel)
    {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "INSERT INTO User_table VALUES (NULL, ?, ?, ?, ?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();
//        statement.bindString(0, String.valueOf(userModel.getId()));
        statement.bindString(1,userModel.getName());
        statement.bindString(2, userModel.getGender());
        statement.bindString(3, userModel.getEmail());
        statement.bindString(4, String.valueOf(userModel.getPhone()));
        statement.bindBlob(5,userModel.getImage());
        statement.executeInsert();
    }
    public boolean InsertUser(UserModel userModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
//        Bitmap imageToUserBitmap = userModel.getImage();
//        objectByteArrayOutputStream = new ByteArrayOutputStream();
//        imageToUserBitmap.compress(Bitmap.CompressFormat.JPEG, 100, objectByteArrayOutputStream);
//        imageUser = objectByteArrayOutputStream.toByteArray();
        cv.put(Column_ID, userModel.getId());
        cv.put(Column_Name, userModel.getName());
        cv.put(Column_Gender, userModel.getGender());
        cv.put(Column_Email, userModel.getEmail());
        cv.put(Column_Phone, userModel.getPhone());
        cv.put(Column_Image, userModel.getImage());
        long insert = db.insert(User, null ,cv);
        if(insert==-1){
            return false;
        }
        else {
            return true;
        }

    }
    public int MaxId(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cs = db.rawQuery("Select Max(Id) as max from User",null);
        if (cs != null)
            cs.moveToFirst();
        else
            return 0;
        return (Integer.parseInt(cs.getString(0)));
    }

//    public boolean UpdateUser(UserModel userModel){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//        //cv.put(Column_ID, userModel.getId());
//        cv.put(Column_Username, userModel.getUser_name());
//        cv.put(Column_Password, userModel.getPassword());
//        cv.put(Column_Email, userModel.getEmail());
//        cv.put(String.valueOf(Column_Phone), userModel.getPhone());
//        Cursor cursor = db.rawQuery("Select * from User_table where Id = ?", new String[Column_ID] );
//        if(cursor.getCount()>0)
//        {
//        long update = db.update(User_table,cv,"Id=?", new String[Column_ID]);
//        if(update==-1){
//            return false;
//        }
//        else {
//            return true;
//        }
//        }else
//            {
//                return false;
//            }
//
//
//    }
//    public boolean DeleteUser(UserModel userModel){
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        Cursor cursor = db.rawQuery("Select * from User_table where Id = ?", new String[Column_ID] );
//        if(cursor.getCount()>0)
//        {
//            long delete = db.delete(User_table,"Id=?", new String[Column_ID]);
//            if(delete==-1){
//                return false;
//            }
//            else {
//                return true;
//            }
//        }else
//        {
//            return false;
//        }
//    }
    public Cursor getUser(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from User",null);
        return cursor;

    }
//    public List<UserModel> getEveryOne(){
//        List<UserModel> returnList = new ArrayList<>();
//        String queryString = "SELECT * FROM " + User_table;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(queryString, null);
//        if (cursor.moveToFirst()){
//            do{
//                int userId = cursor.getInt(0);
//                String username = cursor.getString(1);
//                String pass = cursor.getString(1);
//                String email = cursor.getString(1);
//                int phone = cursor.getInt(1);
//                UserModel newUser = new UserModel(userId, username,pass,email,phone);
//                returnList.add(newUser);
//            }while (cursor.moveToNext());
//        }
//        else {
//
//        }
//        cursor.close();
//        db.close();
//        return  returnList;
//    }
    public UserModel getUserByPhone(int phone){
        UserModel userModel = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from User Where Phone = "+ phone, null);
        cursor.moveToFirst();
        if(cursor!= null){
            userModel = new UserModel(cursor.getInt(0), cursor.getString(1),cursor.getString(2), cursor.getString(3), cursor.getInt(4),  cursor.getBlob(5));
        }
        return userModel;
    }
    public boolean UpdateUser(int IdUser, String NameUser, String Gender, String Email, byte[] image){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Column_Name,NameUser);
        cv.put(Column_Gender,Gender);
        cv.put(Column_Email,Email);
        cv.put(Column_Image,image);
        long update = db.update(User,cv,"Id=?", new String[]{"" + IdUser + ""});
        if(update==-1){
            return false;
        }
        else {
            return true;
        }

    }
    public boolean CheckPass(int IdUser, String OldPass){
        boolean check = false;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cs = db.rawQuery("Select * from Account Where Id = "+ IdUser +" and Password = '"+OldPass+"'",null);
        if (cs != null) {
            check = true;
        }
        return check;
    }
    public boolean ChangePass(int IdUser, String NewPass){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Column_Password, NewPass);
        long update = db.update("Account",cv,"Id=?", new String[]{"" + IdUser + ""});
        if(update==-1){
            return false;
        }
        else {
            return true;
        }

    }
    public String GetNameByIdUser(int IdUser){
        String name = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cs = db.rawQuery("Select * from User Where Id = "+ IdUser,null);
        cs.moveToFirst();
        if (cs != null) {
            name = cs.getString(1);
        }
        return name;
    }

}
