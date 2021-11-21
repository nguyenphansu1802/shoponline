package hcmute.edu.vn.my_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import Control.DB_Usertable;
import Model.UserModel;

public class ChangePassword extends AppCompatActivity {
    EditText et_OldPass, et_Password, et_PasswordAgain;
    UserModel user;
    Button btnChange;
    DB_Usertable databaseUsertable = new DB_Usertable(ChangePassword.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        et_OldPass = (EditText) findViewById(R.id.Old_pass);
        et_Password = (EditText) findViewById(R.id.Password_Change);
        et_PasswordAgain = (EditText) findViewById(R.id.PasswordAgain_Change);
        user = (UserModel) getIntent().getSerializableExtra("user");
        btnChange = findViewById(R.id.btn_changepass);
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check_password()==true){
                    if(databaseUsertable.CheckPass(user.getId(),et_OldPass.getText().toString()) == true ) {
                        boolean resultChange = databaseUsertable.ChangePass(user.getId(),et_Password.getText().toString());
                        if (resultChange == true) {
                            Toast.makeText(ChangePassword.this, "Change Successful ", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ChangePassword.this, "Change Failed User", Toast.LENGTH_SHORT).show();
                        }
                        finish();
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent);
                    }
                }
                else{
                    Toast.makeText(ChangePassword.this,"Password does not match!!!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private boolean check_password()
    {
        boolean check = false;
        String pass1 = et_Password.getText().toString().trim();
        String pass2 = et_PasswordAgain.getText().toString().trim();
        if(pass1.equals(pass2)){
            check = true;
        }
        return check;
    }
}