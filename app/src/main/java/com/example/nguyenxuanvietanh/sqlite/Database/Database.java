package com.example.nguyenxuanvietanh.sqlite.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Tran Thanh Loc
 */

public class Database extends SQLiteOpenHelper{
    private static final String Database_Name = "Database";

    public static String Table_Category = "Category";
    public static String CategoryID = "CategoryID";
    public static String CategoryName = "CategoryName";

    public static String Table_Product = "Product";
    public static String ProductID = "ProductID";
    public static String ProductName = "ProductName";
    public static String ProductPrice = "ProductPrice";


    public Database(Context context) {
        super(context, Database_Name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CreateCategoryTable = " Create Table if not exists " + Table_Category + " ( " + CategoryID + " Integer Primary key Autoincrement , " + CategoryName + " varchar " + " ) ";
        String CreateProductTable = " Create Table if not exists " + Table_Product + " ( " + ProductID + " Integer Primary key Autoincrement ," + ProductName + " varchar , " + ProductPrice + " Integer ," + CategoryID + " Integer " +  " ) ";

        db.execSQL(CreateCategoryTable);
        db.execSQL(CreateProductTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public SQLiteDatabase OpenConnection(){
        return this.getWritableDatabase();
    }
}
