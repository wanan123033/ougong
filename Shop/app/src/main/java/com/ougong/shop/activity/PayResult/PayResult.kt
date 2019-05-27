package com.ougong.shop.activity.PayResult

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.text.TextUtils
import android.view.View
import com.baigui.commonlib.kotlinUtils.extensions.Fromate
import com.baigui.commonlib.utils.ToastUtils
import com.ougong.shop.AccountHelper
import com.ougong.shop.ConstString
import com.ougong.shop.MainActivity
import com.ougong.shop.R
import com.ougong.shop.activity.orderhistory.OrderHistoryActivity
import com.ougong.shop.base_mvp.base.BaseActivity
import io.armcha.ribble.presentation.base_mvp.base.BaseContract
import io.armcha.ribble.presentation.base_mvp.base.BasePresenter
import kotlinx.android.synthetic.main.activity_pay_result.*
import kotlinx.android.synthetic.main.include_back_title.*

class PayResult : BaseActivity<BaseContract.View, BasePresenter<BaseContract.View>>() {
    override fun setContentViewSource() = R.layout.activity_pay_result

    var presenter = object : BasePresenter<BaseContract.View>() {

    }

    override fun initPresenter() = object : BasePresenter<BaseContract.View>() {}

    override fun initView() {
        AccountHelper.synchronizationOrder()
        title_back.visibility = View.VISIBLE
        title_back.setOnClickListener { finish() }
        if (intent.getBooleanExtra("payresult", false)) {
            title_name.text = "支付成功"
            pay_result_name.text = "订单支付成功"
            fail_pay_lin.visibility = View.GONE

            sucess_pay_lin.visibility = View.VISIBLE
            pay_result_ice.setImageResource(R.drawable.pay_sucess)
            /**
             * intent.putExtra("companyName",it.companyName)
            intent.putExtra("orderNo",it.orderNo)
             */
            var orderNo = intent.getStringExtra("orderNo")
            var companyName = intent.getStringExtra("companyName")
            if (!TextUtils.isEmpty(orderNo) && !TextUtils.isEmpty(companyName)) {
                //普通设计师
                item2.text = "订单号: " + orderNo
                item3.text = "装修公司名称: "+ companyName
                item3.visibility = View.VISIBLE
            }

            var money = intent.getDoubleExtra("money", 0.toDouble())
            item1.text = "支付金额:  ￥" + money.Fromate()
        } else {
            title_name.text = "支付失败"
            pay_result_ice.setImageResource(R.drawable.pay_fail)
            pay_result_name.text = "订单支付失败"
            sucess_pay_lin.visibility = View.GONE
            fail_pay_lin.visibility = View.VISIBLE
        }
        sucess_btn_1.setOnClickListener {
            val intent = Intent(this, OrderHistoryActivity::class.java)
            startActivity(intent)
            finish()
        }

        sucess_btn_2.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        fail_btn_2.setOnClickListener {
            val intent = Intent(this, OrderHistoryActivity::class.java)
            intent.putExtra(OrderHistoryActivity.PAGE_TITLE_POS, 1)
            startActivity(intent)
            finish()
        }

        fail_btn_1.setOnClickListener {
            val intent = Intent(this, OrderHistoryActivity::class.java)
            intent.putExtra(OrderHistoryActivity.PAGE_TITLE_POS, 1)
            startActivity(intent)
            finish()

        }
    }
}
