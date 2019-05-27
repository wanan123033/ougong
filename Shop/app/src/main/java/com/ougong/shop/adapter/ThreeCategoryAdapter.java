package com.ougong.shop.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.ougong.shop.Bean.CateryBean;
import com.ougong.shop.R;
import com.ougong.shop.utils.MessageBus;
import org.jetbrains.annotations.NotNull;

public class ThreeCategoryAdapter extends BaseAdapter{
    private Context context;
    private CateryBean[] child;

    public ThreeCategoryAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        if (child == null){
            return 0;
        }
        return child.length;
    }

    @Override
    public CateryBean getItem(int position) {
        return child[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_style,parent,false);
        }
        ((TextView)convertView).setText(child[position].getName());
        if (child[position].getEnabless()){
            convertView.setBackgroundResource(R.drawable.shape_splh_line);
        }else {
            convertView.setBackgroundResource(R.drawable.shape_dash_line);
        }

        return convertView;
    }

    public void setData(@NotNull CateryBean[] child) {
        this.child = child;
    }

    public CateryBean[] getCates(){
        return this.child;
    }

}
