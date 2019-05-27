package com.ougong.shop;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.View;
import com.baigui.netlib.BeanBase;
import com.ougong.shop.activity.mainHomepage.HomePageFragment;
import com.ougong.shop.activity.Maininfo.InfoFragment;
import com.ougong.shop.V1.LabelFragment;
import com.ougong.shop.activity.MainShopCarFragment.ShopCarFragment;

import java.util.HashMap;
import java.util.Map;

public class test extends View {
    public test(Context context) {
        super(context);
        init();
    }

    private void init() {
        map.put(0,new HomePageFragment());
        map.put(1,new LabelFragment());
        map.put(2,new ShopCarFragment());
        map.put(3,new InfoFragment());

        BeanBase[] a;
    }

    public test(Context context,  AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public test(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public test(Context context,  AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    protected Map<Integer,Fragment> map = new HashMap<>();
    public Fragment getf(int index){
        return map.get(index);
    }
}
