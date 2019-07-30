package com.example.nguyenxuanvietanh.sqlite.Object;

/**
 * Created by Tran Thanh Loc
 */
public class Product {
    private int ProductID;
    private String ProductName;
    private int ProductPrice;
    private int CategoryID;

    public Product(int productID, String productName, int productPrice, int categoryID) {
        ProductID = productID;
        ProductName = productName;
        ProductPrice = productPrice;
        CategoryID = categoryID;

    }

    public Product() {
    }

    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int productID) {
        ProductID = productID;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public int getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(int productPrice) {
        ProductPrice = productPrice;
    }

    public int getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(int categoryID) {
        CategoryID = categoryID;
    }


}
