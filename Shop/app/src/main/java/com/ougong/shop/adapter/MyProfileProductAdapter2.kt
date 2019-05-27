package com.ougong.shop.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.ougong.shop.App
import com.ougong.shop.R
import org.json.JSONArray
import org.json.JSONObject

class MyProfileProductAdapter2(var context: Context?) : BaseAdapter() {
    var jsonArray:JSONArray? = null
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val conView = LayoutInflater.from(context).inflate(R.layout.item_product_profile,parent,false)
        var holder = MyProfileProductHolder(conView)
        holder.initData(getItem(position))
        return conView
    }

    override fun getItem(position: Int): JSONObject {
        return jsonArray!!.getJSONObject(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        if (jsonArray == null){
            return 0
        }
        return jsonArray!!.length()
    }

    fun setData(jsonArray: JSONArray?) {
        this.jsonArray = jsonArray
        notifyDataSetChanged()
    }

    class MyProfileProductHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var iv_img: ImageView? = null
        var tv_name: TextView? = null
        var tv_spec: TextView? = null
        var tv_color: TextView? = null
        var tv_sum: TextView? = null
        var iv_jian: ImageView? = null
        var iv_jia: ImageView? = null
        var tv_delete: TextView? = null
        init {
            iv_img = itemView.findViewById(R.id.iv_img)
            tv_name = itemView.findViewById(R.id.tv_name)
            tv_spec = itemView.findViewById(R.id.tv_spec)
            tv_color = itemView.findViewById(R.id.tv_color)
            tv_sum = itemView.findViewById(R.id.tv_sum)
            iv_jian = itemView.findViewById(R.id.iv_jian)
            iv_jia = itemView.findViewById(R.id.iv_jia)
            tv_delete = itemView.findViewById(R.id.tv_delete)
        }
        fun initData(jsonObject: JSONObject) {
            Glide.with(App.app!!).load(jsonObject.getString("headImage")).into(iv_img!!)
            var name = jsonObject.getString("name")
            var spec = jsonObject.getString("spec")
            if (name.length > 20){
                name = name.substring(0,20)
            }
            if (spec.length > 20){
                spec = spec.substring(0,20)
            }
            tv_name?.text = name
            tv_spec?.text = spec
            tv_color?.text = jsonObject.getString("color")
            tv_sum?.text = ""+jsonObject.getInt("count")

        }
    }

}
