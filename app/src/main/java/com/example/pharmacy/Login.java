package com.example.pharmacy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.pharmacy.Users.Person;
import com.google.android.material.snackbar.Snackbar;

public class Login extends AppCompatActivity {
    EditText Email;
    EditText Password;
    Button Login;
    RelativeLayout coordinatorLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.Password);
        Login = findViewById(R.id.Login);
        coordinatorLayout = findViewById(R.id.RootLayout);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(Email.getText().toString())){
                    Email.setError("Email is Required for Login");
                    return;
                }
                if (TextUtils.isEmpty(Password.getText().toString())){
                    Password.setError("Password is Required for login");
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(Email.getText().toString()).matches()){
                    Email.setError("Please enter Valid Email Address");
                    return;
                }

                Person person = new Person(Email.getText().toString() , Password.getText().toString());
                person.Login(com.example.pharmacy.Login.this);



            }
        });
    }
}
