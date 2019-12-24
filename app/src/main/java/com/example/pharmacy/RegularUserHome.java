package com.example.pharmacy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class RegularUserHome extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regular_user_home);
        bottomNavigationView = findViewById(R.id.RegularUserBottomNavigation);
        frameLayout = findViewById(R.id.RegualerUserFragment);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.Products:
                        Fragment selected = new ProductsFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.RegualerUserFragment , new ViewProductsFragmentRegularUser()).commit();
                        break;
                    case R.id.Profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.RegualerUserFragment , new ProfileFragment()).commit();
                        break;
                }
                return true;
            }
        });

    }
}
