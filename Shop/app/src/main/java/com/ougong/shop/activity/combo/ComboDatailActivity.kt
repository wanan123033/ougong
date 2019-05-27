package com.ougong.shop.activity.combo

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.util.Log
import android.view.View
import com.baigui.commonlib.kotlinUtils.D
import com.baigui.commonlib.kotlinUtils.extensions.Fromate
import com.baigui.commonlib.utils.ToastUtils
import com.google.gson.JsonObject
import com.ougong.shop.Bean.Netbean
import com.ougong.shop.MsgContract
import com.ougong.shop.R
import com.ougong.shop.activity.WebViewActivity
import com.ougong.shop.activity.orderpay.QuickOrderActivity
import com.ougong.shop.adapter.ComboDatailAdapter
import com.ougong.shop.adapter.ComboRammodAdapter
import com.ougong.shop.base_mvp.base.BaseActivity
import com.ougong.shop.httpmodule.ComboDatailBean
import com.ougong.shop.httpmodule.RecommendBean
import com.ougong.shop.httpmodule.RoomProductBean
import com.ougong.shop.utils.MessageBus
import com.ougong.shop.utils.MessageBusMessage
import com.ougong.shop.utils.Subscrition
import io.armcha.ribble.presentation.utils.extensions.onClick
import io.armcha.ribble.presentation.utils.extensions.start
import kotlinx.android.synthetic.main.activity_combo_datail.*
import kotlinx.android.synthetic.main.include_back_title.*
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.lang.StringBuilder
import java.net.URLEncoder


