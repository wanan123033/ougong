package com.ougong.shop.activity.checkin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.baigui.commonlib.utils.ToastUtils
import com.ougong.shop.MsgContract
import com.ougong.shop.R
import com.ougong.shop.activity.orderpay.QuickOrderActivity
import com.ougong.shop.adapter.MyProfileCategoryAdapter
import com.ougong.shop.utils.MessageBus
import com.ougong.shop.utils.MessageBusMessage
import com.ougong.shop.utils.Subscrition
import kotlinx.android.synthetic.main.dialog_my_profile.*
import org.json.JSONArray
import org.json.JSONObject

class MyProfileDialog : DialogFragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_my_profile,container,false)
    }

    override fun onStart() {
        super.onStart()
        dialog.window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
        dialog.window.setBackgroundDrawable(null)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        MessageBus.getBus().register(this)
        initDialog()
    }

    private fun initDialog() {
        iv_close.setOnClickListener {
            dismiss()
        }
        view_dimission.setOnClickListener {
            dismiss()
        }

        rv_list.layoutManager = LinearLayoutManager(context)
        var array:JSONArray? = CheckLinUtils.getInstance().myProfile
        if (array != null) {
            rv_list.adapter = MyProfileAdapter(context!!, array!!)
            rv_list?.setHasFixedSize(true)
            rv_list?.setNestedScrollingEnabled(false)
            initPrice(array)
        }
        tv_commit.setOnClickListener(this)
        if (array == null || array?.length()!! <= 0){
            ToastUtils.toast(context,"配置单为空！")
            dismiss()
        }
    }

    private fun initPrice(array: JSONArray?) {
        tv_price22.text = "全部房间：¥" + totalPrice(array!!)
    }

    override fun onClick(v: View?) {
        val intent = Intent(context,QuickOrderActivity::class.java)
        intent.putExtra(QuickOrderActivity.ORDER_TYPE,1)
        intent.putExtra(QuickOrderActivity.ORDER_STR_CONTENT,getBuyString())
        intent.putExtra(QuickOrderActivity.ORDER_DATA,getBuyString())
        startActivity(intent)
    }

    private fun getBuyString(): String? {
        val sb = StringBuffer()
        val myprofile = CheckLinUtils.getInstance().myProfile
        Log.e("TAG=====",myprofile.toString())
        for (roomIndex in 0..(myprofile.length() - 1)){
            val room = myprofile.getJSONObject(roomIndex)
            val categoryJsonArr = room.getJSONArray("category")
            for (cateIndex in 0..(categoryJsonArr.length() - 1)){
                val productsJson = categoryJsonArr.getJSONObject(cateIndex).getJSONArray("products")
                for (proIndex in 0..(productsJson.length() - 1)){
                    val specID = productsJson.getJSONObject(proIndex).getInt("specId")
                    val count = productsJson.getJSONObject(proIndex).getInt("count")
                    if (sb.isEmpty()){
                        sb.append(""+specID+"-"+count)
                    }else{
                        sb.append(","+specID+"-"+count)
                    }
                }
            }
        }
        return sb.toString()
    }

    private fun totalPrice(array: JSONArray?): String {
        var price = 0.0
        for (roomNum in 0..(array?.length()!! - 1)){
            var jsonArray = array.getJSONObject(roomNum).getJSONArray("category")
            for (categoryNum in 0..(jsonArray!!.length() - 1)){
                var categoryObj = jsonArray.getJSONObject(categoryNum)
                var productArr = categoryObj.getJSONArray("products")
                for (productNum in 0..(productArr.length() - 1)){
                    price+=productArr.getJSONObject(productNum).getDouble("price")
                }
            }
        }
        return String.format("%.2f",price)
    }

    class MyProfileAdapter(var context:Context,var profile:JSONArray?) : RecyclerView.Adapter<MyProfileViewHolder>(){

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyProfileViewHolder {
            return MyProfileViewHolder(LayoutInflater.from(context).inflate(R.layout.item_my_profile,p0,false))
        }

        override fun getItemCount(): Int {
            return profile?.length()!!
        }

        override fun onBindViewHolder(holder: MyProfileViewHolder, p1: Int) {
            holder.initData(profile?.getJSONObject(p1)!!)
        }

        fun setData(myProfile: JSONArray) {
            this.profile = myProfile
            notifyDataSetChanged()
        }

    }
    class MyProfileViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
        var tv_roomType:TextView? = null
        var tv_yixuan:TextView? = null
        var tv_price:TextView? = null
        var rv_list:RecyclerView? = null
        var rl_dd:RelativeLayout? = null
        init {
            tv_roomType = itemView.findViewById(R.id.tv_roomType)
            tv_yixuan = itemView.findViewById(R.id.tv_yixuan)
            tv_price = itemView.findViewById(R.id.tv_price)
            rv_list = itemView.findViewById(R.id.rv_list)
            rl_dd = itemView.findViewById(R.id.rl_dd)
            rv_list?.layoutManager = LinearLayoutManager(itemView.context)
            rv_list?.adapter = MyProfileCategoryAdapter(itemView.context)
            itemView.findViewById<TextView>(R.id.tv_num_dd).text = "已选(件)"
            itemView.findViewById<TextView>(R.id.tv_price_dd).text = "共计价格(元)"
        }
        fun initData(room: JSONObject) {
            tv_roomType?.text = room.getString("roomName")
            tv_price?.text = price(room.getJSONArray("category")!!)
            tv_yixuan?.text = roomNum(room.getJSONArray("category")!!)
            rv_list?.setHasFixedSize(true)
            rv_list?.setNestedScrollingEnabled(false)
            (rv_list?.adapter as MyProfileCategoryAdapter).setData(room.getJSONArray("category")!!)
            rl_dd?.setOnClickListener {
                if (rv_list?.visibility == View.VISIBLE){
                    rv_list?.visibility = View.GONE
                }else{
                    rv_list?.visibility = View.VISIBLE
                }
            }
        }

        private fun roomNum(jsonArray: JSONArray): String {
            var sum = 0
            for (categoryNum in 0..(jsonArray!!.length() - 1)){
                var categoryObj = jsonArray.getJSONObject(categoryNum)
                var productArr = categoryObj.getJSONArray("products")
                for (productNum in 0..(productArr.length() - 1)){
                    sum+=productArr.getJSONObject(productNum).getInt("count")
                }
            }
            return ""+sum
        }

        fun price(jsonArray: JSONArray): String {
            var price = 0.0
            for (categoryNum in 0..(jsonArray!!.length() - 1)){
                var categoryObj = jsonArray.getJSONObject(categoryNum)
                var productArr = categoryObj.getJSONArray("products")
                for (productNum in 0..(productArr.length() - 1)){
                    price+=productArr.getJSONObject(productNum).getDouble("price")
                }
            }
            return String.format("%.2f",price)
        }

    }

    @Subscrition(action = MsgContract.CHECKLIN_PRODUCT_JIA,threadMode = Subscrition.ThreadMode.MAIN)
    fun onEventMessage(msg:MessageBusMessage){
        val product = msg.data as JSONObject
        val specId = product.getInt("specId")
        val roomName = product.getString("roomName")
        var categoryName = product.getString("categoryName")

        CheckLinUtils.getInstance().productAdd(specId,roomName,categoryName)
        (rv_list.adapter as MyProfileAdapter).setData(CheckLinUtils.getInstance().myProfile!!)
        MessageBus.getBus().post(MessageBusMessage(MsgContract.RESH_CHECKLIN_ROOM_NUM_PRICE))
        initPrice(CheckLinUtils.getInstance().myProfile!!)

    }
    @Subscrition(action = MsgContract.CHECKLIN_PRODUCT_JIAN,threadMode = Subscrition.ThreadMode.MAIN)
    fun onEventMessage2(msg:MessageBusMessage){
        val product = msg.data as JSONObject
        val specId = product.getInt("specId")
        val roomName = product.getString("roomName")
        var categoryName = product.getString("categoryName")

        CheckLinUtils.getInstance().productReduce(specId,roomName,categoryName)
        (rv_list.adapter as MyProfileAdapter).setData(CheckLinUtils.getInstance().myProfile!!)
        initPrice(CheckLinUtils.getInstance().myProfile!!)
        MessageBus.getBus().post(MessageBusMessage(MsgContract.RESH_CHECKLIN_ROOM_NUM_PRICE))
    }

    @Subscrition(action = MsgContract.RESH_PROFILE_DATA,threadMode = Subscrition.ThreadMode.MAIN)
    fun onEventMessage3(msg:MessageBusMessage){
        (rv_list.adapter as MyProfileAdapter).setData(CheckLinUtils.getInstance().myProfile!!)
        initPrice(CheckLinUtils.getInstance().myProfile!!)
    }
}