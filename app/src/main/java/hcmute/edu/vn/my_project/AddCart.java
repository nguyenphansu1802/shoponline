package hcmute.edu.vn.my_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import Control.DB_Cart;
import Model.ModelCart;
import Model.UserModel;

public class AddCart extends AppCompatActivity {
    View view;
    ImageView imageView;
    TextView textView;
    ImageView btnBackHome;
    RecyclerView recyclerView;
    AppCompatButton btnPayAll;
    UserModel user;
    List<ModelCart> carts;
    CartAdapter adapter;
    DB_Cart databaseCart = new DB_Cart(AddCart.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cart);

        recyclerView = findViewById(R.id.recyclerViewCart);
        imageView = findViewById(R.id.imageView);
        btnBackHome = findViewById(R.id.btn_backhome);
        btnPayAll = findViewById(R.id.btn_PayAll);
        user = (UserModel) getIntent().getSerializableExtra("user");
        int iduser = user.getId();

        carts = databaseCart.getCartsByIdUser(iduser);
        if (carts.size() != 0) {
            adapter = new CartAdapter(this, carts, user);
            recyclerView.setAdapter(adapter);
            StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(gridLayoutManager);
        } else {
            recyclerView.setVisibility(View.INVISIBLE);
        }
        btnBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new  Intent(getBaseContext(), HomePage.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
        btnPayAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Bill.class);
                intent.putExtra("user", user);
                intent.putExtra("kindpay", "pay all");
                startActivity(intent);
            }
        });
        
    }
}