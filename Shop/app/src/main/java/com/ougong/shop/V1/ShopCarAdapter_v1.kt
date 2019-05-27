package com.ougong.shop.activity.MainShopCarFragment

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ougong.shop.Bean.FuckData
import com.ougong.shop.R

class ShopCarAdapter_v1(private val mContext: Context?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var inflater: LayoutInflater?

    init {
        inflater = LayoutInflater.from(mContext)
    }

    var mBean : FuckData? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            BRAND_TITLE -> {
                val viewtop = inflater!!.inflate(R.layout.include_shopcar_brand, parent, false)
                val params = viewtop.getLayoutParams() as StaggeredGridLayoutManager.LayoutParams
                params.isFullSpan = true//最为重要的一个方法，占满全屏,以下同理
                params.topMargin = 40
                viewtop.setLayoutParams(params)
                brandTitleHolder(viewtop)
            }

            SHOPCAR_ITEM -> {
                val viewtop = inflater!!.inflate(R.layout.include_shopcar_item, parent, false)
                val params = viewtop.getLayoutParams() as StaggeredGridLayoutManager.LayoutParams
                params.isFullSpan = true//最为重要的一个方法，占满全屏,以下同理
                params.topMargin = 40
                viewtop.setLayoutParams(params)
                brandTitleHolder(viewtop)
            }

            SHOPCAR_ITEM -> {
                val viewtop = inflater!!.inflate(R.layout.include_shopcar_item, parent, false)
                val params = viewtop.getLayoutParams() as StaggeredGridLayoutManager.LayoutParams
                params.isFullSpan = true//最为重要的一个方法，占满全屏,以下同理
                params.topMargin = 40
                viewtop.setLayoutParams(params)
                brandTitleHolder(viewtop)
            }
            RECOMAND_TITLE ->{
                val viewtop = inflater!!.inflate(R.layout.include_recomand_title, parent, false)
                val params = viewtop.getLayoutParams() as StaggeredGridLayoutManager.LayoutParams
                params.isFullSpan = true//最为重要的一个方法，占满全屏,以下同理
                params.topMargin = 40
                viewtop.setLayoutParams(params)
                brandTitleHolder(viewtop)
            }

            RECOMAND_ITEM->{

                RecomandItemHolder(inflater!!.inflate(R.layout.include_shopcar_recomand, parent, false))
            }

            else->{
                val viewtop = inflater!!.inflate(R.layout.include_shopcar_brand, parent, false)
                val params = viewtop.getLayoutParams() as StaggeredGridLayoutManager.LayoutParams
                params.isFullSpan = true//最为重要的一个方法，占满全屏,以下同理
                params.topMargin = 40
                viewtop.setLayoutParams(params)
                brandTitleHolder(viewtop)
            }

        }
    }

    override fun getItemCount() = 10

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
    }

    private val BRAND_TITLE = 1

    private val THE_LAST = 0

    private val SHOPCAR_ITEM = 2

    private val RECOMAND_TITLE = 3

    private val RECOMAND_ITEM = 4

    override fun getItemViewType(position: Int): Int {
       return when(position){
            0-> BRAND_TITLE
           1,2,3-> SHOPCAR_ITEM
           4-> RECOMAND_TITLE
           else-> RECOMAND_ITEM
        }
    }

    inner class brandTitleHolder(view: View) : RecyclerView.ViewHolder(view)
    inner class ShopItemHolder(view: View) : RecyclerView.ViewHolder(view)
    inner class RecomandTitleHolder(view: View) : RecyclerView.ViewHolder(view)
    inner class RecomandItemHolder(view: View) : RecyclerView.ViewHolder(view)
}