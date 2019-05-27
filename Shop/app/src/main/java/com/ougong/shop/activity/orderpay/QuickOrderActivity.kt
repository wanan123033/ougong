package com.ougong.shop.activity.orderpay

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.util.Log
import android.view.View
import com.alipay.sdk.app.PayTask
import com.baigui.commonlib.kotlinUtils.extensions.Fromate
import com.baigui.commonlib.kotlinUtils.extensions.Fromate1
import com.ougong.shop.AccountHelper
import com.ougong.shop.Bean.OrderBean
import com.ougong.shop.Bean.PayBean
import com.ougong.shop.Bean.fuckNetbean
import com.ougong.shop.ConstString
import com.ougong.shop.R
import com.ougong.shop.activity.MainShopCarFragment.ChooseAddress.ChooseAddress
import com.ougong.shop.activity.Maininfo.mangeAdress.AddressBean
import com.ougong.shop.activity.PayResult.PayResult
import com.ougong.shop.base_mvp.base.BaseActivity
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_quick_order.*
import kotlinx.android.synthetic.main.include_back_title.*
import okhttp3.Request
import okhttp3.RequestBody

class QuickOrderActivity : BaseActivity<QuickOrderContract.View,QuickOrderContract.Presenter>(),QuickOrderContract.View,
    View.OnClickListener {

    override fun setContentViewSource() = R.layout.activity_quick_order
    override fun initPresenter() = QuickOrderPresenter()
    companion object {
        @JvmField
        var ORDER_TYPE = "ORDER_TYPE"   //订单类型  1 普通订单，2 套餐订单
        @JvmField
        var ORDER_STR_CONTENT = "ORDER_STR_CONTENT" //商品字符串  规格id-数量,规格id-数量
        @JvmField
        var ORDER_DATA = "ORDER_DATA" //订单数据 购物车 套餐数据
    }
    var address:AddressBean? = null     //当前收货地址
    var payType:Int = 0  //支付方式  1企业网银，2线下，3
    var orderType:Int = 0 // 订单类型  1  普通订单  2  套餐订单
    var deliveryType:Int = 0 //  物流方式  1 欧工物流  2  自选物流
    var orderData:String? = null //订单数据
    var mTotalPrice:Double? = null
    var order_str_content:String? = null
    override fun initView() {
        super.initView()
        title_name.text = "填写订单"
        title_back.visibility = View.VISIBLE
        title_back.setOnClickListener {
            finish()
        }
        rl_no_address.setOnClickListener(this)
        rl_mo_address.setOnClickListener(this)
        rl_alipay_pay.setOnClickListener(this)
        rl_weixin_pay.setOnClickListener(this)
        rl_wuliu_ougong.setOnClickListener(this)
        rl_wuliu_zixuan.setOnClickListener(this)
        tv_commit.setOnClickListener(this)
        presenter.getAdressList()

        recycleView.layoutManager = LinearLayoutManager(this)
        recycleView.adapter = OrderListAdapter(applicationContext)
        order_str_content = intent.getStringExtra(ORDER_STR_CONTENT)
        orderType = intent.getIntExtra(ORDER_TYPE,0)
        orderData = intent.getStringExtra(ORDER_DATA)
        initOrderProduct(order_str_content!!)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.rl_no_address -> {    //收货地址选择
                var intent = Intent(this, ChooseAddress::class.java)
                startActivityForResult(intent, ConstString.CHOOSE_ADDRESS_REQUEST)
            }
            R.id.rl_mo_address -> {    //收货地址选择
                var intent = Intent(this, ChooseAddress::class.java)
                intent.putExtra(ConstString.CHOOSE_ADDRESS_HANDLE,address!!.id)
                startActivityForResult(intent, ConstString.CHOOSE_ADDRESS_REQUEST)
            }
            R.id.rl_alipay_pay -> {      //支付宝支付
                payType = 3
                iv_alipay_sel.setImageResource(R.drawable.checkbox_on)
                iv_weixin_icon.setImageResource(R.drawable.shopcar_check_false)

            }
            R.id.rl_weixin_pay -> {      //微信支付
                payType = 4
                iv_weixin_icon.setImageResource(R.drawable.checkbox_on)
                iv_alipay_sel.setImageResource(R.drawable.shopcar_check_false)
            }
            R.id.rl_wuliu_ougong ->{      //欧工物流
                iv_ougong_info.visibility = View.VISIBLE
                rl_zixuan_info.visibility = View.GONE
                deliveryType = 1

                iv_ougong.setImageResource(R.drawable.checkbox_on)
                iv_zixuan.setImageResource(R.drawable.shopcar_check_false)
            }
            R.id.rl_wuliu_zixuan ->{      //自选物流
                iv_ougong_info.visibility = View.GONE
                rl_zixuan_info.visibility = View.VISIBLE
                deliveryType = 2

                iv_ougong.setImageResource(R.drawable.shopcar_check_false)
                iv_zixuan.setImageResource(R.drawable.checkbox_on)
            }
            R.id.tv_commit ->{    //提交订单
                var logisticsCompany:String? = null
                var ownLogisticsName:String? = null
                var ownLogisticsPhone:String? = null
                if (deliveryType == 2){
                    logisticsCompany = ed_wuliu_name.text.toString().trim()
                    ownLogisticsName = ed_wuliu_xingming.text.toString().trim()
                    ownLogisticsPhone = ed_wuliu_phone.text.toString().trim()
                }
                if (orderType == 1){ //普通订单
                    presenter.buyIt(orderType,address!!.id,orderData,null,deliveryType,payType,"",true,logisticsCompany,ownLogisticsName,ownLogisticsPhone)
                }else if (orderType == 2){   //套餐订单
                    presenter.buyIt(orderType,address!!.id,null,orderData,deliveryType,payType,"",true,logisticsCompany,ownLogisticsName,ownLogisticsPhone)
                }
            }
        }
    }
    private fun initOrderProduct(orderStr:String) {
        presenter.sendOrder(orderStr,true)
    }

    override fun getAdressSucess(it: fuckNetbean<AddressBean>) {
        //TODO 取出默认的收货地址

        for (add in it.data){
            if (add.isDefault){
                address = add
                break
            }
        }
        if (address != null){
            rl_mo_address.visibility = View.VISIBLE
            rl_no_address.visibility = View.GONE
        }else{
            rl_mo_address.visibility = View.GONE
            rl_no_address.visibility = View.VISIBLE
        }
        initAddress(address)
    }
    override fun payPreSucess(data: PayBean) {
        if (payType == 3){    //支付宝
            alipay(data)
        }else if (payType == 4){  //微信

        }
    }



    override fun getOrderSucess(it: OrderBean) {
        val  specss = it.products!![0]!!.specificationCartVos!!
        //普通订单时需要更新商品数量及价格
        if (orderType == 1){
            for (spec in specss){
                if (order_str_content!!.contains(spec.properties!![0].specId.toString())){
                    val split = order_str_content!!.split(",")
                    for (specStr in split){
                        if (specStr.contains(spec.properties!![0].specId.toString())){
                            val count = specStr.split("-")[1]
                            spec.minCount = count.toInt()
                            break
                        }
                    }
                }
            }
        }
        (recycleView.adapter as OrderListAdapter).setData(specss)
        initPrice(it)
    }
    private fun initAddress(address: AddressBean?) {
        tv_ren?.text = "收货人："+address?.name
        tv_phone?.text = address?.mobile
        tv_address_info?.text = address?.addressInfo
    }


    private fun initPrice(mbean: OrderBean) {
        var productPrice = 0.toDouble()
        var PostPrice = 0.toDouble()
        var WoodPrice = 0.toDouble()
        var ServicetPrice = 0.toDouble()

        if (mbean != null) {
            for (it in mbean!!.products!!) {
                var temCube = 0f
                for (item in it.specificationCartVos!!){
                    temCube = temCube + item.cube * item.count
                    productPrice = productPrice + item.price * item.count
                }
                if (it.shortFreight != null){
                    if (temCube <= it.shortFreight!!.cubeThreshold){
                        PostPrice += it.shortFreight!!.startPrice
                    }else{
                        PostPrice += temCube*it.shortFreight!!.unitPrice
                    }
                }else{
                    PostPrice += 0.0
                }
                WoodPrice = WoodPrice + temCube * it.woodenPriceFactor
            }
        }

        ServicetPrice = (mbean!!.serviceFeeFactor * productPrice)

        tv_fuwu_price?.text = ServicetPrice.Fromate()
        tv_sum_price?.text = productPrice.Fromate()
        tv_mujia_price?.text = WoodPrice.Fromate()
        tv_duan_price?.text = PostPrice.Fromate()
        mTotalPrice = ServicetPrice + WoodPrice + PostPrice + productPrice
        if (AccountHelper.getUser().type == 3){
            mTotalPrice = ServicetPrice + WoodPrice + PostPrice + productPrice
        }else{
            mTotalPrice = WoodPrice + PostPrice + productPrice
            tv_fuwu_price?.text = (0.00).Fromate()
        }

        tv_price22?.text = "应付金额: "+mTotalPrice?.Fromate()

//        holder.view.order_total.text = .toString()
    }

    private fun alipay(data: PayBean) {
        AccountHelper.synchronizationShopCar()
        if (AccountHelper.getUser().type == 2 && AccountHelper.getUser().infoData?.designData?.parentType != 4){
            if (!TextUtils.isEmpty(data.orderNoList!![0])){
                val intent = Intent(this, PayResult::class.java)
                intent.putExtra("payresult", true)
                intent.putExtra("companyName",data.companyName)
                intent.putExtra("orderNo",data.orderNoList!![0])
                intent.putExtra("money",mTotalPrice)
                startActivity(intent)
                finish()
                return
            }
        }

        if (TextUtils.isEmpty(data.parameterInfo)){
            return
        }

        Flowable.fromCallable {
            val alipay = PayTask(this)
            return@fromCallable alipay.payV2(data.parameterInfo, true)
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
