package hcmute.edu.vn.my_project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import Control.DB_Account;
import Control.DB_Usertable;
import Control.SQLiteHelper;
import Model.AccountModel;
import Model.UserModel;

public class NewAccount extends AppCompatActivity {
    Button btn_AddImage, btn_SignUp, btn_Exit;
    EditText et_Name, et_Password, et_PasswordAgain, et_Email, et_Phone;
    CheckBox chb_male, chb_female;
    private ImageView imageUser;
    private Bitmap imageToStore;
    private static final int PICK_IMAGE_REQUEST=100;
    private Uri imagePilePath;
    public static SQLiteHelper sqLiteHelper;
    DB_Account databaseAccount = new DB_Account(NewAccount.this);
    DB_Usertable databaseUsertable = new DB_Usertable(NewAccount.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);
        sqLiteHelper = new SQLiteHelper(this,"MiniShop.db", null, 1);
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS Role( UserId INTEGER PRIMARY KEY, Role INTEGER)");
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS User( Id INTEGER PRIMARY KEY, Name STRING, Gender STRING, Email STRING, Phone INTEGER, Image BLOB)");
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS Account( Id INTEGER PRIMARY KEY, Username INTEGER, Password STRING, Role INTEGER)");
        btn_AddImage = (Button) findViewById(R.id.AddImage);
        btn_SignUp = (Button) findViewById(R.id.btn_SignUp);
        btn_Exit = (Button) findViewById(R.id.btn_Exit);
        et_Name = (EditText) findViewById(R.id.Name_SignUp);
        et_Password = (EditText) findViewById(R.id.Password_SignUp);
        et_PasswordAgain = (EditText) findViewById(R.id.Password_SignUpAgain);
        et_Email = (EditText) findViewById(R.id.Email_SignUp);
        et_Phone = (EditText) findViewById(R.id.Phone_SignUp);
        chb_male = (CheckBox) findViewById(R.id.male);
        chb_female = (CheckBox) findViewById(R.id.female);
        imageUser = (ImageView) findViewById(R.id.ImageUser_SignUp);

        int role = Integer.parseInt(getIntent().getStringExtra("role"));
        btn_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  if(check_inf()) {
                      if(check_password()) {
                          if(databaseAccount.checkPhone_or_Username(et_Phone.getText().toString()) == false){
                              String gender_user = "";
                              if (chb_male.isChecked() == true) {
                                  gender_user = "Male";
                              } else {
                                  gender_user = "Female";
                              }
                              AccountModel accountModel;
                              UserModel usermodel;
                              Cursor cursor = databaseUsertable.getUser();
                              if (cursor.getCount() < 1) {
                                  accountModel = new AccountModel(1, Integer.parseInt(et_Phone.getText().toString()), et_Password.getText().toString(), 0);
                                  usermodel = new UserModel(1, et_Name.getText().toString(), gender_user, et_Email.getText().toString(), Integer.parseInt(et_Phone.getText().toString()), imageViewToByte(imageUser));
                              } else {
                                  //int max_id = databaseHelper.MaxId();
                                  accountModel = new AccountModel(databaseUsertable.MaxId() + 1, Integer.parseInt(et_Phone.getText().toString()), et_Password.getText().toString(), role);
                                  usermodel = new UserModel(databaseUsertable.MaxId() + 1, et_Name.getText().toString(), gender_user, et_Email.getText().toString(), Integer.parseInt(et_Phone.getText().toString()), imageViewToByte(imageUser));
                              }

                              boolean resultInsert = databaseUsertable.InsertUser(usermodel);
                              if (resultInsert == true)
                              {
                                  databaseAccount.InsertAccount(accountModel);
                                  Toast.makeText(NewAccount.this, "Add Successful User and Account", Toast.LENGTH_SHORT).show();
                              } else {
                                  Toast.makeText(NewAccount.this, "Add Failed User", Toast.LENGTH_SHORT).show();
                              }
                          }else
                          {
                              Toast.makeText(NewAccount.this,"Invalid or used phone number!!!", Toast.LENGTH_LONG).show();
                          }

                      }
                      else{
                          Toast.makeText(NewAccount.this,"Password does not match!!!", Toast.LENGTH_LONG).show();
                      }
                  }
                  else{
                      Toast.makeText(NewAccount.this,"Not enough personal information!!!", Toast.LENGTH_LONG).show();
                  }
            }
        });
        btn_Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        chb_male.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!chb_female.isChecked() && !chb_male.isChecked()) {
                chb_female.setChecked(true);
            }
            if (chb_female.isChecked() && chb_male.isChecked()) {
                chb_female.setChecked(false);
            }
        });
        chb_female.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!chb_male.isChecked() && !chb_female.isChecked()) {
                chb_male.setChecked(true);
            }
            if (chb_male.isChecked() && chb_female.isChecked()) {
                chb_male.setChecked(false);
            }

        });

    }

    public void chooseImage(View objectView)
    {
        try {
            Intent objectIntent = new Intent();
            objectIntent.setType("image/*");
            objectIntent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(objectIntent,PICK_IMAGE_REQUEST);
        }
        catch (Exception e){
            Toast.makeText(this,e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data !=null && data.getData()!=null){
                imagePilePath = data.getData();
                imageToStore = MediaStore.Images.Media.getBitmap(getContentResolver(),imagePilePath);
                imageUser.setImageBitmap(imageToStore);
            }
        }catch (Exception e){
            Toast.makeText(this,e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private byte[] imageViewToByte (ImageView image)
    {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
    private boolean check_inf()
    {
        boolean check = false;
        if(et_Name.getText().toString().trim()!=null
        && et_Password.getText().toString().trim()!=null
        && et_PasswordAgain.getText().toString().trim()!=null
        && et_Email.getText().toString().trim()!=null
        && et_Phone.getText().toString().trim()!=null
        && imageUser.getDrawable()!=null){
            check = true;
        }
        return check;
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
    private boolean check_phone()
    {
        boolean check = false;
        String phone = et_Phone.getText().toString();
        if(phone.length()==10){
            check = true;
        }
        return check;
    }

}