package com.example.pharmacy.Users;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.pharmacy.AdminHome;
import com.example.pharmacy.MainActivity;
import com.example.pharmacy.RegularUserHome;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class Person {
    private FirebaseAuth mAuth;
    private String ID;
    private String Name;
    private String Email;
    private String Password;
    private String Phone;
    private String Address;
    private Long Privilege;

    public Person() {
    }


    public String getID() {
        return ID;
    }
    public void setID(String ID) {
        this.ID = ID;
    }
    public Long getPrivilege() {
        return Privilege;
    }
    public void setPrivilege(Long privilege) {
        Privilege = privilege;
    }
    public Person(String name, String email, String password, String phone, String address) {
        Name = name;
        Email = email;
        Password = password;
        Phone = phone;
        Address = address;
    }
    public Person (String name , String email , String password , String phone , String address , long priv){
        Name = name;
        Email = email;
        Password = password;
        Phone = phone;
        Address = address;
        Privilege = priv;
    }
    public Person(String email, String password) {
        Email = email;
        Password = password;
    }
    public String getName() {
        return Name;
    }
    public String getEmail() {
        return Email;
    }
    public String getPassword() {
        return Password;
    }
    public String getPhone() {
        return Phone;
    }
    public String getAddress() {
        return Address;
    }

    public Person(String ID, String name, String email, String phone, String address, Long privilege) {
        this.ID = ID;
        Name = name;
        Email = email;
        Phone = phone;
        Address = address;
        Privilege = privilege;
    }


    public int InsertUser(final Context context) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Registering");
        progressDialog.show();
        final int[] status = {0};
        Log.d("Emai" , this.Email);
        Log.d("Password " , this.Password);
            mAuth = FirebaseAuth.getInstance();

            mAuth.createUserWithEmailAndPassword(this.Email , this.Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        Map<String , Object> user = new HashMap<>();
                        user.put("Name" , Name);
                        user.put("Email" , Email);
                        user.put("Phone" , Phone);
                        user.put("Address" , Address);
                        user.put("Privilege" , Privilege);
                        user.put("ID" , mAuth.getUid());
                        db.collection("Users").document(mAuth.getUid()).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(context , "Your Registration is Successful" , Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                    Intent intent = new Intent(context , MainActivity.class);
                                    context.startActivity(intent);
                                }
                                else{
                                    Toast.makeText(context , "there was a problem with the Registration please try again" , Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            }
                        });

                    }
                    else {
                        Toast.makeText(context , "there was a problem with the Registration please try again" , Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            });
            return status[0];
    }

    public void Login(final Context context){
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Logging in");
        progressDialog.show();
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(this.Email , this.Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser user = mAuth.getCurrentUser();
                    progressDialog.dismiss();
                    Toast.makeText(context , "Logged in Successfully" , Toast.LENGTH_SHORT).show();
                    Redirect(context);

                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(context , "There was a problem please Check your internet connection and the entered credintials" , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void Redirect(final Context context){
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Login");
        progressDialog.show();
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Users").document(mAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    if (task.getResult().get("Privilege") != null){
                        DocumentSnapshot documentSnapshot = task.getResult();
                        Log.d("Document" , documentSnapshot.get("Privilege")+"");
                        long Priv = (long) documentSnapshot.get("Privilege");
                        if (Priv == 0){
                            progressDialog.dismiss();
                            Intent intent = new Intent(context , RegularUserHome.class);
                            context.startActivity(intent);
                        }
                        else{
                            progressDialog.dismiss();
                            Intent intent = new Intent(context , AdminHome.class);
                            context.startActivity(intent);
                        }
                    }
                    else{
                        mAuth.signOut();
                        progressDialog.dismiss();
                        Toast.makeText(context , "Automatic Login Faild" , Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }

    public void GetAllUsers (final Context context , final FirebaseFinish finish){
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Getting Users");
        progressDialog.show();
        final ArrayList<Person> personArrayList = new ArrayList<Person>();

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                        personArrayList.add(new Person((String) queryDocumentSnapshot.get("ID") ,(String) queryDocumentSnapshot.get("Name") ,(String) queryDocumentSnapshot.get("Email") , (String)queryDocumentSnapshot.get("Phone") ,
                                (String)  queryDocumentSnapshot.get("Address") , (Long)queryDocumentSnapshot.get("Privilege")));
                    }
                    progressDialog.dismiss();
                    finish.onGetAllUsers(personArrayList);
                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(context , "There was a problem Retrieving the Users" , Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    public void GetUserWithID(String ID ,Context context, final FirebaseFinish finish){
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Getting User Data");
        progressDialog.show();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Users").document(ID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    Person person = new Person((String) documentSnapshot.get("ID") , (String) documentSnapshot.get("Name"), (String) documentSnapshot.get("Email") ,
                            (String) documentSnapshot.get("Phone") , (String) documentSnapshot.get("Address") , (Long) documentSnapshot.get("Privilege"));
                    finish.onGetUserWithID(person);
                    progressDialog.dismiss();
                }
            }
        });
    }

    public void ChangeUserPrivilege(String ID , long privilege , final Context context){
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Changing User Privilege");
        progressDialog.show();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        Map<String, Object> map = new HashMap<>();
        map.put("Privilege" , privilege);
        firebaseFirestore.collection("Users").document(ID).update(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(context , "User Privilege Changed Successfully" , Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
                else{
                    Toast.makeText(context , "There was a problem changing user Privilege" , Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });
    }

    public void DeleteAccount(final Context context){
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Delete Account Please be Patient");
        progressDialog.show();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Users").document(FirebaseAuth.getInstance().getUid()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                FirebaseAuth.getInstance().signOut();
                                Intent intent = new Intent(context , MainActivity.class);
                                context.startActivity(intent);
                                Toast.makeText(context , "Your Profile Deleted Successfullt" , Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                            else{
                                Log.d("Problem" , "There was a problem");
                                progressDialog.dismiss();
                            }
                        }
                    });
                }
            }
        });
    }

    public void Logout (Context context){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Logging Out");
        progressDialog.show();
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(context , MainActivity.class);
        context.startActivity(intent);
        progressDialog.dismiss();
    }



}
