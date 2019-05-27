package com.ougong.shop.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.bumptech.glide.Glide;
import com.ougong.shop.App;
import com.ougong.shop.R;

import java.util.List;

public class ViewPagerImg extends LinearLayout implements ViewPager.OnPageChangeListener {
    private ViewPager viewpager;
    private LinearLayout ll_img;
    public ViewPagerImg(Context context) {
        super(context);
        initView(context,null);
    }

    public ViewPagerImg(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context,attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        LayoutInflater.from(getContext()).inflate(R.layout.viewpager_img,this,true);
        viewpager = findViewById(R.id.viewpager);
        ll_img = findViewById(R.id.ll_img);

        viewpager.addOnPageChangeListener(this);

    }

    public void setImgData(List<String> imgUrls,View.OnClickListener onClickListener){
        viewpager.setAdapter(new ViewPagerImgAdapter(getContext(),imgUrls,onClickListener));
        addViewIcon(imgUrls);

    }

    private void addViewIcon(final List<String> imgUrls) {
        ll_img.removeAllViews();
        for (int i = 0 ; i < imgUrls.size() ; i++){
            final ImageView view = (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.item_viewpager_img,null,false);
            Glide.with(App.Companion.getApp()).load(imgUrls.get(i)).into(view);
            ll_img.addView(view);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT);
            layoutParams.rightMargin = 10;
            view.setLayoutParams(layoutParams);

            if (i == 0){
                view.setBackgroundResource(R.drawable.img_selected_combo);
            }else {
                view.setBackground(null);
            }
            final int finalI = i;
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onImgItemClick(ll_img,view, finalI,imgUrls.get(finalI));
                }
            });

        }

    }

    private void onImgItemClick(ViewGroup layout, View view, int position, String imageUrl) {
        viewpager.setCurrentItem(position);
        for (int i = 0 ; i < ll_img.getChildCount() ; i++){
            if (i == position){
                ll_img.getChildAt(i).setBackgroundResource(R.drawable.img_selected_combo);
            }else {
                ll_img.getChildAt(i).setBackground(null);
            }
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0 ; i < ll_img.getChildCount() ; i++){
            if (i == position){
                ll_img.getChildAt(i).setBackgroundResource(R.drawable.img_selected_combo);
            }else {
                ll_img.getChildAt(i).setBackground(null);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    static class ViewPagerImgAdapter extends PagerAdapter{
        private Context context;
        private List<String> imgUrls;
        private View.OnClickListener onClickListener;

        public ViewPagerImgAdapter(Context context,List<String> imgUrls,View.OnClickListener onClickListener) {
            this.imgUrls = imgUrls;
            this.context = context;
            this.onClickListener = onClickListener;
        }

        @Override
        public int getCount() {
            return imgUrls.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView view = new ImageView(context);
            view.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(App.Companion.getApp()).load(imgUrls.get(position)).into(view);
            view.setOnClickListener(onClickListener);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
//            super.destroyItem(container, position, object);
        }
    }
}
