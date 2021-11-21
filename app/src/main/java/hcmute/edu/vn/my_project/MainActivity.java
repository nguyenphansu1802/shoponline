package hcmute.edu.vn.my_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import Control.DB_Account;
import Control.DB_Usertable;
import Model.UserModel;

public class MainActivity extends AppCompatActivity {
    EditText Username_login, Password_login;
    TextView Sign_up, Forgot_Pass;
    ImageView LinkGmail, LinkFacebook;
    Button btn_Login;
    public static final String UserName = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            Username_login = (EditText) findViewById(R.id.UserName_Login);
            Password_login = (EditText) findViewById(R.id.PassWord_Login);
            Sign_up = (TextView)findViewById(R.id.New_Account);
            Forgot_Pass = (TextView) findViewById(R.id.TextView_ForgotPass);
            LinkGmail = (ImageView) findViewById(R.id.imageView_Gmail);
            LinkFacebook = (ImageView)findViewById(R.id.imageView_Face);
            btn_Login = (Button)findViewById(R.id.btn_Login);

            btn_Login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        DB_Account account = new DB_Account(MainActivity.this);
                        boolean isExist = account.checkUser(Username_login.getText().toString(), Password_login.getText().toString());

                        if(isExist){
                            DB_Usertable databaseUser = new DB_Usertable(MainActivity.this);
                            UserModel userModel = databaseUser.getUserByPhone(Integer.parseInt(Username_login.getText().toString()));
                            int check  = account.getRoleByUsername(Username_login.getText().toString());
                            String page = "";
                            if(check == 0){
                                Intent intent = new Intent(MainActivity.this, Admin.class);
                                intent.putExtra("user", userModel);
                                startActivity(intent);
                            }
                            if(check == 1) {
                                Intent intent = new Intent(MainActivity.this, HomePage.class);
                                intent.putExtra("user", userModel);
                                startActivity(intent);
                            }
                        }
                        else {
                            Password_login.setText(null);
                            Toast.makeText(MainActivity.this, "Login failed. Invalid username or password.", Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        Toast.makeText(MainActivity.this,e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
            //implement Onclick event for Explicit Intent

            Sign_up.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new  Intent(getBaseContext(), NewAccount.class);
                    intent.putExtra("role", "1");
                    startActivity(intent);
                }
            });

            //implement onClick event for Implicit Intent

            Forgot_Pass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new  Intent(getBaseContext(), ForgorPassword.class);
                    startActivity(intent);
                }
            });

            LinkGmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            LinkFacebook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

    }
}