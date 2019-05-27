package com.ougong.shop.activity.checkin;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.ougong.shop.Bean.CateryBean;
import com.ougong.shop.R;
import com.ougong.shop.activity.CheckLinData;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FenleiAdapter extends BaseAdapter {
    private Context context;
    private List<CheckLinData.CategoryBean> data;

    public FenleiAdapter(Context context){
        this.context = context;
    }
    @Override
    public int getCount() {
        if (data != null){
            return data.size() + 1;
        }
        return 0;
    }

    @Override
    public CheckLinData.CategoryBean getItem(int position) {
        if (data != null){
            return data.get(position);
        }
        return null;
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
        if (position == data.size()){
            ((TextView) convertView).setText("添加家具类别");
        }else {
            ((TextView) convertView).setText(getItem(position).categoryName);
        }
        if (position != data.size())
            if (getItem(position).enable){
                ((TextView) convertView).setTextColor(Color.BLACK);
                convertView.setBackgroundResource(R.drawable.img_boder);
            }else {
                ((TextView) convertView).setTextColor(Color.parseColor("#999999"));
                convertView.setBackgroundColor(Color.WHITE);
            }
        return convertView;
    }

    public void setData(@NotNull List<CheckLinData.CategoryBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public List<CheckLinData.CategoryBean> getData(){
        return data;
    }
}
