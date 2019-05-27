package com.ougong.shop.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.baoyz.swipemenulistview.SwipeMenuItem
import com.baoyz.swipemenulistview.SwipeMenuListView
import com.ougong.shop.R
import org.json.JSONArray
import org.json.JSONObject


class MyProfileCategoryAdapter(var context: Context?) : RecyclerView.Adapter<MyProfileCategoryAdapter.MyProfileCategoryViewHolder>() {
    var jsonArray:JSONArray? = null
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyProfileCategoryViewHolder {
        return MyProfileCategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.item_profile_category,p0,false))
    }

    override fun getItemCount(): Int {
        if (jsonArray == null){
            return 0
        }
        return jsonArray!!.length()
    }

    override fun onBindViewHolder(holder: MyProfileCategoryViewHolder, p1: Int) {
        holder.initData(jsonArray!!.getJSONObject(p1))
    }

    fun setData(jsonArray: JSONArray) {
        this.jsonArray = jsonArray
        notifyDataSetChanged()
    }

    class MyProfileCategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var tv_cateName:TextView? = null
        var rv_product: RecyclerView? = null
        init {
            tv_cateName = itemView.findViewById(R.id.tv_cateName)
            rv_product = itemView.findViewById(R.id.listView)
            rv_product?.layoutManager = LinearLayoutManager(itemView.context)
            rv_product?.adapter = MyProfileProductAdapter(itemView.context)

        }
        fun initData(jsonObject: JSONObject) {
            tv_cateName?.text = jsonObject.getString("categaryName")
            (rv_product?.adapter as MyProfileProductAdapter).setData(jsonObject.getJSONArray("products"))
        }
    }
}
