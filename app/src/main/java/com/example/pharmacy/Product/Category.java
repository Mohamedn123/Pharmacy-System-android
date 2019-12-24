package com.example.pharmacy.Product;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Category {
    private String ID;
    private String Name;

    public String getID() {
        return ID;
    }

    public String getName() {
        return Name;
    }

    public Category(String name) {
        Name = name;
    }

    public Category(String ID, String name) {
        this.ID = ID;
        Name = name;
    }

    public Category() {
    }

    public void insert(final Context context){
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Adding New Category");
        progressDialog.show();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        Map<String , Object> map = new HashMap<>();
        map.put("Name" , this.Name);
        firebaseFirestore.collection("Category").document().set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(context , "Category Added Successfully" , Toast.LENGTH_SHORT).show();
                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(context , "There was a problem adding Category" , Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void SelectAll(final Context context , final FirebaseFinish finish){
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Getting Categories");
        progressDialog.show();
        final ArrayList<Category> categories = new ArrayList<Category>();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Category").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (DocumentSnapshot documentSnapshot : task.getResult()){
                        progressDialog.dismiss();
                        categories.add(new Category(documentSnapshot.getId() , (String) documentSnapshot.get("Name")));
                    }
                    finish.GetAllCategories(categories);
                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(context , "There was a problem getting Categories Please Try Again" , Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void GetNameByID (Context context , final String ID , final FirebaseFinish finish){
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Loading Product Details");
        progressDialog.show();

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Category").document(ID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    Category category = new Category(ID ,(String) documentSnapshot.get("Name"));
                    finish.GetCategoryByID(category);
                    progressDialog.dismiss();
                }
            }
        });
    }

}
