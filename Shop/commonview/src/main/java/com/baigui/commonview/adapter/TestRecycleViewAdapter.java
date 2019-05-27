package com.baigui.commonview.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.GetChars;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.baigui.commonview.R;

import java.util.ArrayList;
import java.util.List;

public class TestRecycleViewAdapter extends RecyclerView.Adapter {
    //② 创建ViewHolder
    public static class VH extends RecyclerView.ViewHolder{
        public final TextView title;
        public VH(View v) {
            super(v);
            title = (TextView)v;
        }
    }

    private List<String> mDatas = new ArrayList<>();
    public TestRecycleViewAdapter() {
//        this.mDatas = data;

        for (int i = 0; i<10;i++){
            mDatas.add("hello wirld");
        }
    }

    public void GetChars(Class<?> a){

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        //LayoutInflater.from指定写法
        View v = new TextView(parent.getContext());
        v.setBackgroundResource(R.color.white);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((VH)viewHolder).title.setText(mDatas.get(i));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //item 点击事件
            }
        });
    }
}
