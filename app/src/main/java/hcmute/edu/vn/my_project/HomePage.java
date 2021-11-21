package hcmute.edu.vn.my_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import Model.UserModel;

public class HomePage extends AppCompatActivity {
    TextView tv_Username;
    ImageButton btn_UserInf;
    Button  btn_UserCart;
    EditText etFindItems;
    Button btnSeach,btnInfuserBottom;
    Button btnFruit, btnVegetable, btnMeat, btnSeaFish, btnFastFood, btnEquiment, btnHouseHold, btnOther;
    UserModel User;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        tv_Username = (TextView)findViewById(R.id.tv_nameuser_homepage);
        btn_UserInf = (ImageButton) findViewById(R.id.btn_infuser_homepage);
        btn_UserCart = (Button)findViewById(R.id.btn_cart_homepage);

        etFindItems = (EditText)findViewById(R.id.et_findHomepage);
        btnSeach = (Button)findViewById(R.id.btn_seachshomepage);
        btnInfuserBottom = (Button)findViewById(R.id.btn_infuser_bottom);
        btnFruit = (Button)findViewById(R.id.btn_fruit);
        btnMeat = (Button)findViewById(R.id.btn_meat);
        btnVegetable = (Button)findViewById(R.id.btn_vergetable);
        btnFastFood = (Button)findViewById(R.id.btn_fastfood);
        btnSeaFish = (Button)findViewById(R.id.btn_fish);
        btnEquiment = (Button)findViewById(R.id.btn_equipment);
        btnHouseHold = (Button)findViewById(R.id.btn_household);
        btnOther = (Button)findViewById(R.id.btn_other);

        btnFruit.setOnClickListener(this::onClick);
        btnMeat.setOnClickListener(this::onClick);
        btnVegetable.setOnClickListener(this::onClick);
        btnFastFood.setOnClickListener(this::onClick);
        btnSeaFish.setOnClickListener(this::onClick);
        btnEquiment.setOnClickListener(this::onClick);
        btnOther.setOnClickListener(this::onClick);
        //Get IdUser
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        //IdUser = intent.getStringExtra(MainActivity.UserName);
        User =(UserModel) getIntent().getSerializableExtra("user");

        tv_Username.setText(User.getName());

        Bitmap image = BitmapFactory.decodeByteArray(User.getImage(), 0, User.getImage().length);
        btn_UserInf.setImageBitmap(image);
        btn_UserInf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new  Intent(getBaseContext(), InformationAccount.class);
                intent.putExtra("user", User);
                startActivity(intent);
            }
        });
        btnInfuserBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new  Intent(getBaseContext(), InformationAccount.class);
                intent.putExtra("user", User);
                startActivity(intent);
            }
        });
        btnSeach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ListItem.class);
                //intent.putExtra("user", user);
                intent.putExtra("kind", "text");
                intent.putExtra("text", etFindItems.getText().toString().trim());
                intent.putExtra("user", User);
                startActivity(intent);
            }
        });
        btn_UserCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddCart.class);
                intent.putExtra("user", User);
                startActivity(intent);
            }
        });

    }
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), ListItem.class);
        intent.putExtra("user", User);
        intent.putExtra("kind", "available");
        switch (v.getId()) {
            case R.id.btn_fruit:
                intent.putExtra("type", "Fruit");
                break;
            case R.id.btn_vergetable:
                intent.putExtra("type", "Vegetable");
                break;
            case R.id.btn_meat:
                intent.putExtra("type", "Meat");
                break;
            case R.id.btn_fish:
                intent.putExtra("type", "Sea Food");
                break;
            case R.id.btn_fastfood:
                intent.putExtra("type", "Fast Food");
                break;
            case R.id.btn_equipment:
                intent.putExtra("type", "Equipment");
                break;
            case R.id.btn_household:
                intent.putExtra("type", "Household");
            case R.id.btn_other:
                intent.putExtra("type", "Other");
                break;
        }
        startActivity(intent);
    }
}