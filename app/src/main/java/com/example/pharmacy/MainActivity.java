package com.example.pharmacy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.pharmacy.Users.FirebaseFinish;
import com.example.pharmacy.Users.Person;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button Register;
    Button Login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Person person1 = new Person();
        person1.GetAllUsers(MainActivity.this, new FirebaseFinish() {
            @Override
            public void onGetAllUsers(ArrayList<Person> personArrayList) {
                Log.d("PersonsSize" , ""+personArrayList.size());

            }

            @Override
            public void onGetUserWithID(Person person) {

            }

            @Override
            public void GetUserPrivilege(long Privilege) {

            }
        });

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null){
            Person person = new Person();
            person.Redirect(MainActivity.this);
        }
        Register = findViewById(R.id.Register);
        Login = findViewById(R.id.Login);
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext() , Register.class);
                startActivity(intent);
            }
        });
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext() , Login.class);
                startActivity(intent);
            }
        });
    }
}
