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

import Model.ModelItem;
import Model.UserModel;

public class InformationAccount extends AppCompatActivity {
    UserModel user;
    ImageView avatar;
    AppCompatButton btnLogout;
    TextView InfUser, ChangePassword, NameUser, EmailUser, ReportItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_account);
        avatar = findViewById(R.id.imgUser);
        btnLogout = findViewById(R.id.btnSignOut);
        NameUser = findViewById(R.id.txtNameUser);
        EmailUser = findViewById(R.id.txtEmailUser);
        InfUser = findViewById(R.id.tvInfUser);
        ChangePassword = findViewById(R.id.txChangePassword);
        ReportItem = findViewById(R.id.tvReport);
        user = (UserModel) getIntent().getSerializableExtra("user");
        Bitmap image = BitmapFactory.decodeByteArray(user.getImage(), 0, user.getImage().length);
        avatar.setImageBitmap(image);
        NameUser.setText(user.getName());
        EmailUser.setText(user.getEmail());
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new  Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
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
        ChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new  Intent(getBaseContext(), ChangePassword.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
        ReportItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ModelItem modelItem = new ModelItem();
//                Intent intent = new  Intent(getBaseContext(), Report.class);
//                intent.putExtra("item", modelItem );
//                intent.putExtra("user", user);
//                startActivity(intent);
                Intent intent = new Intent(v.getContext(), AddCart.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });



    }
}