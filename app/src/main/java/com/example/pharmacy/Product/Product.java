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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.UnsupportedEncodingException;
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
//    public ArrayList<Product> SelectAll(){
//        String SQL = "SELECT * FROM Product";
//        final String link = "http://10.0.2.2:8080/Pharmacy/AndroidWebservice.php?sql="+SQL;
//        final ArrayList<Product> ProductList = new ArrayList<Product>();
//        Thread thread1 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                OkHttpClient client = new OkHttpClient();
//                Request request = new Request.Builder().url(link).build();
//                try{
//                    Response response = client.newCall(request).execute();
//                    String Data = response.body().string();
//                    JSONArray jsonArray = new JSONArray(Data);
//                    for (int x=0;x<jsonArray.length();x++){
//                        JSONObject jsonObject = (JSONObject) jsonArray.get(x);
//                        byte[] image = jsonObject.getString("Image").getBytes("UTF-8");
//                        Log.d("Image" , String.valueOf(Base64.decode(jsonObject.getString("Image") , Base64.DEFAULT)));
//                        ProductList.add(new Product(Integer.parseInt(jsonObject.getString("ID")) , jsonObject.getString("Name") , jsonObject.getString("Image"), Integer.parseInt(jsonObject.getString("Category_ID")) , Integer.parseInt(jsonObject.getString("Quantity")) , Double.parseDouble(jsonObject.getString("Price"))));
//
//                    }
//
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        thread1.start();
//        try {
//            thread1.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        return ProductList;
//    }
}
