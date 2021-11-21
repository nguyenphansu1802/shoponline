package hcmute.edu.vn.my_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import Control.DB_Cart;
import Control.DB_Item;
import Control.SQLiteHelper;
import Model.ModelCart;
import Model.ModelItem;
import Model.ModelUserCart;
import Model.UserModel;

public class InformationProduct extends AppCompatActivity {

    ImageView img;
    ImageButton imageButtonSubtract, imageButtonAdd;
    TextView txtAmount;
    TextView txtName, txtPrice;
    ImageButton btnBack, btnCart;
    AppCompatButton btnAddCart, btnReportItem;
    DB_Item databaseItem = new DB_Item(InformationProduct.this);
    DB_Cart databaseCart = new DB_Cart(InformationProduct.this);
    public static SQLiteHelper sqLiteHelper;
    private int amount = 1;
    ModelItem item;
    UserModel user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_product);
        //getSupportActionBar().hide();
        sqLiteHelper = new SQLiteHelper(this,"MiniShop.db", null, 1);
//        sqLiteHelper.queryData("Drop table UserCart");
//        sqLiteHelper.queryData("Drop table Cart");
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS UserCart ( UserId INTEGER, CartId INTEGER)");
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS Cart ( CartId INTEGER PRIMARY KEY, UserId INTEGER, ItemId INTEGER, NameItem STRING, Price DOUBLE, Amount INTEGER, Condition INTEGER)");

        img = findViewById(R.id.img);
        txtName = findViewById(R.id.txtName);
        txtPrice = findViewById(R.id.txtPrice);

        btnAddCart = findViewById(R.id.btnAddCart);
        imageButtonSubtract = (ImageButton)findViewById(R.id.btnSubtractAmount);
        imageButtonAdd = (ImageButton)findViewById(R.id.btnAddAmount);
        txtAmount = (TextView)findViewById(R.id.txtAmount);
        item = (ModelItem) getIntent().getSerializableExtra("item");
        user = (UserModel) getIntent().getSerializableExtra("user");
        btnReportItem = (AppCompatButton)findViewById(R.id.btnReportItem);

        Bitmap image = BitmapFactory.decodeByteArray(item.getImage(), 0, item.getImage().length);
        img.setImageBitmap(image);
        txtName.setText(item.getName_item());
        int idtext = item.getId_item();
        txtPrice.setText(standardizeProductPrice((int) item.getPrice()));
        txtAmount.setText(""+amount+"");
        imageButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int amountcheck = Integer.parseInt(txtAmount.getText().toString());
                int amountitem =  item.getTotal();
                if(amountcheck < amountitem){
                    amountcheck = amountcheck + 1;
                    txtAmount.setText(""+amountcheck+"");
                    amount = amountcheck;
                }else{
                    Toast.makeText(InformationProduct.this,"Amount Max!!!", Toast.LENGTH_LONG).show();
                }
            }
        });
        imageButtonSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int amountcheck = Integer.parseInt(txtAmount.getText().toString());
                if(amountcheck > 1){
                    amountcheck = amountcheck - 1;
                    txtAmount.setText(""+amountcheck+"");
                    amount = amountcheck;
                }else{
                    Toast.makeText(InformationProduct.this,"Amount Min!!!", Toast.LENGTH_LONG).show();
                }
            }
        });


        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModelCart modelCart;
                ModelUserCart user_cart;
                if(item.getTotal()>=amount) {
                    boolean check_added = databaseCart.Check_User_Item(user.getId(), item.getId_item());
                    if (check_added == false) {
                        //ModelCart modelCart = new ModelCart(0, item.getId_item(), item.getName_item(), item.getPrice(), amount, 0);
                        boolean check_id = databaseCart.getCart();
                        if (check_id == false) {
                            modelCart = new ModelCart(0, user.getId(), item.getId_item(), item.getName_item(), item.getPrice(), amount,  item.getCondition());
                            user_cart = new ModelUserCart(user.getId(), 0);
                        } else {
                            modelCart = new ModelCart(databaseCart.MaxCartId() + 1, user.getId(), item.getId_item(), item.getName_item(), item.getPrice(), amount, item.getCondition());
                            user_cart = new ModelUserCart(user.getId(), databaseCart.MaxCartId() + 1);
                        }

                        int newamount = item.getTotal() - amount;
                        boolean resultInsert = databaseCart.InsertCart(modelCart);
                        if (resultInsert == true) {
                            databaseCart.InsertUserCart(user_cart);
                            databaseItem.UpdateItemAmount(item.getId_item(), newamount);
                            Toast.makeText(InformationProduct.this, "Add Successful Cart", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(InformationProduct.this, "Add Failed Cart", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        int newamount = item.getTotal() - amount + databaseCart.getAmountByUserId(user.getId(), item.getId_item());
                        boolean resultInsert = databaseCart.UpdateAddCart(user.getId(), item.getId_item(), amount);
                        if (resultInsert == true) {
                            databaseItem.UpdateItemAmount(item.getId_item(), newamount);
                            Toast.makeText(InformationProduct.this, "Update Successful Cart", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(InformationProduct.this, "Update Failed Cart", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{
                    Toast.makeText(InformationProduct.this, "Amount Not Enough To Add Cart", Toast.LENGTH_SHORT).show();
                }

            }

        });
        btnReportItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InformationProduct.this, Report.class);
                intent.putExtra("user", user);
                intent.putExtra("item", item);
                startActivity(intent);
            }
        });
//        btnAddInCartPay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String a = String.valueOf(idtext);
//                Toast.makeText(InformationProduct.this,"a"+a +databaseCart.checkItemID(idtext), Toast.LENGTH_SHORT).show();
////                if(databaseCart.checkItemID(item.getId_item()) == false) {
////
////                    ModelCart modelCart = new ModelCart(user.getId(), item.getId_item(), item.getName_item(), item.getPrice(), amount, 1);
////                    boolean resultInsert = databaseCart.InsertCart(modelCart);
////                    if (resultInsert == true) {
////                        Toast.makeText(InformationProduct.this, "Add Successful Cart", Toast.LENGTH_SHORT).show();
////                    }
//                //else {
////                        Toast.makeText(InformationProduct.this, "Add Failed Cart", Toast.LENGTH_SHORT).show();
////                    }
////                }else{
////                    Toast.makeText(InformationProduct.this, "Items already in the Cart", Toast.LENGTH_SHORT).show();
////                }
//            }
//
//        });
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