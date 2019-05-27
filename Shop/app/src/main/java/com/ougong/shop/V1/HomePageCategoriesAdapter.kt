package com.ougong.shop.V1

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.baigui.commonview.widget.ImageWithTextView
import com.ougong.shop.R

class HomePageCategoriesAdapter : RecyclerView.Adapter<HomePageCategoriesAdapter.TypetypeHolder> {

    private val mContext: Context?


    private var inflater: LayoutInflater


    constructor(mContext: Context) {
        this.mContext = mContext
        inflater = LayoutInflater.from(mContext)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypetypeHolder {
        return TypetypeHolder(ImageWithTextView(mContext))

    }

    override fun onBindViewHolder(holder: TypetypeHolder, position: Int) {
        holder.view.setTvImagetext("推荐")
        holder.view.setIvImagetext(R.drawable.homepage_recommend)
    }

    override fun getItemCount(): Int {
        return 4
    }

    //中间的四个type
    inner class TypetypeHolder(val view: ImageWithTextView) : RecyclerView.ViewHolder(view) {

    }
}