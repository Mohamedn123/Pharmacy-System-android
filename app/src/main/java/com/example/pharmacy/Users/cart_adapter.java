package com.example.pharmacy.Users;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pharmacy.Product.Category;
import com.example.pharmacy.Product.FirebaseFinish;
import com.example.pharmacy.Product.Product;
import com.example.pharmacy.R;

import java.util.ArrayList;

public class cart_adapter extends RecyclerView.Adapter<cart_adapter.ViewHolder> {
    ArrayList<CartItems> CartItems = new ArrayList<CartItems>();
    Context context;
    public cart_adapter(ArrayList<CartItems> CartItems , Context context) {
        this.CartItems = CartItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.cart_row_item , parent , false);
        cart_adapter.ViewHolder viewHolder = new cart_adapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        Product product = new Product();
        product.GetProductByID(context, this.CartItems.get(position).getProductID(), new FirebaseFinish() {
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
            public void GetProductByID(Product product) {
                holder.ProductName.setText(product.getName());
                product.DownloadImage(product.getImage(), new FirebaseFinish() {
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
                        Glide.with(context).load(image).into(holder.product_image);
                    }

                    @Override
                    public void GetProductByID(Product product) {

                    }

                    @Override
                    public void GetCategoryByID(Category category) {

                    }
                });
                holder.ProductQuantity.setText("Quantity: "+CartItems.get(position).getQuantity());
                holder.Delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Person person = new Person();
                        person.DeleteItemFromCart(CartItems.get(position).getCartID() , CartItems.get(position).getID() , context);
                    }
                });
            }

            @Override
            public void GetCategoryByID(Category category) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return this.CartItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView product_image;
        TextView ProductName;
        TextView ProductQuantity;
        Button Delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            product_image = itemView.findViewById(R.id.cart_Product_Image);
            ProductName = itemView.findViewById(R.id.cart_Product_Name);
            ProductQuantity = itemView.findViewById(R.id.cart_product_Quantity);
            Delete = itemView.findViewById(R.id.cart_product_delete);
        }
    }
}
