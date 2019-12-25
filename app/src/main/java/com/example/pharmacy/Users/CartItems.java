package com.example.pharmacy.Users;

public class CartItems{
    String CartID;
    String ID;
    String ProductID;
    long Quantity;
    double TotalPrice;

    public CartItems(String CartID , String ID, String productID, long quantity, double totalPrice) {
        this.CartID = CartID;
        this.ID = ID;
        ProductID = productID;
        Quantity = quantity;
        TotalPrice = totalPrice;
    }

    public String getID() {
        return ID;
    }

    public String getProductID() {
        return ProductID;
    }

    public long getQuantity() {
        return Quantity;
    }

    public double getTotalPrice() {
        return TotalPrice;
    }

    public String getCartID() {
        return CartID;
    }
}
