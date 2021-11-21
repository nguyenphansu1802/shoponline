package hcmute.edu.vn.my_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import Control.DB_Item;
import Control.SQLiteHelper;
import Model.ModelItem;
import Model.UserModel;

public class ListItem extends AppCompatActivity {
    ImageButton btnBackHome;
    TextView txtNameShop;
    RecyclerView recyclerView;
    ImageView imageView;
    TextView textView,textView1;
    List<ModelItem> items;
    ItemAdapter adapter;
    UserModel user;
    public static SQLiteHelper sqLiteHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);
        //getSupportActionBar().hide();

        btnBackHome = findViewById(R.id.btn_home);
        txtNameShop = findViewById(R.id.Name_shop);
        recyclerView = findViewById(R.id.recyclerView);
        imageView = findViewById(R.id.imageView);

        user = (UserModel) getIntent().getSerializableExtra("user");

        DB_Item databaseItem = new DB_Item(ListItem.this);
        int t = databaseItem.getItems1();

        String kind = getIntent().getStringExtra("kind");

        if(kind.equals("available")){
            String type = getIntent().getStringExtra("type");
            txtNameShop.setText("Shop "+ type);
            items = databaseItem.getItems(type);

        }
        if (kind.equals("chooseReport")) {
            txtNameShop.setText("Item Report");
            items = databaseItem.getItemsReport();
        }
        if (kind.equals("text")) {
            String textfind = getIntent().getStringExtra("text");
            items = databaseItem.getItemsByTextFind(textfind);
        }
        if (items.size() != 0) {
            adapter = new ItemAdapter(this, items, user);
            recyclerView.setAdapter(adapter);
            StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(gridLayoutManager);
//            textView.setVisibility(View.INVISIBLE);
        } else {
            recyclerView.setVisibility(View.INVISIBLE);
//            textView.setVisibility(View.VISIBLE);
        }
        btnBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new  Intent(getBaseContext(), HomePage.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
    }

}