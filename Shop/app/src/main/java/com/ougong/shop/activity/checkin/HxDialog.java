package com.ougong.shop.activity.checkin;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ougong.shop.R;
import com.ougong.shop.httpmodule.HxBean;
import com.ougong.shop.view.wheelview.WheelView;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HxDialog extends DialogFragment{
    private View view;
    private WheelView wv_hx;
    private View view_dismiss;
    private TextView tv_sucess;
    private HxBean[] hxData;

    @Override
    public void onStart() {
        super.onStart();
        //设置 dialog 的宽高
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //设置 dialog 的背景为 null
        getDialog().getWindow().setBackgroundDrawable(null);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_hx,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findViews();
        setListener();
        initData();
    }

    private void initData() {
        if (hxData != null) {
            List<String> names = new ArrayList<>();
            for(HxBean bean : hxData){
                names.add(bean.getName());
            }
            wv_hx.setItems(names,0);
        }
    }

    private void setListener() {
        view_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        tv_sucess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() instanceof CheckInActivity) {
                    ((CheckInActivity) getActivity()).setHx(hxData[wv_hx.getSelectedPosition()]);
                }
                dismiss();
            }
        });
    }

    private void findViews() {
        wv_hx = view.findViewById(R.id.wv_hx);
        view_dismiss = view.findViewById(R.id.view_dismiss);
        tv_sucess = view.findViewById(R.id.tv_sucess);
        String[] stringArray = getContext().getResources().getStringArray(R.array.hx_value);
        wv_hx.setItems(Arrays.asList(stringArray),0);
    }

    public void setHxData(@NotNull HxBean[] data) {
        this.hxData = data;
        if (isVisible()){
            initData();
        }
    }
}