class ComboDatailActivity :BaseActivity<ComboDatailActivityContract.View,ComboDatailActivityContract.Presenter>(),ComboDatailActivityContract.View,
    View.OnClickListener {


    var recommendBean: Netbean<RecommendBean>? = null
    var comboDatailBean: Netbean<ComboDatailBean>? = null
    var price: Double? = null
    var num: Int? = null

    override fun reshRecommendData(bean: Netbean<RecommendBean>) {
        recommendBean = bean
        Log.d("TAG21","---------"+recommendBean!!.data.rows!!.size)
        if (recommendBean!!.data.rows!!.size > 0) {
            rl_recommed.visibility = View.VISIBLE
            xl_remmand.visibility = View.VISIBLE
            xl_remmand.adapter = ComboRammodAdapter(this, recommendBean!!.data.rows!!.asList())
        }else{
            rl_recommed.visibility = View.GONE
            xl_remmand.visibility = View.GONE
        }
    }

    override fun initComboDatail(bean: Netbean<ComboDatailBean>?) {
        comboDatailBean = bean
        val images:ArrayList<String> = arrayListOf()
        images.add(bean?.data?.headImage!!)
        for (image in bean!!.data!!.images!!){
            images.add(image)
        }
        vpi_combo_img.setImgData(images,object : View.OnClickListener{
            override fun onClick(v: View?) {
                if(!TextUtils.isEmpty(bean.data.details)) {
                    val intent = Intent(applicationContext, WebViewActivity::class.java)
                    intent.putExtra(WebViewActivity.HTML_CONTENT, bean.data.details)
                    intent.putExtra(WebViewActivity.TITLE, bean.data.name)
                    intent.putExtra(WebViewActivity.PRICE, price)
                    intent.putExtra(WebViewActivity.NUMBER, num)
                    intent.putExtra(WebViewActivity.BUYSTRING, getBuyString())
                    intent.putExtra(WebViewActivity.ORDERSTRING, getOrderString())
                    start(intent)
                }
            }

        })
        tv_name.text = bean.data.name+"  "+bean.data.styleName
        title_name.text = bean.data.name
//

        xl_combo.adapter = ComboDatailAdapter(this, comboDatailBean!!.data.rooms!!.asList(),isGuding)
        rtv_description.maxLines = 70
        rtv_description.visibility = View.VISIBLE
        if (TextUtils.isEmpty(bean.data.intro)){
            rtv_description.visibility = View.GONE
        }else {
            rtv_description.setCloseText(bean.data.intro)
        }
        initRecommendData(bean.data.recommendData)
//        xl_remmand.adapter = ComboRammodAdapter(this,bean.data.recommendData)
        tv_mianji.text = ""+bean.data.area + "m² | "+ bean.data.houseTypeName
        if (bean.data.area == 0 || TextUtils.isEmpty(bean.data.houseTypeName)){
            tv_mianji.visibility = View.GONE
        }

        //TODO 默认选中第一个 循环房间选中第一个选项下的所有商品
        for (roomIndex in 0..(comboDatailBean!!.data.rooms!!.size - 1)){
            comboDatailBean!!.data.rooms!![roomIndex].products[0].forEach {
                it.enable = true
                comboDatailBean!!.data.rooms!![roomIndex].roomPrice += it.price
                comboDatailBean!!.data.rooms!![roomIndex].roomNum += it.count
            }

        }
        xl_combo.adapter!!.notifyDataSetChanged()
        //TODO 计算价格
        initPrice()

    }
    var recommendlist:ArrayList<String> = arrayListOf()
    private fun initRecommendData(recommendData: Array<ComboDatailBean.Recommend>?) {
        val array = JSONArray()
        recommendlist.clear()
        for (num in 0 .. (recommendData!!.size - 1)){
            var recommend:ComboDatailBean.Recommend = recommendData[num]
            array.put(recommend.specId)
            recommendlist.add("" + recommend.specId + "-" + recommend.count)
        }
        Log.d("TAG44","---------")
        val body:RequestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),array.toString())
        presenter.getRecommendData(body)

    }

    companion object {
        val ID = "ID"
        val IS_GUDING = "is_guding"
    }
    override fun setContentViewSource(): Int {
        return R.layout.activity_combo_datail
    }

    override fun initPresenter() = ComboDatailPresenter()
    var isGuding:Boolean = false
    override fun initView() {
        super.initView()
        isGuding = intent.getBooleanExtra(IS_GUDING,false)
        MessageBus.getBus().register(this)
        val id = intent.getIntExtra(ID,0)
        presenter.getComboDatail(id)
        title_name.text = "套餐详情"
        title_back.visibility = View.VISIBLE
        title_back.onClick {
            finish()
        }
        xl_combo.layoutManager = LinearLayoutManager(this)
        xl_combo.setHasFixedSize(true)
        xl_combo.setNestedScrollingEnabled(false)

        xl_remmand.layoutManager = LinearLayoutManager(this)
        xl_remmand.setHasFixedSize(true)
        xl_remmand.setNestedScrollingEnabled(false)

        tv_commit.setOnClickListener(this)
    }

    @Subscrition(action = MsgContract.COMBO_SELECTED,threadMode = Subscrition.ThreadMode.MAIN)
    fun onEventMessage(msg:MessageBusMessage){
        //套餐详情选中
        val product:RoomProductBean = msg.data as RoomProductBean
        val rooms = comboDatailBean!!.data.rooms
        for (room in rooms!!){
            if (room == product.room){
                for (product1 in room.products) {
                    for (pro in product1){
                        if (pro.productId == product.product.productId){
                            for (pro in product1){
                                pro.enable = !pro.enable
                                if (pro.enable){
                                    room.roomNum += pro.count
                                    room.roomPrice += pro.price
                                }else if (!pro.enable && room.roomNum >= 0){
                                    room.roomNum -= pro.count
                                    room.roomPrice -= pro.price
                                    if (room.roomPrice <= 0){
                                        room.roomPrice = 0.0
                                    }
                                    if(room.roomNum <= 0){
                                        room.roomNum = 0
                                    }
                                }
                            }
                            break
                        }
                    }
                }
            }
        }
        xl_combo.adapter!!.notifyDataSetChanged()
        initPrice()
    }

    private fun initPrice() {
        var num = 0
        var price = 0.0
        if (comboDatailBean != null) {
            val rooms = comboDatailBean!!.data.rooms
            if (rooms != null) {
                for (room in rooms!!) {
                    num += room.roomNum
                    price += room.roomPrice
                }
            }
        }
        val recommendBeans = recommendBean
        //推荐数据
        if (recommendBeans != null) {
            for (recommendIndex in 0..(recommendBeans.data.rows!!.size - 1)){
                if(recommendBeans.data.rows!![recommendIndex].product.enable){
                    num += recommendlist[recommendIndex].split("-")[1].toInt()
                    price += recommendBeans.data.rows!![recommendIndex].price * recommendlist[recommendIndex].split("-")[1].toInt()
                }
            }
        }
        tv_price22.text = "优惠价："+price.Fromate()
        tv_commit.text = "提交订单("+num+")"
        this.price = price
        this.num = num
    }

    @Subscrition(action = MsgContract.COMBO_SELECTED_RECOMMED,threadMode = Subscrition.ThreadMode.MAIN)
    public fun onEventMessage2(msg:MessageBusMessage){
        val product = msg.data as RecommendBean.ProductBean
        //推荐商品选中
        val rows = recommendBean!!.data.rows
        for (row in rows!!){
            if (row.product.id == product.id){
                row.product.enable = !row.product.enable
                if (row.product.enable){
                    recommendBean!!.data.selectedNum += row.minCount
                    recommendBean!!.data.selectedPrice += row.price
                }else if (!row.product.enable && recommendBean!!.data.selectedNum >= 0){
                    recommendBean!!.data.selectedNum -= row.minCount
                    recommendBean!!.data.selectedPrice -= row.price
                    if (recommendBean!!.data.selectedPrice <= 0){
                        recommendBean!!.data.selectedPrice = 0.0
                    }
                }
                break
            }
        }
        tv_recomend_num.text = "已选"+ recommendBean!!.data.selectedNum + "件"
        tv_recommed_price.text = "套餐优惠"+recommendBean!!.data.selectedPrice+"元"
        xl_remmand.adapter!!.notifyDataSetChanged()
        initPrice()
    }

    override fun onClick(v: View?) {
        val intent = Intent(this, QuickOrderActivity::class.java)
        val productstr = getBuyString()
        var orderStr = URLEncoder.encode(getOrderString(), "utf-8")
        if (TextUtils.isEmpty(productstr)) {
            ToastUtils.toast(this, "请选择商品")
            return
        }
        intent.putExtra(QuickOrderActivity.ORDER_STR_CONTENT, productstr)
        intent.putExtra(QuickOrderActivity.ORDER_TYPE,2)
        intent.putExtra(QuickOrderActivity.ORDER_DATA,orderStr)
        startActivity(intent)
    }

    private fun getOrderString(): String?{
        if (comboDatailBean != null) {
            val json = JSONObject()
            json.put("packageId", comboDatailBean!!.data.id)
            val str = getProductRoomString()
            json.put("products",str)
            json.put("recommendSpecIds",getRecommed())
            return json.toString()
        }
        return null
    }

    private fun getProductRoomString(): JSONArray {
        val products = JSONArray()

        for (room in comboDatailBean!!.data.rooms!!){
            val roomStr = StringBuffer()
            for (products in room.products){
                for (product in products){
                    if (product.enable){
                        if (roomStr.isEmpty()){
                            roomStr.append("" + product.specId + "-" + product.count)
                        }else{
                            roomStr.append(","+ product.specId + "-" + product.count)
                        }
                    }
                }
            }
            products.put(roomStr.toString())
        }
        return products
    }

    private fun getBuyString(): String {
        val tem = getProductString()
        val recommendBeans = recommendBean
        //推荐数据
        if (recommendBeans != null) {
            for (recommendIndex in 0..(recommendBeans.data.rows!!.size - 1)){
                if(recommendBeans.data.rows!![recommendIndex].product.enable){
                    if (tem.isEmpty()){
                        tem.append(recommendlist.get(recommendIndex))
                    }else{
                        tem.append(","+recommendlist.get(recommendIndex))
                    }
                }
            }
        }
        return tem.toString()
    }

    private fun getProductString():StringBuilder{
        val tem = StringBuilder()
        if (comboDatailBean != null) {
            val rooms = comboDatailBean!!.data.rooms
            //套餐数据
            for (room in rooms!!) {
                for (product1 in room.products) {
                    for (pro in product1) {
                            if (pro.enable) {
                                if (tem.isEmpty()) {
                                    tem.append(pro.specId.toString() + "-" + pro.count)
                                } else {
                                    tem.append("," + pro.specId.toString() + "-" + pro.count)
                                }
                            }
                    }
                }
            }
        }
        return tem
    }

    private fun getRecommed():StringBuilder{
        val tem = StringBuilder()
        //推荐数据
        if (recommendBean != null) {
            val rows = recommendBean!!.data.rows
            for (row in rows!!) {
                if (row.product.enable) {
                    if (tem.isEmpty()) {
                        tem.append(row.product.id.toString() + "-" + row.minCount)
                    } else {
                        tem.append("," + row.product.id.toString() + "-" + row.minCount)
                    }
                }
            }
        }
        return tem
    }
}