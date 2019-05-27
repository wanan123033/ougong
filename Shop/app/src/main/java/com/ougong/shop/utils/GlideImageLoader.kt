package com.ougong.shop.utils

import android.content.Context
import android.widget.ImageView
import com.baigui.commonlib.utils.LogUtils
import com.bumptech.glide.Glide
import com.youth.banner.loader.ImageLoader

class GlideImageLoader : ImageLoader() {
    override fun displayImage(context: Context, path: Any, imageView: ImageView) {

        Glide.with(context.applicationContext)
            .load(path)
            .into(imageView)
    }

//        @Override
//        public ImageView createImageView(Context context) {
//            //圆角
//            return new RoundAngleImageView(context);
//        }
}