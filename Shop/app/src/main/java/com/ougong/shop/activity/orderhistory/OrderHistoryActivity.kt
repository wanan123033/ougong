package com.ougong.shop.activity.orderhistory

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.View
import com.ougong.shop.R
import com.ougong.shop.base_mvp.base.BaseActivity
import io.armcha.ribble.presentation.base_mvp.base.BaseContract
import io.armcha.ribble.presentation.base_mvp.base.BasePresenter
import kotlinx.android.synthetic.main.activity_order_history2.*
import kotlinx.android.synthetic.main.include_back_title.*

class OrderHistoryActivity : BaseActivity<BaseContract.View,BasePresenter<BaseContract.View>>(),BaseContract.View {
    override fun setContentViewSource() = R.layout.activity_order_history2
    override fun initPresenter() = object : BasePresenter<BaseContract.View>(){}
    companion object {
        val PAGE_TITLE_POS = "PAGE_TITLE_POS"
    }

    override fun initView() {
        super.initView()
        val array:Array<String>? = arrayOf("全部订单","待支付","待发货","待收货","已收货")
        tabLayout.setupWithViewPager(page_content)

        page_content.adapter = OrderHistoryTitleAdapter(supportFragmentManager,array!!)

        val pageTitlePos = intent.getIntExtra(PAGE_TITLE_POS,0)
        page_content.currentItem = pageTitlePos

        title_name.text = "我的订单"
        title_back.visibility = View.VISIBLE
        title_back.setOnClickListener{
            finish()
        }
    }

    class OrderHistoryTitleAdapter(fm:FragmentManager,var array:Array<String>): FragmentPagerAdapter(fm){
        override fun getItem(p0: Int):Fragment{
            return OrderHistoryFragment().setIndex(p0)
        }

        override fun getCount() = array.size

        override fun getPageTitle(position: Int): String? {
            return array[position]
        }
    }
}