package com.ougong.shop.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.baigui.commonlib.utils.ScreenUtils
import com.bumptech.glide.Glide
import com.ougong.shop.App
import com.ougong.shop.Bean.CateryBean
import com.ougong.shop.R
import com.ougong.shop.activity.ProductList.bottom.ProductBotomList
import kotlinx.android.synthetic.main.content_sub_item.view.*
import kotlinx.android.synthetic.main.title_group_item.view.*

class ShowMoreAdapter(private val mContext: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var isShowMore = false


    private var mBean: CateryBean? = null
    fun setData(bean: CateryBean) {
        mBean = bean
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {

        if (mBean == null){
            return 0
        }
        return if (isShowMore) {
                mBean!!.child.size + 1
        } else{
                1
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, p1: Int) {

        when (holder) {
            is GroupItemViewHolder -> {
//                holder.view.text.setText(mBean!!.name)
                Glide.with(App.app!!)
                    .load(mBean!!.appImage)
                    .error(Glide.with(mContext).load(R.drawable.catery_title_default))
                    .into(holder.view.image1)
                holder.view.setOnClickListener {
                    isShowMore = !isShowMore
                    notifyDataSetChanged()
                }

            }
            is SubItemViewHolder -> {

                holder.view.name.setText(mBean!!.child!![p1-1].name)
                Glide.with(App.app!!)
                    .load(mBean!!.child!![p1-1].appImage)
                    .error(Glide.with(mContext).load(R.drawable.catery_child_default))
                    .into(holder.view.image)
                holder.view.setOnClickListener {

                    val intent = Intent(mContext, ProductBotomList::class.java)
                    intent.putExtra("data",mBean!!.child!![p1-1])
                    mContext.startActivity(intent)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0)
            0
        else {
            1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        val view: View
        var viewHolder: RecyclerView.ViewHolder
        if (p1 == 0) {
            // parent需要传入对应的位置，否则列表项触发不了点击事件
            view = LayoutInflater.from(mContext).inflate(R.layout.title_group_item, parent, false)
            val params = view.getLayoutParams() as StaggeredGridLayoutManager.LayoutParams
            params.isFullSpan = true//最为重要的一个方法，占满全屏,以下同理
            params.topMargin = ScreenUtils.dipTopx(mContext,16f)
            params.bottomMargin = ScreenUtils.dipTopx(mContext,8f)
            view.setLayoutParams(params)
            viewHolder = GroupItemViewHolder(view)
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.content_sub_item, parent, false)
            viewHolder = SubItemViewHolder(view)
        }
        return viewHolder
    }


}

class GroupItemViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

}

class SubItemViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

}
