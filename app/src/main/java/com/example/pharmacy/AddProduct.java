package com.example.pharmacy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.icu.util.Freezable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pharmacy.Product.Category;
import com.example.pharmacy.Product.FirebaseFinish;
import com.example.pharmacy.Product.Product;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class AddProduct extends AppCompatActivity {
    Spinner CategoriesSpinner;
    Button ChooseImage;
    Button Insert;
    EditText Name;
    EditText Price;
    EditText Quantity;
    String Image = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        CategoriesSpinner = findViewById(R.id.Categories);
        ChooseImage = findViewById(R.id.ChooseImage);
        Insert = findViewById(R.id.Insert);
        Name = findViewById(R.id.Name);
        Price = findViewById(R.id.Price);
        Quantity = findViewById(R.id.Quantity);
        Category category = new Category();
        category.SelectAll(AddProduct.this, new FirebaseFinish() {
            @Override
            public void GetAllCategories(final ArrayList<Category> Categories) {
                final ArrayList<String> categoriesNames = new ArrayList<String>();
                for (int x = 0; x < Categories.size(); x++) {
                    categoriesNames.add(Categories.get(x).getName());
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, categoriesNames);
                    CategoriesSpinner.setAdapter(adapter);
                }


                Insert.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Image == null){
                            Toast.makeText(getApplicationContext() , "Please choose and image first" , Toast.LENGTH_LONG).show();
                            return;
                        }
                        else{
                            String ProductName = Name.getText().toString();
                            int quantity = Integer.parseInt(Quantity.getText().toString());
                            double price = Double.parseDouble(Price.getText().toString());
                            Product product = new Product(ProductName , Image , Categories.get(CategoriesSpinner.getSelectedItemPosition()).getID() , quantity , price);
                            product.insert(AddProduct.this);
                        }
                    }
                });

            }

            @Override
            public void FinishUploading(String ImagePath) {

            }

            @Override
            public void GetAllProducts(ArrayList<Product> productArrayList) {

            }

            @Override
            public void FinishDownloadingImage(Uri image) {

            }
        });


        ChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK , MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });


    }




    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
           if (data == null){
               return;
           }
           else{
                Image = getPath(getApplicationContext() , data.getData());

           }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static String getPath(Context context, Uri uri ) {
        String result = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver( ).query( uri, proj, null, null, null );
        if(cursor != null){
            if ( cursor.moveToFirst( ) ) {
                int column_index = cursor.getColumnIndexOrThrow( proj[0] );
                result = cursor.getString( column_index );
            }
            cursor.close( );
        }
        if(result == null) {
            result = "Not found";
        }
        return result;
    }
}

//        Categories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                position1 = position;
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });


//        Insert.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (image == null){
//                    Toast toast = Toast.makeText(getApplicationContext() , "You should Select Image" , Toast.LENGTH_SHORT);
//                    toast.show();
//                    return;
//                }
//                Log.d("Category ID" ,""+categoryArrayList.get(Categories.getSelectedItemPosition()).getID());
//                Product product = new Product(Name.getText().toString() , image , categoryArrayList.get(position1).getID() , Integer.parseInt(Quantity.getText().toString()) , Double.parseDouble(Price.getText().toString()));
//                product.insert();
//                Intent intent = new Intent(getApplicationContext() , AdminHome.class);
//                intent.putExtra("fragment" , "Product");
//                startActivity(intent);
//            }
//        });

