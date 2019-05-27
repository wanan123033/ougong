package com.ougong.shop.V1

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ougong.shop.R

class HomepageContentAdapter : RecyclerView.Adapter<HomepageContentAdapter.contentHolder> {

    private val mContext: Context

    private val mHomehopspot: List<ProdectBean>?

    private var inflater: LayoutInflater


    constructor(mContext: Context, mHomeCategory: List<ProdectBean>?) {
        this.mContext = mContext
        this.mHomehopspot = mHomeCategory
        inflater = LayoutInflater.from(mContext)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): contentHolder {
        return contentHolder(inflater.inflate(R.layout.homepage_content_item, parent, false))
    }

    override fun onBindViewHolder(holder: contentHolder, position: Int) {

//        holder.view.home_read_piv_iv!!.setImageResource(R.drawable.)
//        holder.view.home_read_title!!.text = "#" + contentBean.getTitle()
    }

    override fun getItemCount(): Int {
        return 300
    }

    inner class contentHolder(var view: View) : RecyclerView.ViewHolder(view) {

    }


}
