package com.ougong.shop.ActiivtyV2.B_clien.decoration_factory.mydesinger

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.ougong.shop.ActiivtyV2.B_clien.cheng_yun_shang.myMoney.myViewHolder
import com.ougong.shop.ActiivtyV2.bean.DesingerBean
import com.ougong.shop.App
import com.ougong.shop.R
import kotlinx.android.synthetic.main.desinger_item.view.*

class MyDesingerAdapter(var context: Context, private val mList: List<DesingerBean>) :
    RecyclerView.Adapter<myViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): myViewHolder {
        return myViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.desinger_item, parent, false))
    }

    override fun getItemCount() = mList.size

    override fun onBindViewHolder(holder: myViewHolder, p1: Int) {

        Glide.with(App.app!!)
            .load(mList[p1].avatar)
            .error(Glide.with(App.app!!).load(R.drawable.ice_default))
            .into(holder.view.profile_image)

        holder.view.name.setText(mList[p1].name)

        holder.view.phone.setText(mList[p1].phone)

        mList[p1].time?.let {
            holder.view.time.setText(it + "提交")
        }
        mList[p1].enable?.let {
            if (mList[p1].enable!!) {

                holder.view.status.text = "已通过"
            }else{

                holder.view.status.text = "未通过"
            }
        }
    }

}

class myViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

}
