package com.example.nguyenxuanvietanh.sqlite.XLDL;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.nguyenxuanvietanh.sqlite.Database.Database;
import com.example.nguyenxuanvietanh.sqlite.Object.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tran Thanh loc
 */

public class XLCategory {
    SQLiteDatabase categorydb;

    public XLCategory(Database db){
        categorydb = db.OpenConnection();
    }

    public List<Category> getCategoryList(){

        /*
        * Lay danh sach catrgory
        * input :
        * output : lay danh sach câtgory
        *
        * Thuat giai :
        *
        * tao lits chua catagory
        * Thuc hien cau truy van sql select
        *
        * dung lenh cursor = db.rawQuery()
            duyet cursor lay du lieu
            co du lieu vao list
           *
        *
        */

        List<Category> categoryList = new ArrayList<>();

        String str = "Select * from " + Database.Table_Category;

        Cursor cursor = categorydb.rawQuery(str, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            int categoryID = cursor.getInt(cursor.getColumnIndex(Database.CategoryID));
            String categoryName = cursor.getString(cursor.getColumnIndex(Database.CategoryName));

            Category category = new Category(categoryID,categoryName);
            categoryList.add(category);

            cursor.moveToNext();
        }
        return categoryList;
    }

    public boolean AddCategory(Category category){
        ContentValues contentValues = new ContentValues();

        contentValues.put(Database.CategoryName, category.getCategoryName());

        long result = categorydb.insert(Database.Table_Category, null, contentValues);
        if(result != 0){
            return  true;
        }
        else {
            return false;
        }
    }

    public long UpdateCategory(int id, Category category){
        ContentValues contentValues = new ContentValues();

        contentValues.put(Database.CategoryName, category.getCategoryName());

        String clause = Database.CategoryID + " =? ";
        String[] arr = new String[]{String.valueOf(id)};

        return categorydb.update(Database.Table_Category, contentValues, clause, arr);
    }

    public long DeleteCategory(int id){
        String clause = Database.CategoryID + " =? ";
        String[] arr = new String[]{String.valueOf(id)};

        return categorydb.delete(Database.Table_Category, clause, arr);
    }
}
