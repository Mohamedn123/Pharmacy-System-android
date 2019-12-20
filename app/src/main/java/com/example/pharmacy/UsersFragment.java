package com.example.pharmacy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmacy.Users.FirebaseFinish;
import com.example.pharmacy.Users.Person;
import com.example.pharmacy.Users.user_adapter;

import java.util.ArrayList;

public class UsersFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_users , container , false);
        final RecyclerView recyclerView = view.findViewById(R.id.UsersRecyclerView);
        Person person = new Person();
        person.GetAllUsers(view.getContext(), new FirebaseFinish() {
            @Override
            public void onGetAllUsers(ArrayList<Person> personArrayList) {
                recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                user_adapter adapter = new user_adapter(personArrayList);
                recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(),DividerItemDecoration.VERTICAL));

                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onGetUserWithID(Person person) {

            }
        });
        return view;
    }
}
