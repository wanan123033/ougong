package com.ougong.shop.adapter

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.baigui.commonlib.kotlinUtils.extensions.Fromate
import com.bumptech.glide.Glide
import com.ougong.shop.App
import com.ougong.shop.MsgContract
import com.ougong.shop.R
import com.ougong.shop.activity.checkin.CheckLinUtils
import com.ougong.shop.utils.MessageBus
import com.ougong.shop.utils.MessageBusMessage
import org.json.JSONObject

class MyProfileProductItemAdapter(var jsonObject: JSONObject, context: Context?) : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    View.OnClickListener {
    var inflater:LayoutInflater? = null
    init {
        inflater = LayoutInflater.from(context)
    }
    override fun onClick(v: View?) {
        //TODO 删除该产品
        Log.e("TAG======",jsonObject.toString())
        val cateId = jsonObject.getInt("categoryId")
        val roomId = jsonObject.getInt("roomId")
        val specId = jsonObject.getInt("specId")
        CheckLinUtils.getInstance().deleteProduct(roomId,cateId,specId)
        MessageBus.getBus().post(MessageBusMessage(MsgContract.RESH_PROFILE_DATA))
    }
    override fun getItemViewType(position: Int): Int {
        if (position == 0){
            return 0
        }else{
            return 1
        }
    }

    override fun onCreateViewHolder(p0: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1){
            return MyProfileProductItemHolder(inflater!!.inflate(R.layout.item_product_profile_item,p0,false))
        }else{
            return DeleteItemHolder(inflater!!.inflate(R.layout.item_product_delete,p0,false))
        }
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, viewType: Int) {
       when(p0){
           is MyProfileProductItemHolder ->{
               p0.initData(jsonObject)
               p0.iv_jian?.setOnClickListener{
                   MessageBus.getBus().post(MessageBusMessage(MsgContract.CHECKLIN_PRODUCT_JIAN,jsonObject))
               }
               p0.iv_jia?.setOnClickListener{
                    MessageBus.getBus().post(MessageBusMessage(MsgContract.CHECKLIN_PRODUCT_JIA,jsonObject))
               }
           }
           is DeleteItemHolder ->{
               p0.itemView.setOnClickListener(this)
           }
       }
    }

    class MyProfileProductItemHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        var iv_img: ImageView? = null
        var tv_name: TextView? = null
        var tv_spec: TextView? = null
        var tv_color: TextView? = null
        var tv_sum: TextView? = null
        var iv_jian: ImageView? = null
        var iv_jia: ImageView? = null
        var tv_delete: TextView? = null
        var tv_price: TextView? = null
        init {
            iv_img = itemView.findViewById(R.id.iv_img)
            tv_name = itemView.findViewById(R.id.tv_name)
            tv_spec = itemView.findViewById(R.id.tv_spec)
            tv_color = itemView.findViewById(R.id.tv_color)
            tv_sum = itemView.findViewById(R.id.tv_sum)
            iv_jian = itemView.findViewById(R.id.iv_jian)
            iv_jia = itemView.findViewById(R.id.iv_jia)
            tv_delete = itemView.findViewById(R.id.tv_delete)
            tv_price = itemView.findViewById(R.id.tv_price)
        }
        fun initData(jsonObject: JSONObject) {
            Glide.with(App.app!!).load(jsonObject.getString("headImage")).into(iv_img!!)
            var name = jsonObject.getString("name")
            var spec = jsonObject.getString("spec")
            if (name.length > 20){
                name = name.substring(0,20)
            }
            if (spec.length > 20){
                spec = spec.substring(0,20)
            }
            tv_name?.text = name
            tv_spec?.text = spec
            tv_color?.text = jsonObject.getString("color")
            tv_sum?.text = ""+jsonObject.getInt("count")
            tv_price?.text = jsonObject.getDouble("price").Fromate()

        }

    }

    class DeleteItemHolder(itemView: View) :RecyclerView.ViewHolder(itemView)
}
