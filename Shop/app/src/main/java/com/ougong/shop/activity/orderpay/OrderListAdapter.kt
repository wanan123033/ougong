package com.ougong.shop.activity.orderpay

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
import com.ougong.shop.Bean.SpecificationCartVos
import com.ougong.shop.R

class OrderListAdapter(var context : Context) : RecyclerView.Adapter<OrderListAdapter.OrderListHolder>() {
    var rows:List<SpecificationCartVos>? = arrayListOf()


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): OrderListHolder {
        return OrderListHolder(LayoutInflater.from(context).inflate(R.layout.item_room_datail_item,p0,false))
    }

    override fun getItemCount(): Int {
        return rows!!.size
    }

    override fun onBindViewHolder(holder: OrderListHolder, p1: Int) {
        holder.setData(rows!!.get(p1),p1)
    }

    fun setData(rows: List<SpecificationCartVos>?) {
        this.rows = rows
        notifyDataSetChanged()
    }

    class OrderListHolder(itemView:View):RecyclerView.ViewHolder(itemView){
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
            itemView.findViewById<View>(R.id.tv_pos).visibility = View.GONE
        }
        fun setData(rows: SpecificationCartVos, p1: Int) {
            if (rows.images!!.size >0){
                Glide.with(App.app!!).load(rows.images!![0]).into(iv_img!!)
            }else{
                Glide.with(App.app!!).load(rows.product!!.headImage).into(iv_img!!)
            }

            tv_name?.text = rows.title
            tv_spec?.text = rows.spec
            tv_color?.text = rows.color
            tv_sum?.text = "X"+rows.count
            tv_price?.text = rows.price.Fromate()
        }

    }
}
