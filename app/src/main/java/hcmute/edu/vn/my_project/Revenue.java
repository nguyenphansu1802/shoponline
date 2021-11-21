package hcmute.edu.vn.my_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import Control.DB_Bill;
import Control.DB_Report;
import Model.ModelBill;
import Model.ModelReport;
import Model.UserModel;

public class Revenue extends AppCompatActivity {

    ImageButton btnBackHome;
    RecyclerView recyclerView;
    ImageView imageView;
    TextView textView,textView1;
    List<ModelBill> bills;
    RevenueAdapter adapter;
    DB_Bill databaseBill = new DB_Bill(Revenue.this);
    UserModel user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_report);

        btnBackHome = findViewById(R.id.btn_home);
        recyclerView = findViewById(R.id.recyclerView);
        user = (UserModel) getIntent().getSerializableExtra("user");
        bills = databaseBill.getListBill();

        if (bills.size() != 0) {
            adapter = new RevenueAdapter(this, bills, user);
            recyclerView.setAdapter(adapter);
            StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(gridLayoutManager);
//            textView.setVisibility(View.INVISIBLE);
        } else {
            recyclerView.setVisibility(View.INVISIBLE);
//            textView.setVisibility(View.VISIBLE);
        }
        btnBackHome.setOnClickListener(new View.OnClickListener() {     
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}