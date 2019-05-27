package com.ougong.shop.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.baigui.netlib.BeanBase
import com.ougong.shop.Bean.CateryBean
import com.ougong.shop.R
import kotlinx.android.synthetic.main.homepage_recycleview.view.*

class LabelContentAdapter(private val mContext: Context?) : RecyclerView.Adapter<LabelContentAdapter.ViewHolder>() {

    private var inflater: LayoutInflater?

    private var mBean : CateryBean? = null

    fun setData(cateryBean: CateryBean){
        mBean = cateryBean
//        mAdapter.setData(mBean!!.child)
        notifyDataSetChanged()
    }
    init {
        inflater = LayoutInflater.from(mContext)
    }
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int):ViewHolder {
        val view = inflater!!.inflate(R.layout.homepage_recycleview, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = if (mBean == null) 0 else mBean!!.child.size

    override fun onBindViewHolder(holder: ViewHolder, p1: Int) {
        var adapter = ShowMoreAdapter(mContext!!)
        //bean 不为空
        if (mBean == null){
            return
        }
        adapter.setData(mBean!!.child[p1])
        holder.view.homepage_recyclerView_horizontal.adapter = adapter
        holder.view.homepage_recyclerView_horizontal.setLayoutManager(StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL))
        //holder.view.homepage_recyclerView_horizontal.setLayoutManager(LinearLayoutManager(mContext))
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}