package com.example.pharmacy.Product;

import android.net.Uri;

import java.util.ArrayList;

public interface FirebaseFinish {
    void GetAllCategories(ArrayList<Category> Categories);
    void FinishUploading(String ImagePath);
    void GetAllProducts(ArrayList<Product> productArrayList);
    void FinishDownloadingImage (Uri image);
    void GetProductByID(Product product);
    void GetCategoryByID(Category category);
}
