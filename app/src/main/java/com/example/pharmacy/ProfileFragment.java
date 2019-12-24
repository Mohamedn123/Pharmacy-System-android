package com.example.pharmacy;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pharmacy.Users.FirebaseFinish;
import com.example.pharmacy.Users.Person;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {
    private TextView Name;
    private TextView Address;
    private TextView Phone;
    private TextView Email;
    private TextView Privilege;
    private Button Logout;
    FloatingActionButton Edit;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_profile , container , false);

        Name = view.findViewById(R.id.userName1);
        Address = view.findViewById(R.id.userAddress1);
        Phone = view.findViewById(R.id.userPhone1);
        Email = view.findViewById(R.id.userEmail1);
        Privilege = view.findViewById(R.id.userPrivilege1);
        Edit = view.findViewById(R.id.EditProfile);
        Logout = view.findViewById(R.id.Logout);
        String ID = FirebaseAuth.getInstance().getUid();
        Person person = new Person();
        person.GetUserWithID(ID, view.getContext(), new FirebaseFinish() {
            @Override
            public void onGetAllUsers(ArrayList<Person> personArrayList) {

            }

            @Override
            public void onGetUserWithID(final Person person) {
                Name.setText(person.getName());
                Address.setText(person.getAddress());
                Phone.setText(person.getPhone());
                Email.setText(person.getEmail());
                if (person.getPrivilege() == 0) {
                    Privilege.setText("Regular User");
                } else if (person.getPrivilege() == 1) {
                    Privilege.setText("Admin User");
                }


                Edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(view.getContext() , EditProfile.class);
                        startActivity(intent);
                    }
                });

                Logout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        person.Logout(view.getContext());
                    }
                });

            }

            @Override
            public void GetUserPrivilege(long Privilege) {

            }
        });
        return view;
    }
}
