package Control;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import Model.ModelBill;
import Model.ModelItem;
import Model.ModelReport;
import hcmute.edu.vn.my_project.Report;

public class DB_Report extends SQLiteHelper{
    public static  final String Report_table = "Report";
    public static  final String Column_ReportID = "ReportId";
    public static  final String Column_UserID = "UserId";
    public static  final String Column_UserName = "UserName";
    public static  final String Column_ItemID = "ItemId";
    public static  final String Column_ItemName = "ItemName";
    public static  final String Column_Contents = "Contents";
    public DB_Report(Context context) {
        super(context,"MiniShop.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public boolean InsertReport(ModelReport modelReport){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Column_ReportID, modelReport.getReport_id());
        cv.put(Column_UserID, modelReport.getUser_id());
        cv.put(Column_UserName, modelReport.getUsername());
        cv.put(Column_ItemID, modelReport.getItem_id());
        cv.put(Column_ItemName, modelReport.getItemname());
        cv.put(Column_Contents, modelReport.getContents());
        long insert = db.insert(Report_table, null ,cv);
        if(insert==-1){
            return false;
        }
        else {
            return true;
        }
    }
    public int MaxReportId(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cs = db.rawQuery("Select Max(ReportId) as max from Report",null);
        if (cs != null)
            cs.moveToFirst();
        else
            return 0;
        return cs.getInt(0);
    }
    public Cursor getReport(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cs = db.rawQuery("Select * from Report",null);
        return cs;

    }
    public List<ModelReport> getListReport() {
        List<ModelReport> reportLists = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from Report", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ModelReport report = new ModelReport(cursor.getInt(0), cursor.getInt(1),cursor.getString(2), cursor.getInt(3),cursor.getString(4), cursor.getString(5));
            reportLists.add(report);
            cursor.moveToNext();
        }

        return reportLists;
    }
    public boolean DeleteReport(int ReportId) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("Select * from Report where ReportId = " + ReportId, null);
        if (cursor.getCount() > 0) {
            long delete = db.delete(Report_table, "ReportId =?", new String[]{"" + ReportId + ""});
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
