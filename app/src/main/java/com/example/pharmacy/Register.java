package com.example.pharmacy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pharmacy.Users.Person;

import java.util.ArrayList;

public class Register extends AppCompatActivity {
    Button Register;
    Button Login;
    EditText Name;
    EditText Email;
    EditText Password;
    EditText ConfirmPassword;
    EditText Phone;
    EditText Address;
    boolean EmailFaild = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Register = findViewById(R.id.Register);
        Login = findViewById(R.id.Login);
        Name = findViewById(R.id.Name);
        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.Password);
        ConfirmPassword = findViewById(R.id.ConfirmPassword);
        Phone = findViewById(R.id.Phone);
        Address = findViewById(R.id.Address);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(Name.getText().toString())){
                    Name.setError("Name is Required");
                    return;
                }
                if (TextUtils.isEmpty(Email.getText().toString())){
                    Email.setError("Email is Required");
                    return;
                }
                if(TextUtils.isEmpty(Password.getText().toString())){
                    Password.setError("Password is Required");
                    return;
                }
                if (TextUtils.isEmpty(ConfirmPassword.getText().toString())){
                    ConfirmPassword.setError("Password Confirmation is Required");
                    return;
                }
                if (TextUtils.isEmpty(Phone.getText().toString())){
                    Phone.setError("Phone is Required");
                    return;
                }
                if (TextUtils.isEmpty(Address.getText().toString())){
                    Address.setError("Address is Required");
                    return;
                }
                if (!Password.getText().toString().equals(ConfirmPassword.getText().toString())){
                    Password.setError("Password and Confirm password mismatch");
                    ConfirmPassword.setError("Password and Confirm password mismatch");
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(Email.getText().toString()).matches()){
                    Email.setError("Please enter valid Email");
                    return;
                }
                if (EmailFaild){
                    Email.setError("This Email is Taken Please Login");
                    return;
                }
                long priv = 0;
                Person User = new Person(Name.getText().toString() , Email.getText().toString() , Password.getText().toString() , Phone.getText().toString() , Address.getText().toString() ,priv);
                int result =  User.InsertUser(Register.this);

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
