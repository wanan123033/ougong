package com.ougong.shop.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.baigui.commonlib.kotlinUtils.extensions.Fromate
import com.bumptech.glide.Glide
import com.ougong.shop.App
import com.ougong.shop.R
import com.ougong.shop.httpmodule.CheckLinFanganDatailBean

class MyRoomDatailAdapter(var context: Context) :RecyclerView.Adapter<MyRoomDatailAdapter.RoomDatailHolder>(){
    var products: Array<CheckLinFanganDatailBean.ProductBean>? = null
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RoomDatailHolder {
        return RoomDatailHolder(LayoutInflater.from(context).inflate(R.layout.item_my_room_datail,p0,false))
    }

    override fun getItemCount(): Int {
        if (products == null){
            return 0
        }
        return products!!.size
    }

    override fun onBindViewHolder(holder: RoomDatailHolder, p1: Int) {
        holder.initProduct(products!![p1])
    }

    fun setData(products: Array<CheckLinFanganDatailBean.ProductBean>) {
        this.products = products
        notifyDataSetChanged()
    }

    class RoomDatailHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        var iv_img:ImageView? = null
        var tv_name:TextView? = null
        var tv_spec:TextView? = null
        var tv_color:TextView? = null
        var tv_sum:TextView? = null
        var tv_price:TextView? = null
        init {
            iv_img = itemView.findViewById(R.id.iv_img)
            tv_name = itemView.findViewById(R.id.tv_name)
            tv_spec = itemView.findViewById(R.id.tv_spec)
            tv_color = itemView.findViewById(R.id.tv_color)
            tv_sum = itemView.findViewById(R.id.tv_sum)
            tv_price = itemView.findViewById(R.id.tv_price)
        }

        fun initProduct(productBean: CheckLinFanganDatailBean.ProductBean) {
            Glide.with(App.app!!).load(productBean.headImage).into(iv_img!!)
            tv_name?.text = productBean.title
            tv_spec?.text = productBean.color
            tv_color?.text = productBean.spec
            tv_sum?.text = "X"+productBean.count
            tv_price?.text = productBean.price.Fromate()
        }
    }
}