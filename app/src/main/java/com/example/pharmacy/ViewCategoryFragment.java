package com.example.pharmacy;
import com.example.pharmacy.Product.FirebaseFinish;
import com.example.pharmacy.Product.Product;
import com.example.pharmacy.Product.category_adapter;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmacy.Product.Category;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ViewCategoryFragment extends Fragment {
    FloatingActionButton AddNewCategory;
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_view_category, null);

        AddNewCategory = root.findViewById(R.id.AddNewCategory);
        recyclerView = root.findViewById(R.id.CategoryRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Category category = new Category();
        category.SelectAll(root.getContext(), new FirebaseFinish() {
            @Override
            public void GetAllCategories(ArrayList<Category> Categories) {
                category_adapter categoryAdapter = new category_adapter(Categories);
                recyclerView.setAdapter(categoryAdapter);
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
        AddNewCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Add New Category");
                final EditText input = new EditText(getContext());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                input.setGravity(Gravity.CENTER);
                builder.setView(input);
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String m_text = input.getText().toString();
                        dialog.cancel();
                        Category category = new Category(m_text);
                        category.insert(root.getContext());

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
        return root;
    }


}
