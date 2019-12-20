package com.example.pharmacy.Users;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmacy.R;
import com.example.pharmacy.ViewUserInformation;

import java.util.ArrayList;

public class user_adapter extends RecyclerView.Adapter<user_adapter.MyViewHolder>{
    ArrayList<Person> personArrayList = new ArrayList<Person>();

    public user_adapter(ArrayList<Person> personArrayList) {
        this.personArrayList = personArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.users_row_item , parent , false);
        user_adapter.MyViewHolder myViewHolder = new user_adapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.Username.setText(this.personArrayList.get(position).getName());
        holder.Email.setText(this.personArrayList.get(position).getEmail());
        holder.Address.setText(this.personArrayList.get(position).getAddress());
        if (this.personArrayList.get(position).getPrivilege() == 0){
            holder.Privilege.setText("Regular User");
        }
        else if (this.personArrayList.get(position).getPrivilege() == 1){
            holder.Privilege.setText("Admin User");
        }
    }

    @Override
    public int getItemCount() {
        return this.personArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Username;
        TextView Email;
        TextView Privilege;
        TextView Address;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            Username = itemView.findViewById(R.id.UserName);
            Email = itemView.findViewById(R.id.UserEmail);
            Privilege = itemView.findViewById(R.id.UserPrivilege);
            Address = itemView.findViewById(R.id.UserAddress);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int Pos = getAdapterPosition();
                    Intent intent = new Intent(itemView.getContext() , ViewUserInformation.class);
                    intent.putExtra("UserID" , personArrayList.get(Pos).getID());
                    itemView.getContext().startActivity(intent);
                }
            });
        }


    }
}
