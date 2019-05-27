package com.ougong.shop.activity.SplashActivity

import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.baigui.commonlib.kotlinUtils.StarusBarUtils
import com.baigui.commonlib.kotlinUtils.extensions.isPermissionGranted
import com.baigui.commonlib.kotlinUtils.extensions.requestPermission
import com.baigui.commonlib.kotlinUtils.extensions.shouldShowPermissionRationale
import com.baigui.commonlib.utils.LogUtils
import com.baigui.commonlib.utils.SPUtils
import com.ougong.shop.MainActivity
import kotlinx.android.synthetic.main.activity_splash.*
import io.armcha.ribble.presentation.utils.extensions.start
import kotlinx.android.synthetic.main.activity_splash.*
import java.util.jar.Manifest
import com.ougong.shop.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val time = SPUtils.get(this, "RefeshOpenTime", 0L) as Long
        SPUtils.put(this, "RefeshOpenTime", System.currentTimeMillis())
        if (!(time == 0L || System.currentTimeMillis() - time >= 1000 * 3600 * 24 * 15)) {
            start<MainActivity>()
            finish()
        }

        StarusBarUtils.transparencyBar(this)
        setContentView(R.layout.activity_splash)


        viewpage.adapter = SplashAdapter(supportFragmentManager)
        splash_dot_0.isSelected = true
        viewpage.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {

            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

            }

            override fun onPageSelected(p0: Int) {
                ll.visibility = View.VISIBLE
                splash_dot_0.isSelected = false
                splash_dot_1.isSelected = false
                splash_dot_2.isSelected = false
                when (p0) {

                    0 -> {
                        splash_dot_0.isSelected = true
                    }
                    1 -> {
                        splash_dot_1.isSelected = true
                    }
                    2 -> {
                        splash_dot_2.isSelected = true
                    }
                    3 -> {
                        ll.visibility = View.GONE
                    }
                }
            }

        })
        if (!isPermissionGranted(android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //拒绝过，这次需要一个dialog来说明一下，给我们权限，但是这里就可以不要了、
            if (shouldShowPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            }
            requestPermission(
                arrayOf(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.WRITE_EXTERNAL_STORAGE,android.Manifest.permission.READ_PHONE_STATE
                ), 3
            )
        }
//        if (!isPermissionGranted(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//            //拒绝过，这次需要一个dialog来说明一下，给我们权限，但是这里就可以不要了、
//            if (shouldShowPermissionRationale(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//            }
//            requestPermission(
//                arrayOf(
//                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
//                ), 3
//            )
//
//        }
//        if (!isPermissionGranted(android.Manifest.permission.READ_PHONE_STATE))
//        //拒绝过，这次需要一个dialog来说明一下，给我们权限，但是这里就可以不要了、
//            if (shouldShowPermissionRationale(android.Manifest.permission.READ_PHONE_STATE)) {
//            }
//        requestPermission(
//            arrayOf(
//                android.Manifest.permission.READ_PHONE_STATE
//            ), 3
//        )
    }

}
