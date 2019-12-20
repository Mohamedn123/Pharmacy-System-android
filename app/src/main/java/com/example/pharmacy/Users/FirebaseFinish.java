package com.example.pharmacy.Users;

import java.util.ArrayList;

public interface FirebaseFinish {
    void onGetAllUsers(ArrayList<Person> personArrayList);
    void onGetUserWithID(Person person);

}
