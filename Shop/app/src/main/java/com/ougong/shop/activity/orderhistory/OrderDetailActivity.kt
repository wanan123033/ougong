package com.ougong.shop.activity.orderhistory

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import com.alipay.sdk.app.PayTask
import com.baigui.commonlib.utils.ToastUtils
import com.ougong.shop.AccountHelper
import com.ougong.shop.Bean.Netbean
import com.ougong.shop.Bean.OrderHistoryDetail
import com.ougong.shop.Bean.PayBean
import com.ougong.shop.R
import com.ougong.shop.activity.PayResult.PayResult
import com.ougong.shop.adapter.OrderHistoryItemAdapter
import com.ougong.shop.base_mvp.base.BaseActivity
import com.ougong.shop.httpmodule.OrderHistoryJavaBean
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_order_detail.*
import kotlinx.android.synthetic.main.include_back_title.*

class OrderDetailActivity:BaseActivity<OrderDetailContract.View,OrderDetailContract.Presenter>(),OrderDetailContract.View,
    View.OnClickListener {
    var data: OrderHistoryDetail? = null
    override fun reshData(it: Netbean<String>) {

    }

    override fun buyItPay(it: Netbean<PayBean>) {
        payPreSucess(it.data)
    }
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tv_cannel -> {  //取消订单
                cannalOrder(data!!.id!!)
            }
            R.id.tv_buy_it -> {  //立即购买
                pay(data!!.id!!)
            }
            R.id.tv_qrsh  -> {   //确认收货
                qrsh(data!!.id!!)
            }
            R.id.tv_buy_next -> {//再次购买
                gotoPay(data!!.id!!)
            }
            R.id.tv_delete -> {  //删除订单
                delete(data!!.id!!)
            }
        }
    }

    override fun initOrderDetail(data: OrderHistoryDetail) {
        this.data = data
        tv_pay_status?.text = data.statusName
        tv_name?.text = data.addressSnap?.name
        tv_phone?.text = data.addressSnap?.mobile
        tv_address?.text = data.addressSnap?.addressInfo
        tv_store?.text = data.brandInfoList!![0].brandSnap?.name
        tv_total_price?.text = "￥"+data.productPrice
        tv_fuwu_price?.text = "￥"+data.servicePrice
        tv_mu_price?.text = "￥"+data.woddenPrice
        tv_duan_price?.text = "￥"+data.shortDistancePrice
        tv_sum_total_price?.text = "共"+data.orderInfoList?.size+"件商品，应付金额：￥"+data.productPrice
        tv_order_code?.text = data.orderNo
        tv_data_code?.text = data.createTime
        tv_payType_code?.text = data.payTypeName
        tv_payCode_code?.text = data.paymentNo
        tv_brand_code.text = data.brandInfoList!![0].brandSnap?.name
        tv_totalPrice.text = "应付金额：￥"+data.productPrice

        initRecyclerView(data.orderInfoList)

        if (data.status!! <= -40.0 && data.status!! >= -40.3){   //已退款
            tv_cannal?.visibility = View.GONE
            tv_buy_it?.visibility = View.GONE
            tv_qrsh?.visibility = View.GONE
            tv_buy_next?.visibility = View.GONE
            tv_delete?.visibility = View.VISIBLE
        }else if (data.status!! <= -30.0 && data.status!! >= -30.3){  //待退款
            tv_cannal?.visibility = View.GONE
            tv_buy_it?.visibility = View.GONE
            tv_qrsh?.visibility = View.GONE
            tv_buy_next?.visibility = View.GONE
            tv_delete?.visibility = View.GONE
        }else if (data.status!! <= -20.0 && data.status!! >= -20.3){  //待处理
            tv_cannal?.visibility = View.GONE
            tv_buy_it?.visibility = View.GONE
            tv_qrsh?.visibility = View.GONE
            tv_buy_next?.visibility = View.GONE
            tv_delete?.visibility = View.GONE
        }else if (data.status!! <= -10.0 && data.status!! >= -10.3){  //已取消
            tv_cannal?.visibility = View.GONE
            tv_buy_it?.visibility = View.GONE
            tv_qrsh?.visibility = View.GONE
            tv_buy_next?.visibility = View.GONE
            tv_delete?.visibility = View.VISIBLE
        }else if (data.status!! <= 0.3 && data.status!! >= 0.0){     //未支付
            tv_cannal?.visibility = View.VISIBLE
            tv_buy_it?.visibility = View.VISIBLE
            tv_qrsh?.visibility = View.GONE
            tv_buy_next?.visibility = View.GONE
            tv_delete?.visibility = View.GONE
        }else if (data.status!! <= 10.8 && data.status!! >= 10.0){   //已支付
            tv_cannal?.visibility = View.GONE
            tv_buy_it?.visibility = View.GONE
            tv_qrsh?.visibility = View.GONE
            tv_buy_next?.visibility = View.VISIBLE
            tv_delete?.visibility = View.GONE
        }else if (data.status!! <= 20.2 && data.status!! >= 20.0){   //已发货
            tv_cannal?.visibility = View.GONE
            tv_buy_it?.visibility = View.GONE
            tv_qrsh?.visibility = View.VISIBLE
            tv_buy_next?.visibility = View.VISIBLE
            tv_delete?.visibility = View.GONE
        }else if (data.status!! <= 30.2 && data.status!! >= 30.0){   //已收货
            tv_cannal?.visibility = View.GONE
            tv_buy_it?.visibility = View.GONE
            tv_qrsh?.visibility = View.GONE
            tv_buy_next?.visibility = View.VISIBLE
            tv_delete?.visibility = View.VISIBLE

        }
    }

    private fun initRecyclerView(orderInfoList: Array<OrderHistoryJavaBean.OrderInfo>?) {
        rv_product.layoutManager = LinearLayoutManager(this)
        rv_product.adapter = OrderHistoryItemAdapter(this,orderInfoList!!)
    }

    override fun setContentViewSource() = R.layout.activity_order_detail
    override fun initPresenter() = OrderDetailPresenter()
    companion object {
        val ORDER_ID = "ORDER_ID"
    }

    override fun initView() {
        super.initView()
        title_name.text = "订单详情"
        title_back.visibility = View.VISIBLE
        title_back.setOnClickListener {
            finish()
        }
        val orderId = intent.getIntExtra(ORDER_ID,0)
        presenter.getOrderDetail(orderId)

        tv_cannal?.setOnClickListener(this)
        tv_buy_it?.setOnClickListener(this)
        tv_qrsh?.setOnClickListener(this)
        tv_buy_next?.setOnClickListener(this)
        tv_delete?.setOnClickListener(this)
    }

    fun pay(orderId: Int) {
        //TODO 调用支付方式对话框支付  暂时先支持支付宝
        presenter.gotoPay(orderId,3)

    }

    fun qrsh(orderId: Int) {
        presenter.qrsh(orderId)
    }

    fun cannalOrder(orderId: Int) {
        presenter.cannalOrder(orderId)
    }

    fun gotoPay(orderId: Int) {
        //TODO 调用支付方式对话框购买  暂时先支持支付宝
        presenter.gotoPay(orderId,3)
    }

    fun delete(orderId: Int) {
        presenter.deleteOrder(orderId)
    }
    fun payPreSucess(it: PayBean) {

        AccountHelper.synchronizationShopCar()

        if (TextUtils.isEmpty(it.parameterInfo)){
            return
        }

        Flowable.fromCallable {
            val alipay = PayTask(this)
            return@fromCallable alipay.payV2(it.parameterInfo, true)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

                if (TextUtils.equals(it["resultStatus"], "9000")) {
                    val intent = Intent(this, PayResult::class.java)
                    intent.putExtra("payresult", true)
                    startActivity(intent)
                    finish()
                } else {
                    val intent = Intent(this, PayResult::class.java)
                    intent.putExtra("payresult", false)
                    startActivity(intent)
                    finish()
                }
            }, Throwable::printStackTrace)

    }
}