package com.example.pharmacy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pharmacy.Product.Category;
import com.example.pharmacy.Product.FirebaseFinish;
import com.example.pharmacy.Product.Product;
import com.example.pharmacy.Users.Person;

import java.util.ArrayList;

public class ViewProductUser extends AppCompatActivity {
    TextView Name;
    TextView Price;
    TextView Category1;
    TextView Quantity;
    ImageView ProductImage;
    Button AddTocart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product_user);
        Intent intent = getIntent();
        String ID = intent.getStringExtra("ProductID");
        Name = findViewById(R.id.ProductNameAdmin);
        Price = findViewById(R.id.ProductPriceAdmin);
        Category1 = findViewById(R.id.ProductCategoryAdmin);
        Quantity = findViewById(R.id.ProductQuantityAdmin);
        ProductImage = findViewById(R.id.ProductImageAdmin);
        AddTocart = findViewById(R.id.AddToCart);
        final Product product1 = new Product();
        product1.GetProductByID(ViewProductUser.this, ID, new FirebaseFinish() {
            @Override
            public void GetAllCategories(ArrayList<Category> Categories) {

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

            @Override
            public void GetProductByID(final Product product) {
                Name.setText(product.getName());
                Price.setText(""+product.getPrice());
                Quantity.setText(""+product.getQuantity());
                product1.DownloadImage(product.getImage(), new FirebaseFinish() {
                    @Override
                    public void GetAllCategories(ArrayList<com.example.pharmacy.Product.Category> Categories) {

                    }

                    @Override
                    public void FinishUploading(String ImagePath) {

                    }

                    @Override
                    public void GetAllProducts(ArrayList<Product> productArrayList) {

                    }

                    @Override
                    public void FinishDownloadingImage(Uri image) {
                        Glide.with(getApplicationContext()).load(image.toString()).into(ProductImage);
                    }

                    @Override
                    public void GetProductByID(Product product) {

                    }

                    @Override
                    public void GetCategoryByID(com.example.pharmacy.Product.Category category) {

                    }
                });
                Category category = new Category();
                category.GetNameByID(ViewProductUser.this, product.getCategory(), new FirebaseFinish() {
                    @Override
                    public void GetAllCategories(ArrayList<com.example.pharmacy.Product.Category> Categories) {

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

                    @Override
                    public void GetProductByID(Product product) {

                    }

                    @Override
                    public void GetCategoryByID(com.example.pharmacy.Product.Category category) {
                        Category1.setText(category.getName());
                    }
                });
                AddTocart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Person person = new Person();
                        person.AddTocart(product.getID() ,1 ,product.getPrice() ,ViewProductUser.this);
                    }
                });

            }

            @Override
            public void GetCategoryByID(com.example.pharmacy.Product.Category category) {

            }
        });
    }
}
