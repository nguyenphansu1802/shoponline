package hcmute.edu.vn.my_project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import Control.DB_Usertable;
import Model.UserModel;

public class UpdateInformationUser extends AppCompatActivity {
    UserModel user;
    Button btn_AddImage, btn_SignUp, btn_Exit;
    EditText et_Name, et_Email;
    CheckBox chb_male, chb_female;
    private ImageView imageUser;
    private Bitmap imageToStore;
    private static final int PICK_IMAGE_REQUEST=100;
    private Uri imagePilePath;
    DB_Usertable databaseUsertable = new DB_Usertable(UpdateInformationUser.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_information_user);

        btn_AddImage = (Button) findViewById(R.id.AddImage);
        btn_SignUp = (Button) findViewById(R.id.btn_UpdateUser);
        btn_Exit = (Button) findViewById(R.id.btn_Exit);
        et_Name = (EditText) findViewById(R.id.Name_SignUp);
        et_Email = (EditText) findViewById(R.id.Email_SignUp);
        chb_male = (CheckBox) findViewById(R.id.male);
        chb_female = (CheckBox) findViewById(R.id.female);
        imageUser = (ImageView) findViewById(R.id.ImageUser_SignUp);

        user = (UserModel) getIntent().getSerializableExtra("user");
        et_Name.setText(user.getName());
        et_Email.setText(user.getEmail());
        Bitmap image = BitmapFactory.decodeByteArray(user.getImage(), 0, user.getImage().length);
        imageUser.setImageBitmap(image);
        String genderUser = user.getGender();
        if(genderUser.equals("Female")){
            chb_female.isChecked();
        }else{
            chb_male.isChecked();
        }

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
        btn_Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btn_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String gender_user = "";
                if (chb_male.isChecked() == true) {
                    gender_user = "Male";
                } else {
                    gender_user = "Female";
                }

                boolean resultInsert = databaseUsertable.UpdateUser(user.getId(),  et_Name.getText().toString(), gender_user, et_Email.getText().toString(),imageViewToByte(imageUser));
                if (resultInsert == true)
                {
                    Toast.makeText(UpdateInformationUser.this, "Update Successful ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UpdateInformationUser.this, "Update Failed User", Toast.LENGTH_SHORT).show();
                }
                finish();
                Intent intent = new  Intent(getBaseContext(), MainActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);

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
//    private boolean check_inf()
//    {
//        boolean check = false;
//        if(et_Name.getText().toString().trim()!=null
//                && et_Email.getText().toString().trim()!=null
//                && imageUser.getDrawable()!=null){
//            check = true;
//        }
//        return check;
//    }
}