package hcmute.edu.vn.my_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import Model.UserModel;

public class Admin extends AppCompatActivity {
    UserModel user;
    ImageView avatar;
    AppCompatButton btnLogout;
    TextView InfUser, NameUser,  HomePage, ItemShop, Revenue, ReportList, AdminAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        avatar = findViewById(R.id.imgUser);
        btnLogout = findViewById(R.id.btnSignOut);//
        NameUser = findViewById(R.id.txtNameUser);//

        HomePage = findViewById(R.id.btn_HomePage);//
        ItemShop = findViewById(R.id.btn_aminitem);//
        Revenue = findViewById(R.id.tv_revenue);
        InfUser = findViewById(R.id.tx_information);//
        ReportList = findViewById(R.id.tv_reportlist);
        AdminAccount = findViewById(R.id.tv_newAccountAdmin);//

        user = (UserModel) getIntent().getSerializableExtra("user");
        Bitmap image = BitmapFactory.decodeByteArray(user.getImage(), 0, user.getImage().length);
        avatar.setImageBitmap(image);
        NameUser.setText(user.getName());
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        InfUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new  Intent(getBaseContext(), UpdateInformationUser.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
        HomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new  Intent(getBaseContext(), HomePage.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
        ItemShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new  Intent(getBaseContext(), Item.class);
                startActivity(intent);
            }
        });
        AdminAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new  Intent(getBaseContext(), NewAccount.class);
                intent.putExtra("role", "0");
                startActivity(intent);
            }
        });
        ReportList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new  Intent(getBaseContext(), ListReport.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
        Revenue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new  Intent(getBaseContext(), Revenue.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });


    }
}