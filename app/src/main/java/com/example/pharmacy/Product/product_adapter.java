package com.example.pharmacy.Product;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pharmacy.R;
import com.example.pharmacy.Users.Person;
import com.example.pharmacy.ViewProductAdmin;
import com.example.pharmacy.ViewProductUser;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class product_adapter extends RecyclerView.Adapter<product_adapter.MyViewHolder>{
    ArrayList<Product> productArrayList;
    Context context;
    public product_adapter(ArrayList<Product> productArrayList , Context context) {
        this.productArrayList = productArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.product_row_item , parent , false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.textView.setText(productArrayList.get(position).getName());
        Product product = new Product();
        product.DownloadImage(this.productArrayList.get(position).getImage(), new FirebaseFinish() {
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
                Glide.with(context).load(image.toString()).into(holder.imageView);

            }

            @Override
            public void GetProductByID(Product product) {

            }

            @Override
            public void GetCategoryByID(Category category) {

            }
        });
    }


    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.ProductName);
            imageView = itemView.findViewById(R.id.ProductImage);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int Position = getAdapterPosition();
                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                    Person person = new Person();
                    person.GetUserPrivilege(firebaseAuth.getUid(), new com.example.pharmacy.Users.FirebaseFinish() {
                        @Override
                        public void onGetAllUsers(ArrayList<Person> personArrayList) {

                        }

                        @Override
                        public void onGetUserWithID(Person person) {

                        }

                        @Override
                        public void GetUserPrivilege(long Privilege) {
                            if (Privilege == 0){
                                Log.d("User Privilege" , Privilege+"");
                                Intent intent = new Intent(context , ViewProductUser.class);
                                intent.putExtra("ProductID" , productArrayList.get(Position).getID());
                                context.startActivity(intent);
                            }
                            else if (Privilege == 1){
                                Intent intent = new Intent(context , ViewProductAdmin.class);
                                intent.putExtra("ProductID" , productArrayList.get(Position).getID());
                                context.startActivity(intent);
                            }
                        }
                    });

                }
            });

        }
    }
}
