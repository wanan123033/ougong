package com.ougong.shop.activity.SplashActivity

import android.media.Image
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.ougong.shop.MainActivity
import com.ougong.shop.R
import io.armcha.ribble.presentation.utils.extensions.start
import kotlinx.android.synthetic.main.fragment_splash.view.*
import kotlinx.android.synthetic.main.fragment_splash_last.*

class SplashAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(p0: Int): Fragment {
//        return CommonFrament().setPosation(p0)
        return when (p0) {
            0,1,2 -> CommonFrament().setPosation(p0)
            else -> LastFragment()
        }
    }

    override fun getCount() = 4
}

class CommonFrament : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        val imageView = ImageView(activity)
//        imageView.scaleType = ImageView.ScaleType.FIT_XY
//        resourceId?.let { imageView.setImageResource(it) }

        val view = inflater.inflate(R.layout.fragment_splash,container,false)
        resourceId?.let {
            when (it){
                0 ->{
                    view.splash_fragment_title.setImageResource(R.drawable.splash_title1)
                    view.splash_fragment_content.setImageResource(R.drawable.splash_content_2)
                }
                1->{
                    view.splash_fragment_title.setImageResource(R.drawable.splash_title_3)
                    view.splash_fragment_content.setImageResource(R.drawable.splash_content1)
                }
                2->{
                    view.splash_fragment_title.setImageResource(R.drawable.splash_title_4)
                    view.splash_fragment_content.setImageResource(R.drawable.splash_content_3)
                }
            }
        }

        return view
    }

    var resourceId: Int? = null
    //这个可以是构造函数中的值，但是貌似不太好，所以这里这样会写，其实这个肯定在oncreatview之前调用

    fun setPosation(id: Int): Fragment {
        resourceId = id
        return this
    }
}

class LastFragment() : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_splash_last, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        enter.setOnClickListener {
            enter.isEnabled = true
            activity!!.start<MainActivity>()
            activity!!.finish()
        }
    }

}