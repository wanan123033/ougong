package com.ougong.shop.mtest1;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import com.ougong.shop.R;
import com.ougong.shop.V1.HomepagerRecycleAdapter;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.ArrayList;
import java.util.List;

public class bannerHelper {

    public static void help(MZBannerView view, int index) {
        List<String> l = new ArrayList<String>();
//        for (int i = 0; i < 3; i++) {
//            l.add(R.drawable.dinner_room + "");
//        }

        if (index == 1){
            l.add(R.drawable.b2_1 + "");
            l.add(R.drawable.b2_2+ "");
            l.add(R.drawable.b2_3 + "");
        }else if (index == 2){
            l.add(R.drawable.b3_3 + "");
            l.add(R.drawable.b3_1 + "");
            l.add(R.drawable.b_v2_1 + "");
        }
        view.setCanLoop(true);
        view.setIndicatorVisible(false);
        view.setPages(l, new MZHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });
       //        view.start();
    }

    static class BannerViewHolder implements MZViewHolder<String>

    {
        private ImageView mImageView;

        @Override
        public View createView(Context context) {
            ImageView view = new ImageView(context);
            mImageView = view;
            return view;
        }

        @Override
        public void onBind(Context context, int position, String data) {
            mImageView.setImageResource(Integer.decode(data));
        }
    }
}