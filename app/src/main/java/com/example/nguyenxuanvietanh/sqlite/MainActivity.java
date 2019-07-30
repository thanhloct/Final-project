package com.example.nguyenxuanvietanh.sqlite;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nguyenxuanvietanh.sqlite.Adapter.CategoryAdapter;
import com.example.nguyenxuanvietanh.sqlite.Database.Database;
import com.example.nguyenxuanvietanh.sqlite.Object.Category;
import com.example.nguyenxuanvietanh.sqlite.Object.Product;
import com.example.nguyenxuanvietanh.sqlite.XLDL.XLCategory;
import com.example.nguyenxuanvietanh.sqlite.XLDL.XLProduct;

import java.util.List;

/**
 * Created by Tran Thanh Loc
 */

public class MainActivity extends AppCompatActivity {
    ListView lsvCategory;
    Database database;
    List<Category> categoryList;

    XLCategory xlCategory;
    XLProduct xlProduct;
    CategoryAdapter categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = new Database(this);
        lsvCategory = (ListView) findViewById(R.id.lsvCategory);

        Database database = new Database(getApplicationContext());
        xlCategory = new XLCategory(database);
        xlProduct = new XLProduct(database);
        categoryList = xlCategory.getCategoryList();

        categoryAdapter = new CategoryAdapter(this, categoryList);
        lsvCategory.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();

        lsvCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ProductActivity.class);
                intent.putExtra("CategoryID", categoryList.get(position).getCategoryID());
                startActivity(intent);
            }
        });

        registerForContextMenu(lsvCategory);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnAddCategory:
                Dialog dialogAddCategory = new Dialog(MainActivity.this);
                dialogAddCategory.setTitle("Thêm Loại Sản Phẩm");
                dialogAddCategory.setContentView(R.layout.addcategory_layout);


                final EditText edtCategoryName = (EditText) dialogAddCategory.findViewById(R.id.edtCategoryName);
                Button btnAddCategory = (Button) dialogAddCategory.findViewById(R.id.btnAddCategory);

                btnAddCategory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(edtCategoryName.toString().trim().isEmpty()){
                            Toast.makeText(MainActivity.this, "Không được bỏ trống", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            String categoryName = edtCategoryName.getText().toString();

                            Category category = new Category();
                            category.setCategoryName(categoryName);

                            if(xlCategory.AddCategory(category)){
                                categoryList = xlCategory.getCategoryList();

                                CategoryAdapter categoryAdapter = new CategoryAdapter(MainActivity.this, categoryList);
                                lsvCategory.setAdapter(categoryAdapter);
                                categoryAdapter.notifyDataSetChanged();

                                Toast.makeText(getApplicationContext(),"Thành Công",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getApplicationContext(),"Thất Bại",Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });
                dialogAddCategory.show();
                break;
            case R.id.mnAddProduct:
                Dialog dialogAddProduct = new Dialog(MainActivity.this);
                dialogAddProduct.setTitle("Thêm Sản Phẩm");
                dialogAddProduct.setContentView(R.layout.addproduct_layout);

                categoryList = xlCategory.getCategoryList();
                CategoryAdapter categoryAdapter = new CategoryAdapter(getApplicationContext(), categoryList);


                final Spinner spCategory = (Spinner) dialogAddProduct.findViewById(R.id.spCategory);
                final EditText edtProductName = (EditText) dialogAddProduct.findViewById(R.id.edtProductName);
                final EditText edtProductPrice = (EditText) dialogAddProduct.findViewById(R.id.edtProductPrice);
                Button btnAddProduct = (Button) dialogAddProduct.findViewById(R.id.btnAddProduct);

                spCategory.setAdapter(categoryAdapter);
                categoryAdapter.notifyDataSetChanged();

                btnAddProduct.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(edtProductName.toString().trim().isEmpty() || edtProductPrice.toString().trim().isEmpty()){
                            Toast.makeText(MainActivity.this, "Không được bỏ trống", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            int category = categoryList.get(spCategory.getSelectedItemPosition()).getCategoryID();
                            String productName = edtProductName.getText().toString();
                            int productPrice = Integer.parseInt(edtProductPrice.getText().toString());

                            Product product = new Product();
                            product.setCategoryID(category);
                            product.setProductName(productName);
                            product.setProductPrice(productPrice);

                            if (xlProduct.AddProduct(product)){

                                Toast.makeText(getApplicationContext(),"Thành công",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getApplicationContext(),"Thất bại",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                dialogAddProduct.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.context_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final int position = info.position;

        switch (id){
            case R.id.mnUpdate:
                final Dialog dialogUpdateCategory = new Dialog(MainActivity.this);
                dialogUpdateCategory.setContentView(R.layout.addcategory_layout);
                dialogUpdateCategory.setTitle("Sửa ");

                final EditText edtCategoryName = (EditText) dialogUpdateCategory.findViewById(R.id.edtCategoryName);
                Button btnUpdateCategory = (Button) dialogUpdateCategory.findViewById(R.id.btnAddCategory);
                btnUpdateCategory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int categoryID = categoryList.get(position).getCategoryID();
                        String categoryName = edtCategoryName.getText().toString();

                        Category category = new Category(categoryID, categoryName);
                        categoryList = xlCategory.getCategoryList();

                        long checkout = xlCategory.UpdateCategory(categoryID, category);
                        if(checkout != 0){
                            categoryList = xlCategory.getCategoryList();

                            categoryAdapter = new CategoryAdapter(MainActivity.this,categoryList);
                            lsvCategory.setAdapter(categoryAdapter);
                            categoryAdapter.notifyDataSetChanged();
                            Toast.makeText(getApplicationContext(),"Update thành công",Toast.LENGTH_SHORT).show();
                            dialogUpdateCategory.dismiss();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Update thất bại",Toast.LENGTH_SHORT).show();
                            dialogUpdateCategory.dismiss();
                        }
                    }
                });

                dialogUpdateCategory.show();
                break;

            case R.id.mnDelete:
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setTitle("Delete");
                alertDialog.setMessage("Bạn có chắc muốn xóa hay không ?");
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int categoryID = categoryList.get(position).getCategoryID();
                        long checkout = xlCategory.DeleteCategory(categoryID);
                        if(checkout != 0){
                            categoryList = xlCategory.getCategoryList();

                            categoryAdapter = new CategoryAdapter(MainActivity.this, categoryList);
                            lsvCategory.setAdapter(categoryAdapter);
                            categoryAdapter.notifyDataSetChanged();
                            Toast.makeText(getApplicationContext(),"Xóa thành công",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Xóa thất bại",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                alertDialog.show();
                break;
        }

        return super.onContextItemSelected(item);
    }
}
