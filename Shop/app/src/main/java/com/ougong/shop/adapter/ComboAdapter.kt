package com.ougong.shop.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.ougong.shop.App
import com.ougong.shop.R
import com.ougong.shop.activity.combo.ComboDatailActivity

import com.ougong.shop.httpmodule.ComboBean

class ComboAdapter(
    var context: Context,
    var bean: ArrayList<ComboBean.DataBean>,
    var isfooter: Boolean? = false,
    var isGuding: Boolean? = false
) :RecyclerView.Adapter<ComboAdapter.ComboViewHolder>(){
    var inflater: LayoutInflater?
    init {
        inflater = LayoutInflater.from(context)
    }
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ComboViewHolder {
        val view = inflater!!.inflate(R.layout.item_combo, p0, false)
        return  ComboViewHolder(view,isGuding)
    }

    override fun getItemCount(): Int {
        if (bean == null){
            return 0
        }
        return bean.size
    }

    override fun onBindViewHolder(p0: ComboViewHolder, p1: Int) {
        p0.setData(bean.get(p1),isfooter!!,getItemCount())
    }

    fun addData(bean: ArrayList<ComboBean.DataBean>) {
        if (this.bean != null){
            this.bean.addAll(bean)
        }
        notifyDataSetChanged()
    }

    fun setFooter(b: Boolean) {
        this.isfooter = b
        notifyDataSetChanged()
    }

    fun clearData() {
        this.bean.clear()
        notifyDataSetChanged()
    }

    class ComboViewHolder(itemView: View, var isGuding: Boolean?) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var mData: ComboBean.DataBean? = null
        var iv_img:ImageView? = null
        var tv_name:TextView? = null
        var tv_price:TextView? = null
        var ll_footer_view:LinearLayout? = null
        init {
            iv_img = itemView.findViewById(R.id.iv_img)
            tv_name = itemView.findViewById(R.id.tv_name)
            tv_price = itemView.findViewById(R.id.tv_price)
            ll_footer_view = itemView.findViewById(R.id.ll_footer_view)
        }
        fun setData(
            data: ComboBean.DataBean,
            footer: Boolean,
            position: Int) {
            this.mData = data
            Glide.with(App.app!!).load(data.headImage).into(iv_img!!)
            tv_name!!.text = data.name
            tv_price!!.text = "￥"+data.minPrice

            if (footer && adapterPosition == position){
                ll_footer_view?.visibility = View.VISIBLE
            }else{
                ll_footer_view?.visibility = View.GONE
            }
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            val intent = Intent(itemView.context, ComboDatailActivity::class.java)
            intent.putExtra(ComboDatailActivity.ID, mData!!.id)
            intent.putExtra(ComboDatailActivity.IS_GUDING,isGuding)
            itemView.context.startActivity(intent)

        }
    }
}