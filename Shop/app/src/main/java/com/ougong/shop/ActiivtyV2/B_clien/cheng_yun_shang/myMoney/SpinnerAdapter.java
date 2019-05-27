package com.ougong.shop.ActiivtyV2.B_clien.cheng_yun_shang.myMoney;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SpinnerAdapter extends ArrayAdapter<String> {
    Context context;
    String[] items = new String[] {};
    int textViewResourceId;


    public SpinnerAdapter(final Context context,
                          final int textViewResourceId, final String[] objects) {
        super(context, textViewResourceId, objects);
        this.items = objects;
        this.textViewResourceId = textViewResourceId;
        this.context = context;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(
                    textViewResourceId, parent, false);
        }

        TextView tv = (TextView) convertView;
        tv.setText(items[position]);
        tv.setGravity(Gravity.CENTER);
        return convertView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(
                    textViewResourceId, parent, false);
        }

        TextView tv = (TextView) convertView;
        tv.setText(items[position]);
        tv.setGravity(Gravity.CENTER);
        return convertView;
    }
}