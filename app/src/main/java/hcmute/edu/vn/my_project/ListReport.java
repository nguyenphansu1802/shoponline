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

import Control.DB_Item;
import Control.DB_Report;
import Model.ModelItem;
import Model.ModelReport;
import Model.UserModel;

public class ListReport extends AppCompatActivity {
    ImageButton btnBackHome;
    RecyclerView recyclerView;
    ImageView imageView;
    TextView textView,textView1;
    List<ModelReport> reports;
    ReportAdapter adapter;
    DB_Report databaseReport = new DB_Report(ListReport.this);
    UserModel user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_report);

        btnBackHome = findViewById(R.id.btn_home);
        recyclerView = findViewById(R.id.recyclerView);
        user = (UserModel) getIntent().getSerializableExtra("user");
        reports = databaseReport.getListReport();

        if (reports.size() != 0) {
            adapter = new ReportAdapter(this, reports, user);
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