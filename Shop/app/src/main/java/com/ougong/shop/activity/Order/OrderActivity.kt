package com.ougong.shop.activity.Order

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Handler
import android.support.v4.app.DialogFragment
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import com.alipay.sdk.app.PayTask
import com.baigui.commonlib.utils.LogUtils
import com.ougong.shop.AccountHelper
import com.ougong.shop.Bean.OrderBean
import com.ougong.shop.Bean.PayBean
import com.ougong.shop.ConstString
import com.ougong.shop.R
import com.ougong.shop.activity.Maininfo.mangeAdress.AddressBean
import com.ougong.shop.activity.PayResult.PayResult
import com.ougong.shop.base_mvp.base.BaseActivity
import com.ougong.shop.fragment.NoAddressDialog
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_confirm_order.*
import kotlinx.android.synthetic.main.include_back_title.*

class OrderActivity : BaseActivity<OrderContract.View, OrderContract.Presenter>(), OrderContract.View {
    var mOrderType:Int = 1   //1 普通订单  2.套餐订单
    companion object {
        val ORDER_TYPE:String = "ORDER_TYPE"
    }

    @SuppressLint("CheckResult")
    override fun payPreSucess(it: PayBean) {

        AccountHelper.synchronizationShopCar()

        if (AccountHelper.getUser().type == 2 && AccountHelper.getUser().infoData?.designData?.parentType != 4){
            if (!TextUtils.isEmpty(it.orderNoList!![0])){
                val intent = Intent(this, PayResult::class.java)
                intent.putExtra("payresult", true)
                intent.putExtra("companyName",it.companyName)
                intent.putExtra("orderNo",it.orderNoList!![0])
                intent.putExtra("money",mTotalPrice)
                startActivity(intent)
                finish()
                return
            }
        }

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

    override fun initPresenter() = OrderPresenter()

    var mTotalPrice = 0.toDouble()
    override fun getOrderSucess(it: OrderBean) {
//        mTotalPrice = 0.toDouble()
//        for (it1 in it.products!!) {
//            for (it2 in it1.specificationCartVos!!) {
//                mTotalPrice = mTotalPrice + it2.price * it2.count
//            }
//        }
//        order_total_price.text = "应付金额: ￥" + mTotalPrice.Fromate()
        mbean = it
        mOrderAdapter.setOrderBean(it)

        mOrderAdapter.addressBean = getAddress()

        AccountHelper.synchronizationOrder()
    }

    var mbean: OrderBean? = null
    override fun setContentViewSource() = R.layout.activity_confirm_order

    private lateinit var mOrderAdapter: OrderAdapter

    override fun initView() {
        title_back.visibility = View.VISIBLE
        title_back.setOnClickListener { finish() }

        mOrderType = intent.getIntExtra(ORDER_TYPE,1)
        title_name.text = "填写订单"
        mOrderAdapter = OrderAdapter(this)
        recycleView.setAdapter(mOrderAdapter)
        //这里不用自定义的流式布局也是可以的，这里这是根据特定需要简单自定义了一个
        recycleView.setLayoutManager(LinearLayoutManager(this))
        buy_it.setOnClickListener {

            if (mbean == null)
                return@setOnClickListener
            val tem = StringBuilder()
            for (it1 in mbean!!.products!!) {
                for (it2 in it1.specificationCartVos!!) {
                    tem.append(it2.id.toString() + "-" + it2.count + ",")
                }
            }
            tem.deleteCharAt(tem.lastIndex)

            val address = getAddress()
            if (address == null) {
                noAddressDialog = NoAddressDialog()
                noAddressDialog!!.show(supportFragmentManager, "noaddress")
                Handler().postDelayed({
                    noAddressDialog?.dismiss()
                    noAddressDialog = null
                }, 3000)
                LogUtils.e("--------------", this)
            } else {
                presenter.BuyIt(mOrderType,address.id, tem.toString(), mOrderAdapter.mDefaultPost, 3, mOrderAdapter.noteString ?: "")
            }

        }
    }

    var noAddressDialog: DialogFragment? = null
    fun getAddress(): AddressBean? {
        if (mChooseAddressBean != null) {
            return mOrderAdapter.addressBean!!
        }
        mbean!!.addressList?.let {
            for (it in mbean!!.addressList!!) {
                if (it.isDefault) {
                    return it
                }
            }
            if (mbean!!.addressList!!.size > 0)
                return mbean!!.addressList!![0]
        }
        return null
    }

    override fun initData() {
        val order = intent.getStringExtra("order")
        presenter.sendOrder(order,false)
    }

    var mChooseAddressBean: AddressBean? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            ConstString.CHOOSE_ADDRESS_REQUEST -> {
                if (resultCode == ConstString.CHOOSE_ADDRESS_RESULT) {
                    mChooseAddressBean = data?.getSerializableExtra(ConstString.CHOOSE_ADDRESS_RESULT_ID) as AddressBean
                    mOrderAdapter.addressBean = mChooseAddressBean
                    mOrderAdapter.notifyDataSetChanged()
                } else {
                    mChooseAddressBean = null
                }
            }

        }
    }

    fun setPrice(fromate: String) {
        order_total_price.text = fromate
    }
}
