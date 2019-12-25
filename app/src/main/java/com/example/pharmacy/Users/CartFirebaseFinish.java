package com.example.pharmacy.Users;

import java.util.ArrayList;

public interface CartFirebaseFinish {
    void GetCartID(String ID);
    void AddNewCart(String CartID);
    void GetTotalPrice(double Price);
    void GetAllCartItems(ArrayList<CartItems> CartItems);
}
