package com.example.nguyenxuanvietanh.sqlite.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nguyenxuanvietanh.sqlite.Object.Category;
import com.example.nguyenxuanvietanh.sqlite.R;

import java.util.List;

/**
 * Created by Tran Thanh Loc
 */

public class CategoryAdapter extends BaseAdapter {
    Context context;
    List<Category> categoryList;

    public CategoryAdapter(Context context, List<Category> categoryList){
        this.context = context;
        this.categoryList = categoryList;
    }


    @Override
    public int getCount() {
        return categoryList.size();
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
        TextView txtCategoryName;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_category_layout, parent, false);

            viewHolder.txtCategoryName = (TextView) convertView.findViewById(R.id.txtCategoryName);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Category category = categoryList.get(position);
        viewHolder.txtCategoryName.setText(category.getCategoryName());

        return convertView;
    }
}
