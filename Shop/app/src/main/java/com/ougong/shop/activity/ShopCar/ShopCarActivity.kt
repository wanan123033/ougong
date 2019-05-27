package com.ougong.shop.activity.ShopCar

import android.content.Intent
import com.baigui.commonlib.utils.LogUtils
import com.ougong.shop.ConstString
import com.ougong.shop.R
import com.ougong.shop.activity.MainShopCarFragment.ShopCarFragment
import com.ougong.shop.base_mvp.base.BaseActivity
import io.armcha.ribble.presentation.base_mvp.base.BaseContract
import io.armcha.ribble.presentation.base_mvp.base.BasePresenter

/**
 * 这是一个多余的  购物车页面
 */
class ShopCarActivity :  BaseActivity<BaseContract.View, BasePresenter<BaseContract.View>>() {

    var presenter = object : BasePresenter<BaseContract.View>(){

    }
    override fun initPresenter()= object : BasePresenter<BaseContract.View>(){

    }

    override fun setContentViewSource() = R.layout.activity_shop_car

    override fun initView() {
        supportFragmentManager.beginTransaction().let {
            it.add(R.id.container,ShopCarFragment(),"shopcar")
            it.commit()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ConstString.LOG_IN_REQUEST) {
            if (resultCode == ConstString.LOG_IN_RESULT_OK) {//最好是刷新一下Fragment。但是每次解锁屏幕都要请求数据，用最暴力的方法解决吧

                supportFragmentManager.beginTransaction().let {
                    it.replace(R.id.container,ShopCarFragment(),"shopcar")
                    it.commit()
                }
            } else if (resultCode == ConstString.LOG_IN_RESULT_NO) {
                finish()
//                LogUtils.e("------------------------",this)
//                mHomePageFragment = doSelect(mHomePageFragment,0){homePageFragmentV2()} }
            }
        }
    }
}
