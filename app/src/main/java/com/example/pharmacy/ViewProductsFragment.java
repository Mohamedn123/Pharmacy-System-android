package com.example.pharmacy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmacy.Product.Category;
import com.example.pharmacy.Product.FirebaseFinish;
import com.example.pharmacy.Product.Product;
import com.example.pharmacy.Product.product_adapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;

public class ViewProductsFragment extends Fragment {
    FloatingActionButton Add;
    RecyclerView ProductRecyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_view_prodcuts, null);
        Add = root.findViewById(R.id.AddNewProduct);
        ProductRecyclerView = root.findViewById(R.id.ProductsRecyclerView);
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext() , AddProduct.class);
                startActivity(intent);
            }
        });

        final Product product = new Product();
        product.GetAllProducts(root.getContext(), new FirebaseFinish() {
            @Override
            public void GetAllCategories(ArrayList<Category> Categories) {

            }

            @Override
            public void FinishUploading(String ImagePath) {

            }

            @Override
            public void GetAllProducts(ArrayList<Product> productArrayList) {
                ArrayList<String> ImagePaths = new ArrayList<String>();
                ProductRecyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
                product_adapter productAdapter = new product_adapter(productArrayList , root.getContext());
                ProductRecyclerView.setAdapter(productAdapter);

            }

            @Override
            public void FinishDownloadingImage(Uri image) {

            }
        });


        return root;
    }
}
