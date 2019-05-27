package com.ougong.shop.activity.orderhistory

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.util.Log
import android.view.View
import com.alipay.sdk.app.PayTask
import com.baigui.commonlib.utils.ToastUtils
import com.jcodecraeer.xrecyclerview.XRecyclerView
import com.ougong.shop.AccountHelper
import com.ougong.shop.Bean.Netbean
import com.ougong.shop.Bean.PayBean
import com.ougong.shop.ConstString
import com.ougong.shop.R
import com.ougong.shop.activity.PayResult.PayResult
import com.ougong.shop.adapter.OrderHistory2Adapter
import com.ougong.shop.base_mvp.base.BaseFragment
import com.ougong.shop.httpmodule.OrderHistoryJavaBean
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_order_history.*

class OrderHistoryFragment : BaseFragment<OrderHistoryContract.View,OrderHistoryContract.Presenter>(),OrderHistoryContract.View ,OrderOpeator{
    var mCurrent = 1

    override fun reshData(it: Netbean<String>) {
        mCurrent = 1
        presenter.getHistoryList(mCurrent, getStatus())
    }

    override fun buyItPay(it: Netbean<PayBean>) {
        payPreSucess(it.data)
    }

    override fun initHistoryList(bean: Netbean<OrderHistoryJavaBean>) {
        Log.e("bean",bean.data.rows!!.toList().toString())
        recycleView.refreshComplete()
        if (mCurrent != 1) {

            if (bean.data.total!! < ConstString.PAGESIZE){
                recycleView.setNoMore(true)
                recycleView.setLoadingMoreEnabled(false)
                return
            }else{
                recycleView.setNoMore(false)
                recycleView.setLoadingMoreEnabled(true)
            }
            if(bean.data.rows!!.isEmpty()){
                recycleView.setNoMore(true)
                recycleView.setLoadingMoreEnabled(false)
                return
            }
            (recycleView.adapter as OrderHistory2Adapter).addData(bean.data.rows!!)
        }else{
            (recycleView.adapter as OrderHistory2Adapter).setData(bean.data.rows!!)
        }
        if (mCurrent == 1 && bean.data.rows.isNullOrEmpty()){
            recycleView.visibility = View.GONE
            empty_view.visibility = View.VISIBLE
        }else{
            empty_view.visibility = View.GONE
            recycleView.visibility = View.VISIBLE
        }
    }

    override fun setContentViewSource() = R.layout.fragment_order_history
    override fun initPresenter() = OrderHistoryPresenter()
    var position:Int? = 0
    fun setIndex(position:Int):OrderHistoryFragment{
        this.position = position
        return this
    }

    override fun initView() {
        super.initView()
        recycleView.layoutManager = LinearLayoutManager(context)
        recycleView.adapter = OrderHistory2Adapter(context,this)
        mCurrent = 1
        presenter.getHistoryList(mCurrent, getStatus())

        recycleView.setPullRefreshEnabled(true)
        recycleView.setLoadingMoreEnabled(true)
        recycleView.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onRefresh() {
                mCurrent = 1
                presenter.getHistoryList(mCurrent, getStatus())
            }

            override fun onLoadMore() {
                mCurrent++
                presenter.getHistoryList(mCurrent, getStatus())
            }

        })
    }

    fun getStatus():Int? =
        when(position){
            0 -> null
            1 -> 0
            2 -> 10
            3 -> 20
            4 -> 30
            else -> null
        }

    override fun pay(orderId: Int) {
        //TODO 调用支付方式对话框支付  暂时先支持支付宝
        presenter.gotoPay(orderId,3)

    }

    override fun qrsh(orderId: Int) {
        presenter.qrsh(orderId)
    }

    override fun cannalOrder(orderId: Int) {
        presenter.cannalOrder(orderId)
    }

    override fun gotoPay(orderId: Int) {
        //TODO 调用支付方式对话框购买  暂时先支持支付宝
        presenter.gotoPay(orderId,3)
    }

    override fun delete(orderId: Int) {
        presenter.deleteOrder(orderId)
    }

    fun payPreSucess(it: PayBean) {

        AccountHelper.synchronizationShopCar()

        if (TextUtils.isEmpty(it.parameterInfo)){
            return
        }

        Flowable.fromCallable {
            val alipay = PayTask(activity)
            return@fromCallable alipay.payV2(it.parameterInfo, true)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

                if (TextUtils.equals(it["resultStatus"], "9000")) {
                    val intent = Intent(activity, PayResult::class.java)
                    intent.putExtra("payresult", true)
                    startActivity(intent)
                    activity.finish()
                } else {
                    val intent = Intent(activity, PayResult::class.java)
                    intent.putExtra("payresult", false)
                    startActivity(intent)
                    activity.finish()
                }
            }, Throwable::printStackTrace)

    }
}