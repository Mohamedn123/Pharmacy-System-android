package com.example.pharmacy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pharmacy.Product.Product;
import com.example.pharmacy.Users.FirebaseFinish;
import com.example.pharmacy.Users.Person;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class ViewUserInformation extends AppCompatActivity {
    private TextView Name;
    private TextView Address;
    private TextView Phone;
    private TextView Email;
    private TextView Privilege;
    private Button Call;
    private Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_information);
        Name = findViewById(R.id.userName);
        Address = findViewById(R.id.userAddress);
        Phone = findViewById(R.id.userPhone);
        Email = findViewById(R.id.userEmail);
        Privilege = findViewById(R.id.userPrivilege);
        Call = findViewById(R.id.Call);
        spinner = findViewById(R.id.UserTypes);
        ArrayList<String> UserTypes = new ArrayList<String>();
        final Intent intent = getIntent();
        final String ID = intent.getStringExtra("UserID");
        UserTypes.add("Regular User");
        UserTypes.add("Admin User");
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this ,android.R.layout.simple_spinner_item , UserTypes);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adp);


        final Person person = new Person();
        person.GetUserWithID(ID, ViewUserInformation.this, new FirebaseFinish() {
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

                Call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                            ActivityCompat.requestPermissions(ViewUserInformation.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                        }

                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + person.getPhone()));

                        startActivity(intent);
                    }
                });
                int privilege = person.getPrivilege().intValue();
                spinner.setSelection(privilege);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                       if (position != person.getPrivilege()){
                           Person person = new Person();
                           person.ChangeUserPrivilege(ID , position , ViewUserInformation.this);
                           Intent intent1 = new Intent(getApplicationContext() , ViewUserInformation.class);
                           intent1.putExtra("UserID" , ID);
                           startActivity(intent1);
                       }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }

            @Override
            public void GetUserPrivilege(long Privilege) {

            }
        });



    }
}
