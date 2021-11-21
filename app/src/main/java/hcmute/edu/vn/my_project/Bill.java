package hcmute.edu.vn.my_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Control.DB_Bill;
import Control.DB_Cart;
import Control.SQLiteHelper;
import Model.ModelBill;
import Model.ModelCart;
import Model.UserModel;

public class Bill extends AppCompatActivity {
    UserModel user;
    BillAdapter adapter;
    ImageButton btnBackHome;
    TextView tvUserBill, tvTotal;
    RecyclerView recyclerView;
    AppCompatButton btnConfirm;
    List<ModelCart> carts;
    DB_Cart databaseCart = new DB_Cart(Bill.this);
    DB_Bill databaseBill = new DB_Bill(Bill.this);
    SQLiteHelper sqLiteHelper;
    ModelCart modelCart;
    int total = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        sqLiteHelper = new SQLiteHelper(this,"MiniShop.db", null, 1);
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS Bill ( BillId INTEGER PRIMARY KEY, UserId INTEGER, TotalPrice INTEGER)");
        btnBackHome = findViewById(R.id.btn_home);
        tvUserBill = findViewById(R.id.tvUserBill);
        recyclerView = findViewById(R.id.recyclerView);
        tvTotal = findViewById(R.id.tvTotalBill);
        btnConfirm = findViewById(R.id.btn_Confirm);
        user = (UserModel) getIntent().getSerializableExtra("user");
        tvUserBill.setText(user.getPhone()+" ("+ user.getName()+")");
        int iduser = user.getId();
        btnBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new  Intent(getBaseContext(), HomePage.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
        String kidpay = getIntent().getStringExtra("kindpay");
        //pay all
        if(kidpay.equals("pay all")) {
            carts = databaseCart.getCartsByIdUser(user.getId());
            int count = carts.size();
            for (int i = 0; i < count; i++) {
                total = total + (int) carts.get(i).getPrice() * carts.get(i).getAmount();
            }

            tvTotal.setText(standardizeProductPrice(total));
            if (carts.size() != 0) {
                adapter = new BillAdapter(this, carts, user);
                recyclerView.setAdapter(adapter);
                StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(gridLayoutManager);

            } else {
                recyclerView.setVisibility(View.INVISIBLE);
            }
            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ModelBill modelBill;
                    if (databaseBill.getBill().getCount() < 1) {
                        modelBill = new ModelBill(1, iduser, total);
                    } else {

                        modelBill = new ModelBill(databaseBill.MaxBillId() + 1, iduser, total);
                    }
                    boolean resultInsert = databaseBill.InsertBill(modelBill);
                    if (resultInsert == true) {
                        for (int i = 0; i < carts.size(); i++) {
                            databaseCart.DeleteCartByUser(iduser);
                        }
                        Toast.makeText(Bill.this, "Add Successful Bill", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(v.getContext(), AddCart.class);
                        intent.putExtra("user", user);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Bill.this, "Add Failed Bill", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
        //pay 1 item
        else{
            modelCart = (ModelCart) getIntent().getSerializableExtra("cart");
            List<ModelCart> modelCarts = new ArrayList<>();
            modelCarts.add(modelCart);
            if (modelCarts.size() != 0) {
                adapter = new BillAdapter(this, modelCarts, user);
                recyclerView.setAdapter(adapter);
                StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(gridLayoutManager);

            } else {
                recyclerView.setVisibility(View.INVISIBLE);
            }
            int totalprice = (int)(modelCart.getAmount()*modelCart.getPrice());
            tvTotal.setText(standardizeProductPrice(totalprice));
            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ModelBill modelBill;
                    if (databaseBill.getBill().getCount() < 1) {
                        modelBill = new ModelBill(1, iduser, totalprice);
                    } else {
                        modelBill = new ModelBill(databaseBill.MaxBillId() + 1, iduser, totalprice);
                    }
                    boolean resultInsert = databaseBill.InsertBill(modelBill);
                    if (resultInsert == true) {
                        databaseCart.DeleteCart(modelCart.getCartId());
                        Toast.makeText(Bill.this, "Add Successful Bill", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(v.getContext(), AddCart.class);
                        intent.putExtra("user", user);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Bill.this, "Add Failed Bill", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
    private String standardizeProductPrice(int price) {
        String priceInString = String.valueOf(price);
        String result = "";
        int i = priceInString.length() - 1, count = 0;
        while (i >= 0) {
            result += priceInString.substring(i, i + 1);
            count++;
            if (count == 3 && i != 0) {
                result += ".";
                count = 0;
            }
            i--;
        }
        return new StringBuilder(result).reverse().toString() + "đ";
    }
}