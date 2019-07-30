package com.example.nguyenxuanvietanh.sqlite;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguyenxuanvietanh.sqlite.Adapter.CategoryAdapter;
import com.example.nguyenxuanvietanh.sqlite.Adapter.ProductAdapter;
import com.example.nguyenxuanvietanh.sqlite.Database.Database;
import com.example.nguyenxuanvietanh.sqlite.Object.Category;
import com.example.nguyenxuanvietanh.sqlite.Object.Product;
import com.example.nguyenxuanvietanh.sqlite.XLDL.XLCategory;
import com.example.nguyenxuanvietanh.sqlite.XLDL.XLProduct;

import java.util.List;
/**
 * Created by Tran Thanh Loc
 */
public class ProductActivity extends AppCompatActivity {
    ListView lsvProduct;
    Database database;
    List<Category> categoryList;
    List<Product> productList;

    XLCategory xlCategory;
    XLProduct xlProduct;
    ProductAdapter productAdapter;

    int categoryID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        lsvProduct = (ListView) findViewById(R.id.lsvProduct);

        database = new Database(this);
        xlCategory = new XLCategory(database);
        xlProduct = new XLProduct(database);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            categoryID = bundle.getInt("CategoryID");
            productList = xlProduct.getlistProduct(categoryID);

            productAdapter = new ProductAdapter(this, productList);
            lsvProduct.setAdapter(productAdapter);
            productAdapter.notifyDataSetChanged();
        }
        registerForContextMenu(lsvProduct);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final int position = info.position;

        switch (item.getItemId()){
            case R.id.mnUpdate:
                final Dialog dialogUpdateProduct = new Dialog(this);
                dialogUpdateProduct.setTitle("Update Product");
                dialogUpdateProduct.setContentView(R.layout.addproduct_layout);

                categoryList = xlCategory.getCategoryList();
                CategoryAdapter categoryAdapter = new CategoryAdapter(getApplicationContext(), categoryList);

                final Spinner spCategory = (Spinner) dialogUpdateProduct.findViewById(R.id.spCategory);
                final EditText edtProductName = (EditText) dialogUpdateProduct.findViewById(R.id.edtProductName);
                final EditText edtProductPrice = (EditText) dialogUpdateProduct.findViewById(R.id.edtProductPrice);
                Button btnUpdate = (Button) dialogUpdateProduct.findViewById(R.id.btnAddProduct);

                spCategory.setAdapter(categoryAdapter);
                categoryAdapter.notifyDataSetChanged();

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int productID = productList.get(position).getProductID();
                        int categoryID = categoryList.get(spCategory.getSelectedItemPosition()).getCategoryID();

                        String productName = edtProductName.getText().toString();
                        int productPrice = Integer.parseInt(edtProductPrice.getText().toString());

                        Product product = new Product(productID, productName, productPrice, categoryID);
                        long checkout = xlProduct.UpdateProduct(product, productID);
                        if(checkout != 0){
                            productList = xlProduct.getlistProduct(categoryID);

                            productAdapter = new ProductAdapter(getApplicationContext(), productList);
                            lsvProduct.setAdapter(productAdapter);
                            productAdapter.notifyDataSetChanged();
                            Toast.makeText(getApplicationContext(),"Update thành công",Toast.LENGTH_SHORT).show();
                            dialogUpdateProduct.dismiss();
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Update thất bại",Toast.LENGTH_SHORT).show();
                            dialogUpdateProduct.dismiss();
                        }
                    }
                });
                dialogUpdateProduct.show();
                break;

            case R.id.mnDelete:

                break;
        }
        return super.onContextItemSelected(item);
    }
}
