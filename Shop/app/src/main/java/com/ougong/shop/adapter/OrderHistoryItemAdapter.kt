package com.ougong.shop.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.ougong.shop.App
import com.ougong.shop.R
import com.ougong.shop.httpmodule.OrderHistoryJavaBean
import com.squareup.picasso.Picasso

class OrderHistoryItemAdapter(var context: Context?,var orderInfoList: Array<OrderHistoryJavaBean.OrderInfo>) :
    RecyclerView.Adapter<OrderHistoryItemAdapter.OrderHistoryItemHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int) = OrderHistoryItemHolder(LayoutInflater.from(context)
        .inflate(R.layout.item_order_history_item,p0,false))

    override fun getItemCount() = orderInfoList.size

    override fun onBindViewHolder(holder: OrderHistoryItemHolder, p1: Int) {
        holder.initData(orderInfoList[p1].specSnap!!)
    }

    class OrderHistoryItemHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        var tv_name:TextView? = null
        var iv_img:ImageView? = null
        var tv_spec:TextView? = null
        var tv_color:TextView? = null
        var tv_price:TextView? = null
        var tv_sum:TextView? = null
        init {
            tv_name = itemView.findViewById(R.id.tv_name)
            iv_img = itemView.findViewById(R.id.iv_img)
            tv_spec = itemView.findViewById(R.id.tv_spec)
            tv_color = itemView.findViewById(R.id.tv_color)
            tv_price = itemView.findViewById(R.id.tv_price)
            tv_sum = itemView.findViewById(R.id.tv_sum)
        }
        fun initData(orderInfo: OrderHistoryJavaBean.SpecSnap) {
            tv_name?.text = orderInfo.product?.title
            Picasso.get().load(orderInfo.product?.headImage).into(iv_img)
            tv_spec?.text = orderInfo.spec
            tv_color?.text = orderInfo.color
            tv_price?.text = "￥"+orderInfo.price
            tv_sum?.text = "X"+orderInfo.count
        }
    }

}
