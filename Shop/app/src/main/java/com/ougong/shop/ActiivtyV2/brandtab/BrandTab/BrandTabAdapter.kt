package com.ougong.shop.ActiivtyV2.brandtab.BrandTab

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.ougong.shop.ActiivtyV2.bean.BrandBeanCollection
import com.ougong.shop.ActiivtyV2.brandtab.BrandActivity.BrandActivity
import com.ougong.shop.App
import com.ougong.shop.Bean.ProductListItem
import com.ougong.shop.ConstString
import com.ougong.shop.R
import com.ougong.shop.activity.Product.ProductActivity
import kotlinx.android.synthetic.main.brand_item.view.*

class BrandTabAdapter(var context: Context, private val mList: List<BrandBeanCollection.BrandItemBean>) :
    RecyclerView.Adapter<myViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): myViewHolder {
        return myViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.brand_item, parent, false))
    }

    override fun getItemCount() = mList.size

    override fun onBindViewHolder(holder: myViewHolder, p1: Int) {

        Glide.with(App.app!!)
            .load(mList[p1].preview)
            .error(Glide.with(App.app!!).load(R.drawable.catery_child_default))
            .into(holder.view.brand_item_image)

        Glide.with(App.app!!)
            .load(mList[p1].logo)
            .error(Glide.with(App.app!!).load(R.drawable.catery_child_default))
            .into(holder.view.brand_item_ice)

        holder.view.setOnClickListener {
            val intent = Intent(context, BrandActivity::class.java)
            intent.putExtra(ConstString.PRODUCT_ITEM,mList[p1].id)
            intent.putExtra(ConstString.PRODUCT_ITEM_NAME,mList[p1].name)
            context.startActivity(intent)
        }
    }
//
//    fun setData() {
//
//    }

}

class myViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

}
