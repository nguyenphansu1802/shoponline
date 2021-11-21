package hcmute.edu.vn.my_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import Control.DB_Bill;
import Control.DB_Report;
import Control.SQLiteHelper;
import Model.ModelBill;
import Model.ModelItem;
import Model.ModelReport;
import Model.UserModel;

public class Report extends AppCompatActivity {
    UserModel user;
    ModelItem item;
    EditText ChooseItemReport, contentReport;
    Button ChooseItem;
    ImageButton btn_home;
    AppCompatButton ConfirmReport;
    ModelReport modelReport;
    SQLiteHelper sqLiteHelper;
    DB_Report databaseReport = new DB_Report(Report.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        sqLiteHelper = new SQLiteHelper(this,"MiniShop.db", null, 1);
        //sqLiteHelper.queryData("Drop table Report");
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS Report ( ReportId INTEGER PRIMARY KEY, UserId INTEGER, UserName STRING, ItemId INTEGER, ItemName STRING, Contents STRING)");
        ChooseItemReport = (EditText)findViewById(R.id.etchooseItemReport);
        contentReport = findViewById(R.id.etContentReport);
        ChooseItem = findViewById(R.id.btnChooseReport);
        btn_home = findViewById(R.id.btn_home);
        ConfirmReport = findViewById(R.id.btn_ConfirmReport);
        item = (ModelItem) getIntent().getSerializableExtra("item");
        user = (UserModel) getIntent().getSerializableExtra("user");
        String itemname = item.getName_item();
        String username = user.getName();
        ChooseItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Report.this, ListItem.class);
                intent.putExtra("kind", "chooseReport");
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new  Intent(getBaseContext(), HomePage.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
        if(item != null){
            ChooseItemReport.setText(item.getName_item());
        }
        ConfirmReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInf()){
                    if (databaseReport.getReport().getCount() < 1) {
                        modelReport = new ModelReport(1, user.getId(), user.getName(), item.getId_item(), item.getName_item(), contentReport.getText().toString());
                    } else {
                        modelReport = new ModelReport(databaseReport.MaxReportId() + 1, user.getId(),username , item.getId_item(),itemname , contentReport.getText().toString());
                    }
                    boolean resultInsert = databaseReport.InsertReport(modelReport);
                    if (resultInsert == true)
                    {
                        Toast.makeText(Report.this, "Send Report Successful", Toast.LENGTH_SHORT).show();
                        contentReport.setText("");
                        ChooseItemReport.setText("");
                    } else {
                        Toast.makeText(Report.this, "Send Report Failed", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(Report.this,"Not enough report information!!!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    private boolean checkInf()
    {
        boolean check = false;
        if(ChooseItemReport.getText().toString().trim()!=null
                && contentReport.getText().toString().trim()!=null){
            check = true;
        }
        return check;
    }
}