package com.example.pharmacy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminHome extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        bottomNavigationView = findViewById(R.id.BottomNavigation);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            if (bundle.getString("fragment").equals("Product")){
                Bundle extras = new Bundle();
                extras.putString("fragment" , "Products");
                Fragment fragment = new ProductsFragment();
                fragment.setArguments(extras);
                bottomNavigationView.setSelectedItemId(R.id.Product);
                getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_Container , fragment).commit();
            }
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment Selected = null;
                 switch (menuItem.getItemId()){
                     case R.id.Product:
                         Selected = new ProductsFragment();
                         break;

                     case R.id.Home:
                         Selected = new InsightsFragment();
                         break;

                     case R.id.Orders:
                         Selected = new OrdersFragment();
                         break;

                     case R.id.Users:
                         Selected = new UsersFragment();
                         break;

                     case R.id.Profile:
                         Selected = new ProfileFragment();
                         break;
                 }
                 getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_Container , Selected).commit();
                 return true;
            }
        });

    }
}
