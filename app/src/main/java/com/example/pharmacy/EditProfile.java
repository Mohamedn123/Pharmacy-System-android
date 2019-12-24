package com.example.pharmacy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pharmacy.Users.FirebaseFinish;
import com.example.pharmacy.Users.Person;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class EditProfile extends AppCompatActivity {
    EditText Name;
    EditText Phone;
    EditText Address;
    TextView Email;
    Button SaveEdits;
    Button DeleteAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Name = findViewById(R.id.Name1);
        Phone = findViewById(R.id.Phone1);
        Address = findViewById(R.id.Address1);
        Email = findViewById(R.id.RegisteredEmail);
        SaveEdits = findViewById(R.id.SaveEdits);
        DeleteAccount = findViewById(R.id.DeleteAccount);
        Person person = new Person();
        person.GetUserWithID(FirebaseAuth.getInstance().getUid(), EditProfile.this, new FirebaseFinish() {
            @Override
            public void onGetAllUsers(ArrayList<Person> personArrayList) {

            }

            @Override
            public void onGetUserWithID(final Person person) {
                Name.setText(person.getName());
                Phone.setText(person.getPhone());
                Address.setText(person.getAddress());
                Email.setText(person.getEmail());

                DeleteAccount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        person.DeleteAccount(EditProfile.this);
                    }
                });
            }

            @Override
            public void GetUserPrivilege(long Privilege) {

            }
        });



    }
}
