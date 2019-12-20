package com.example.pharmacy;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProductsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_products, null);
        Bundle bundle = getArguments();
        BottomNavigationView TopNavigation = root.findViewById(R.id.TopNavigation);
        if (bundle != null){
            if (bundle.getString("fragment").equals("Products")){
                Log.d("Message" , "Product");
                TopNavigation.setSelectedItemId(R.id.Product);
                Fragment selected = new ViewProductsFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_Container2 , selected).commit();
            }

        }
        TopNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment Selected = null;
                switch (menuItem.getItemId()){
                    case R.id.Product:
                        Selected = new ViewProductsFragment();
                        break;
                    case R.id.Category:
                        Selected = new ViewCategoryFragment();
                        break;
                }
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_Container2 , Selected).commit();
                return true;
            }
        });
        return root;
    }
}
