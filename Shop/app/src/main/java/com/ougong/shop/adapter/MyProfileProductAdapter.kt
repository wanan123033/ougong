package com.ougong.shop.adapter

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
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
import com.ougong.shop.utils.MessageBus
import com.ougong.shop.utils.MessageBusMessage
import org.json.JSONArray
import org.json.JSONObject

class MyProfileProductAdapter(var context: Context?) : RecyclerView.Adapter<MyProfileProductAdapter.MyProfileProductHolder>(){

    var jsonArray:JSONArray? = null
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyProfileProductHolder {
        return MyProfileProductHolder(LayoutInflater.from(context).inflate(R.layout.item_product_profile,p0,false))
    }

    override fun getItemCount(): Int {
        if (jsonArray == null){
            return 0
        }
        return jsonArray!!.length()
    }

    override fun onBindViewHolder(holder: MyProfileProductHolder, p1: Int) {
        holder.initData(jsonArray!!.getJSONObject(p1))
//        holder.iv_jian?.setOnClickListener{
//            MessageBus.getBus().post(MessageBusMessage(MsgContract.CHECKLIN_PRODUCT_JIAN,jsonArray!!.getJSONObject(p1)))
//        }
//        holder.iv_jia?.setOnClickListener{
//            MessageBus.getBus().post(MessageBusMessage(MsgContract.CHECKLIN_PRODUCT_JIA,jsonArray!!.getJSONObject(p1)))
//        }
//        holder.tv_delete?.setOnClickListener {
//
//        }
    }

    fun setData(jsonArray: JSONArray?) {
        this.jsonArray = jsonArray
        notifyDataSetChanged()
    }

    class MyProfileProductHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var rv_item:RecyclerView? = null
        init {

            rv_item = itemView.findViewById(R.id.rv_item)
        }
        fun initData(jsonObject: JSONObject) {
            rv_item?.layoutManager = LinearLayoutManager(itemView.context,LinearLayoutManager.HORIZONTAL,true)
            rv_item?.adapter = MyProfileProductItemAdapter(jsonObject,itemView.context)

        }
    }

}
