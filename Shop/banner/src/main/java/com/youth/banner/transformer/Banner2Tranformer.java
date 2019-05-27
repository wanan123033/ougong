package com.youth.banner.transformer;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by zhouwei on 17/8/16.
 */

public class Banner2Tranformer implements ViewPager.PageTransformer {

    private float MAX_SCALE = 1.0f;
    private float MIN_SCALE = 0.9f;
    private float MIN_Alpha = 0.5f;

    @Override
    public void transformPage(View view, float position) {
        //setScaleY只支持api11以上
        if (position < -1) {
            view.setScaleX(MIN_SCALE);
            view.setScaleY(MIN_SCALE);
        } else if (position <= 1) //a页滑动至b页 ; a页从 0.0 -1 ;b页从1 ~ 0.0
        { // [-1,1]
            // Log.e("TAG", view + " , " + position + "");
            float scaleFactor = MIN_SCALE + (1 - Math.abs(position)) * (MAX_SCALE - MIN_SCALE);
            float alpha = MIN_Alpha + ((1 - Math.abs(position)) * (MAX_SCALE - MIN_Alpha));
            //每次滑动后进行微小的移动目的是为了防止在三星的某些手机上出现两边的页面为显示的情况
            if (position > 0) {
                view.setTranslationX(-scaleFactor * 2);
            } else if (position < 0) {
                view.setTranslationX(scaleFactor * 2);
            }
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
            view.setAlpha(alpha);
        } else { // (1,+Infinity]
            view.setScaleX(MIN_SCALE);
            view.setScaleY(MIN_SCALE);
            view.setAlpha(MIN_Alpha);
        }
    }
}