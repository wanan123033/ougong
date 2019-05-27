package com.ougong.shop.activity.mainHomepage.Search

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.baigui.commonlib.kotlinUtils.extensions.Fromate
import com.bumptech.glide.Glide
import com.ougong.shop.App
import com.ougong.shop.Bean.ProductListItem
import com.ougong.shop.ConstString
import com.ougong.shop.R
import com.ougong.shop.activity.Product.ProductActivity
import kotlinx.android.synthetic.main.product_item.view.*
import kotlinx.android.synthetic.main.product_list_item.view.*

class SearchAdapter(var context : Context, private val mList: List<ProductListItem>) : RecyclerView.Adapter<myViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): myViewHolder {
        return myViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_item, parent, false))
    }

    override fun getItemCount() = mList.size

    override fun onBindViewHolder(holder: myViewHolder, p1: Int) {

        Glide.with(App.app!!)
            .load(mList[p1].headImage)
            .error(Glide.with(App.app!!).load(R.drawable.catery_child_default))
            .into(holder.view.ice)

        holder.view.title.setText(mList[p1].title)

        holder.view.price.setText(mList[p1].showPrice?.Fromate())

        holder.view.setOnClickListener {
            val intent = Intent(context, ProductActivity::class.java)
            intent.putExtra(ConstString.PRODUCT_ITEM,mList[p1])
            context.startActivity(intent)        }
    }

}

class myViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

}
