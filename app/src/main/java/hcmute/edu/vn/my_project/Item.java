package hcmute.edu.vn.my_project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.widget.Toast;

import Control.DB_Item;
import Control.SQLiteHelper;
import Model.ModelItem;

public class Item extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText et_IDItem, et_NameItem, et_priceItem, et_totalItem;
    Spinner sn_typeItemTest, sn_typeItem;
    ImageView iv_imageItem;
    CheckBox checkCondition;

    Button btn_findItem, btn_AddItem, btn_updateItem, btn_deleteItem;
    private String SN_Type;
    private Bitmap imageToStore;
    private static final int PICK_IMAGE_REQUEST=100;
    private Uri imagePilePath;
    public static SQLiteHelper sqLiteHelper;
    DB_Item databaseItem = new DB_Item(Item.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        setSpinner();
        sqLiteHelper = new SQLiteHelper(this,"MiniShop.db", null, 1);
        //sqLiteHelper.queryData("Drop table Item");
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS Item ( Id INTEGER PRIMARY KEY, Name_Item  STRING, Type STRING, Price DOUBLE, Total  INTEGER, Image BLOB, Condition INTEGER)");
        et_IDItem = (EditText)findViewById(R.id.edittext_IDProduct);
        et_NameItem = (EditText)findViewById(R.id.edittext_NameProduct);
        et_priceItem = (EditText)findViewById(R.id.edittext_PriceProduct);
        et_totalItem = (EditText)findViewById(R.id.edittext_totalproduct);
        checkCondition = (CheckBox)findViewById(R.id.check_Condition);
        //et_amount = (EditText)findViewById(R.id.edittext_AmountProduct);
        iv_imageItem = (ImageView)findViewById(R.id.imageview_ImageProduct);
        btn_findItem = (Button)findViewById(R.id.btn_SearchProductId);
        btn_AddItem = (Button)findViewById(R.id.btn_AddProduct);
        btn_updateItem = (Button)findViewById(R.id.btn_UpdateProduct);
        btn_deleteItem = (Button)findViewById(R.id.btn_DeleteProduct);
        sn_typeItemTest = (Spinner) findViewById(R.id.spinner1);
        //add item
        btn_AddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    ModelItem modelItem;
                    Cursor cursor = databaseItem.getItem();
                    int condition = -1;
                    if(checkCondition.isChecked()) {
                        condition = 1;
                    } else {
                        condition = 0;
                    }
                    if(check_inf()){
                        modelItem = new ModelItem(Integer.parseInt(et_IDItem.getText().toString()), et_NameItem.getText().toString(), SN_Type, Double.parseDouble(et_priceItem.getText().toString()), Integer.parseInt(et_totalItem.getText().toString()), imageViewToByte(iv_imageItem),condition);
                        if(databaseItem.checkItem(Integer.parseInt(et_IDItem.getText().toString())) == false) {
                            boolean resultInsert = databaseItem.InsertItem(modelItem);
                            if (resultInsert == true) {
                                cleanInf();
                                Toast.makeText(Item.this, "Add Successful Item", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(Item.this, "Add Failed Item", Toast.LENGTH_LONG).show();
                            }
                        }else
                        {
                            Toast.makeText(Item.this, "The ID already exists.", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(Item.this, "Not enough personal information!!!", Toast.LENGTH_LONG).show();
                    }

            }
        });
        btn_updateItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModelItem modelItem;
                Cursor cursor = databaseItem.getItem();
                int condition = -1;
                if(checkCondition.isChecked()) {
                    condition = 1;
                } else {
                    condition = 0;
                }
                if(check_inf()) {
                    modelItem = new ModelItem(Integer.parseInt(et_IDItem.getText().toString()), et_NameItem.getText().toString(), SN_Type, Double.parseDouble(et_priceItem.getText().toString()), Integer.parseInt(et_totalItem.getText().toString()), imageViewToByte(iv_imageItem), condition);
                    if (databaseItem.checkItem(Integer.parseInt(et_IDItem.getText().toString())) == true) {
                        boolean resultUpdate = databaseItem.UpdateItem(modelItem);
                        if (resultUpdate == true) {
                            cleanInf();
                            Toast.makeText(Item.this, "Update Successful Item", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(Item.this, "Update Failed Item", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(Item.this, "ID No Found", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(Item.this, "Not enough personal information!!!", Toast.LENGTH_LONG).show();
                }
            }
        });
        btn_deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(et_IDItem.getText().toString());
                if(et_IDItem.getText().toString().trim()!=null) {
                    if (databaseItem.checkItem(id) == true) {
                        boolean resultUpdate = databaseItem.DeleteItem(id);
                        if (resultUpdate == true) {
                            cleanInf();
                            Toast.makeText(Item.this, "Delete Successful Item", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(Item.this, "Delete Failed Item", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(Item.this, "No Found ID Delete", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(Item.this, "ID must not be empty", Toast.LENGTH_LONG).show();
                }

            }
        });
        btn_findItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(databaseItem.checkItem(Integer.parseInt(et_IDItem.getText().toString())) == true){
                    try {
                    Cursor cursor = databaseItem.InformationItemById(Integer.parseInt(et_IDItem.getText().toString()));
                    et_NameItem.setText(cursor.getString(1));
                    setSpinnerByFind(cursor);
                    et_priceItem.setText(cursor.getString(3));
                    et_totalItem.setText(cursor.getString(4));
                    iv_imageItem.setImageBitmap(imageByteToBitmap(cursor.getBlob(5)));
                    if(cursor.getInt(6)==1){
                        checkCondition.setChecked(true);
                    }else
                    {
                        checkCondition.setChecked(false);
                    }
                    }catch (Exception e){
                        Toast.makeText(Item.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(Item.this, "ID No Found", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    private void setSpinnerByFind(Cursor cursor){
        //set spinner
        List<String> categoriespinner = new ArrayList<String>();
        categoriespinner.add(cursor.getString(2));
        categoriespinner.add("Fruit");
        categoriespinner.add("Vegetable");
        categoriespinner.add("Meat");
        categoriespinner.add("Sea Food");
        categoriespinner.add("Fast Food");
        categoriespinner.add("Household");
        categoriespinner.add("Equipment");
        categoriespinner.add("Other");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoriespinner);
        sn_typeItemTest.setAdapter(dataAdapter);
    }
    private void setSpinner(){
        sn_typeItem = (Spinner) findViewById(R.id.spinner1);
        sn_typeItem.setOnItemSelectedListener(this);
        List<String> categories = new ArrayList<String>();
        categories.add("Fruit");
        categories.add("Vegetable");
        categories.add("Meat");
        categories.add("Sea Food");
        categories.add("Fast Food");
        categories.add("Household");
        categories.add("Equipment");
        categories.add("Other");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sn_typeItem.setAdapter(dataAdapter);
    }
//    public void btnadd(View objectView)
//    {
//        ModelItem modelItem;
//        modelItem = new ModelItem(Integer.parseInt(et_IDItem.getText().toString()));//, et_NameItem.getText().toString(), sn_typeItem.toString(), Double.parseDouble(et_priceItem.getText().toString()), Integer.parseInt(et_totalItem.getText().toString()), Integer.parseInt(et_amount.getText().toString()), imageViewToByte(iv_imageItem));
//        boolean resultInsert = databaseItem.InsertItem(modelItem);
//        if (resultInsert == true) {
//            Toast.makeText(Item.this, "Add Successful Item", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(Item.this, "Add Failed Item", Toast.LENGTH_SHORT).show();
//        }
//
//    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        SN_Type = parent.getItemAtPosition(position).toString();
        //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
    private byte[] imageViewToByte (ImageView image)
    {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
    private Bitmap imageByteToBitmap (byte[] bt)
    {

        Bitmap bitmap = BitmapFactory.decodeByteArray(bt, 0, bt.length);
        return  bitmap;
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
                iv_imageItem.setImageBitmap(imageToStore);
            }
        }catch (Exception e){
            Toast.makeText(this,e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private boolean check_inf()
    {
        boolean check = false;
        if(et_IDItem.getText().toString().trim()!=null
                && et_NameItem.getText().toString().trim()!=null
                && et_priceItem.getText().toString().trim()!=null
                && et_totalItem.getText().toString().trim()!=null

                && iv_imageItem.getDrawable()!=null){
            check = true;
        }
        return check;
    }
    private void cleanInf(){
        et_NameItem.setText(null);
        et_IDItem.setText(null);
        et_priceItem.setText(null);
        et_totalItem.setText(null);
        iv_imageItem.setImageBitmap(null);
        checkCondition.setChecked(true);
        setSpinner();
    }
}