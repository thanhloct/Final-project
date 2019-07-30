package com.example.nguyenxuanvietanh.sqlite.XLDL;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.nguyenxuanvietanh.sqlite.Database.Database;
import com.example.nguyenxuanvietanh.sqlite.Object.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tran Thanh Loc
 */

public class XLProduct {
    SQLiteDatabase productdb;

    public XLProduct(Database db){
        productdb = db.OpenConnection();
    }

    public List<Product> getlistProduct(int categoryid){
        List<Product> productList = new ArrayList<>();

        String str = "Select * from " + Database.Table_Product + " where " + Database.CategoryID + " = '" + categoryid + "'" ;

        Cursor cursor = productdb.rawQuery(str, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            int productID = cursor.getInt(cursor.getColumnIndex(Database.ProductID));
            String productName = cursor.getString(cursor.getColumnIndex(Database.ProductName));
            int productPrice = cursor.getInt(cursor.getColumnIndex(Database.ProductPrice));
            int categoryID = cursor.getInt(cursor.getColumnIndex(Database.CategoryID));

            Product product = new Product(productID, productName, productPrice, categoryID);
            productList.add(product);

            cursor.moveToNext();
        }

        return productList;
    }

    public boolean AddProduct(Product product){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Database.ProductName, product.getProductName());
        contentValues.put(Database.ProductPrice, product.getProductPrice());
        contentValues.put(Database.CategoryID, product.getCategoryID());


        long result = productdb.insert(Database.Table_Product, null, contentValues);

        if(result != 0){
            return true;
        }
        else
            return false;
    }

    public long UpdateProduct(Product product, int id){
        ContentValues contentValues = new ContentValues();

        contentValues.put(Database.ProductName, product.getProductName());
        contentValues.put(Database.ProductPrice, product.getProductPrice());
        contentValues.put(Database.CategoryID, product.getCategoryID());

        String clause = Database.ProductID + " =? ";
        String[] arr = new String[]{String.valueOf(id)};

        return productdb.update(Database.Table_Product, contentValues, clause, arr);
    }

    public long DeleteProduct(int id){
        String clause = Database.ProductID + " =? ";
        String[] arr = new String[]{String.valueOf(id)};

        return productdb.delete(Database.Table_Product, clause, arr);
    }
}
