package com.example.nguyenxuanvietanh.sqlite.Object;

/**
 * Created by Tran Thanh Loc
 */

public class Category {
    private int CategoryID;
    private String CategoryName;

    public Category(int categoryID, String categoryName) {
        CategoryID = categoryID;
        this.CategoryName = categoryName;
    }

    public Category() {
    }

    public int getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(int categoryID) {
        CategoryID = categoryID;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }
}
