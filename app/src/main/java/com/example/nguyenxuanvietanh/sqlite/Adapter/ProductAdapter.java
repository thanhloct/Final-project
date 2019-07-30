package com.example.nguyenxuanvietanh.sqlite.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nguyenxuanvietanh.sqlite.Object.Product;
import com.example.nguyenxuanvietanh.sqlite.R;

import java.util.List;

/**
 * Created by Tran Thanh Loc
 */

public class ProductAdapter extends BaseAdapter {
    Context context;
    List<Product> productList;

    public ProductAdapter(Context context, List<Product> listProduct){
        this.context = context;
        this.productList = listProduct;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        TextView txtProductName;
        TextView txtProductPrice;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_product_layout, parent, false);

            viewHolder.txtProductName = (TextView) convertView.findViewById(R.id.txtProductName);
            viewHolder.txtProductPrice = (TextView) convertView.findViewById(R.id.txtProductPrice);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Product product = productList.get(position);
        viewHolder.txtProductName.setText(product.getProductName());
        viewHolder.txtProductPrice.setText(String.valueOf(product.getProductPrice()));

        return convertView;
    }



}
