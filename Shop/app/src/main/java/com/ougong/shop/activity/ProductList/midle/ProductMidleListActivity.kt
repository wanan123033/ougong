package com.ougong.shop.activity.ProductList.midle

import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.View
import com.ougong.shop.Bean.CateryBean
import com.ougong.shop.Bean.fuckNetbean
import com.ougong.shop.R
import com.ougong.shop.activity.MainLabeFrgment.LabelFitmentFragment
import com.ougong.shop.activity.MainLabeFrgment.LabelFragmentV2
import com.ougong.shop.base_mvp.base.BaseActivity
import io.armcha.ribble.presentation.base_mvp.base.BaseContract
import io.armcha.ribble.presentation.base_mvp.base.BasePresenter
import kotlinx.android.synthetic.main.activity_product_midle_list.*
import kotlinx.android.synthetic.main.include_back_title.*
import android.graphics.Typeface.DEFAULT_BOLD
import android.view.TextureView
import android.widget.TextView
import com.ougong.shop.AccountHelper
import com.ougong.shop.activity.ShopCar.ShopCarActivity
import io.armcha.ribble.presentation.utils.extensions.start
import kotlinx.android.synthetic.main.include_shop_car_ice.*


class ProductMidleListActivity : BaseActivity<BaseContract.View, BasePresenter<BaseContract.View>>() {

    var mBean: CateryBean? = null

    override fun setContentViewSource() = R.layout.activity_product_midle_list

    var presenter = object : BasePresenter<BaseContract.View>() {

    }

    override fun initPresenter() = object : BasePresenter<BaseContract.View>() {
    }
    override fun initData() {

        mBean = intent.getSerializableExtra("data") as CateryBean
        title_name.setText(mBean?.name)

        viewpage.adapter = MyAdapter(supportFragmentManager)

        tabLayout.setupWithViewPager(viewpage)

        mBean?.let {
            (viewpage.adapter as MyAdapter).setData(mBean!!)
        }
    }

    fun refeshViewShopCarIce() {
        if (AccountHelper.globalDataTemp.shopCarCount > 0) {
            dot_shopcar_count.visibility = View.VISIBLE
            dot_shopcar_count.text = AccountHelper.globalDataTemp.shopCarCount.toString()
        } else {
            dot_shopcar_count.visibility = View.GONE
        }
    }
    override fun initView() {
        super.initView()
        refeshViewShopCarIce()
        shopcar_ice.setOnClickListener { start<ShopCarActivity>() }
        title_back.visibility = View.VISIBLE

        title_back.setOnClickListener { finish() }
    }
    inner class MyAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(p0: Int): Fragment {
            if (p0 == 0) {
                return ContentFragment().setData(mFuckNetbean!!)
            } else {
                return ContentFragment().setData(mFuckNetbean!!.child[p0-1])
            }
        }
        var mFuckNetbean: CateryBean? = null
        fun setData(data: CateryBean) {
            mFuckNetbean = data
            notifyDataSetChanged()
        }

        override fun getCount(): Int = if (mFuckNetbean == null) {
            0
        } else {
            mFuckNetbean!!.child.size + 1
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return if (position == 0) {
                "全部"
            } else {
                mFuckNetbean?.child!![position - 1].name
            }
        }
    }

}