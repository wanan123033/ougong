package com.ougong.shop.activity.orderhistory

import android.content.Intent
import android.view.View
import com.ougong.shop.R
import com.ougong.shop.base_mvp.base.BaseFragment
import io.armcha.ribble.presentation.base_mvp.base.BaseContract
import io.armcha.ribble.presentation.base_mvp.base.BasePresenter
import kotlinx.android.synthetic.main.fragment_order_menu.*

class OrderMenuFragment : BaseFragment<BaseContract.View,BasePresenter<BaseContract.View>>(),BaseContract.View {
    override fun setContentViewSource() = R.layout.fragment_order_menu
    override fun initPresenter() = object : BasePresenter<BaseContract.View>(){}
    override fun initView() {
        super.initView()
        rl_all.setOnClickListener(this)
        ll_item1.setOnClickListener(this)
        ll_item2.setOnClickListener(this)
        ll_item3.setOnClickListener(this)
        ll_item4.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.rl_all -> {   //全部订单
                val intent = Intent(getActivity(),OrderHistoryActivity::class.java)
                intent.putExtra(OrderHistoryActivity.PAGE_TITLE_POS,0)
                getActivity()?.startActivity(intent)
            }
            R.id.ll_item1 -> {  //待支付
                val intent = Intent(getActivity(),OrderHistoryActivity::class.java)
                intent.putExtra(OrderHistoryActivity.PAGE_TITLE_POS,1)
                getActivity()?.startActivity(intent)
            }
            R.id.ll_item2 -> {  //待发货
                val intent = Intent(getActivity(),OrderHistoryActivity::class.java)
                intent.putExtra(OrderHistoryActivity.PAGE_TITLE_POS,2)
                getActivity()?.startActivity(intent)
            }
            R.id.ll_item3 -> {  //待收货
                val intent = Intent(getActivity(),OrderHistoryActivity::class.java)
                intent.putExtra(OrderHistoryActivity.PAGE_TITLE_POS,3)
                getActivity()?.startActivity(intent)
            }
            R.id.ll_item4 -> {  //已收货
                val intent = Intent(getActivity(),OrderHistoryActivity::class.java)
                intent.putExtra(OrderHistoryActivity.PAGE_TITLE_POS,4)
                getActivity()?.startActivity(intent)
            }
        }
    }
}