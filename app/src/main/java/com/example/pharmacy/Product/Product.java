package com.example.pharmacy.Product;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Product {
    private String ID;
    private String Name;
    private String Image;
    private String Category;
    private int Quantity;
    private double Price;

    public Uri getUriImage() {
        return uriImage;
    }

    public Product(String ID, String name, String category, int quantity, double price, Uri uriImage) {
        this.ID = ID;
        Name = name;
        Category = category;
        Quantity = quantity;
        Price = price;
        this.uriImage = uriImage;
    }

    private Uri uriImage;
    public Product(String name, String image, String category, int quantity, double price) {
        Name = name;
        Image = image;
        Category = category;
        Quantity = quantity;
        Price = price;
    }

    public Product(String ID, String name, String image, String category, int quantity, double price) {
        this.ID = ID;
        Name = name;
        Image = image;
        Category = category;
        Quantity = quantity;
        Price = price;
    }

    public Product() {
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return Name;
    }

    public String getImage() {
        return Image;
    }

    public String getCategory() {
        return Category;
    }

    public int getQuantity() {
        return Quantity;
    }

    public double getPrice() {
        return Price;
    }

    public void insert(final Context context){
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Adding New Product");
        progressDialog.show();
        UploadImage(context, new FirebaseFinish() {
            @Override
            public void GetAllCategories(ArrayList<com.example.pharmacy.Product.Category> Categories) {

            }

            @Override
            public void FinishUploading(String ImagePath) {
                FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                Map<String , Object> map = new HashMap<>();
                map.put("Name" , Name);
                map.put("Price" , Price);
                map.put("Quantity" , Quantity);
                map.put("Image" , ImagePath);
                map.put("Category" , Category);
                firebaseFirestore.collection("Products").document().set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            progressDialog.dismiss();
                            Toast.makeText(context , "Product Added Successfully" , Toast.LENGTH_LONG).show();
                        }
                        else{
                            progressDialog.dismiss();
                            Toast.makeText(context , "There was a problem adding this product" , Toast.LENGTH_LONG).show();
                        }
                    }
                });
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

            }
        });


    }

    public void UploadImage(final Context context , final FirebaseFinish finish){
        File file = new File(Image);
        Uri image = Uri.fromFile(file);
        final String ImagePath = "ProductImages/"+image.getLastPathSegment();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference imageRef = storage.getReference();
        StorageReference ImageReference = imageRef.child(ImagePath);
        UploadTask uploadTask = ImageReference.putFile(image);
        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                finish.FinishUploading(ImagePath);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context , "Upload Problem" , Toast.LENGTH_LONG).show();

            }
        });
    }


    public void GetAllProducts(Context context , final FirebaseFinish finish){
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Getting Product Details");
        progressDialog.show();
        final ArrayList<Product> productArrayList = new ArrayList<Product>();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Products").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                        final String ID = queryDocumentSnapshot.getId();
                        final String ProductName = (String) queryDocumentSnapshot.get("Name");
                        final double price =(double) queryDocumentSnapshot.get("Price");
                        final int quantity = (int) (long) queryDocumentSnapshot.get("Quantity");
                        String ImagePath = (String) queryDocumentSnapshot.get("Image");
                        final String category = (String) queryDocumentSnapshot.get("Category");

                        productArrayList.add(new Product(ID , ProductName , ImagePath , category , quantity , price));

                    }
                    progressDialog.dismiss();
                    Log.d("Inside CLass" , ""+productArrayList.size());
                    finish.GetAllProducts(productArrayList);
                }
            }
        });

    }

    public void GetProductByID(Context context , String ID , final FirebaseFinish finish){
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Getting Product Info");
        progressDialog.show();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Products").document(ID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot = task.getResult();
                String ID = (String) documentSnapshot.getId();
                String ProductName = (String) documentSnapshot.get("Name");
                double Price = (double) documentSnapshot.get("Price");
                final int quantity = (int) (long) documentSnapshot.get("Quantity");
                String ImagePath = (String) documentSnapshot.get("Image");
                final String category = (String) documentSnapshot.get("Category");
                Product product = new Product(ID , ProductName , ImagePath , category , quantity , Price);
                finish.GetProductByID(product);
                progressDialog.dismiss();

            }
        });
    }

    public void DownloadImage(String ImagePath , final FirebaseFinish finish){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference().child(ImagePath);
        long One_MB = 1024 * 1024;
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                finish.FinishDownloadingImage(uri);
            }
        });


    }

    public void DeleteProduct(String ID , final Context context){
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Deleting Product");
        progressDialog.show();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Products").document(ID).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(context , "Product Deleted Successfully" , Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
                else {
                    Toast.makeText(context , "There was a problem Deleting Product" , Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }
        });
    }

    public void DeleteImage(String ImagePath , final Context context){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference().child(ImagePath);
        storageReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){

                }
                else{
                    Toast.makeText(context , "There was a problem Removing all the Product Data" , Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
