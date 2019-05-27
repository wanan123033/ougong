package com.baigui.commonview;

import android.content.Context;
import android.graphics.Color;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by benny .
 * on 10:54.2017/6/8 .
 * function：抖动功能
 */

public class JitterUtils {
    private static Animation shake;
    public static void JitterUtils(Context context) {
        //为组件设置一个抖动效果
         shake = AnimationUtils.loadAnimation(context, R.anim.shake);
    }

    public static void start(Context context, final EditText editText) {
        JitterUtils(context);
        editText.startAnimation(shake);
        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                editText.setHintTextColor(Color.parseColor("#f0f1301e"));
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                editText.setHintTextColor(Color.parseColor("#999999"));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
    public static void start(Context context, final TextView textView) {
        JitterUtils(context);
        textView.startAnimation(shake);
        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                textView.setHintTextColor(Color.parseColor("#f0f1301e"));
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                textView.setHintTextColor(Color.parseColor("#999999"));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
