package com.ougong.shop.activity.MainShopCarFragment.ChooseAddress

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ougong.shop.R
import com.ougong.shop.activity.Maininfo.mangeAdress.AddressBean
import kotlinx.android.synthetic.main.choose_add_address.view.*

class ChooseAddressAdapter : RecyclerView.Adapter<VH>() {

    //因为取的时候都是回调中取的，所以不为空
    fun getData(position: Int) = mList!![position]

    var mList: Array<AddressBean>? = null
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): VH {
        return VH(LayoutInflater.from(p0.context).inflate(R.layout.choose_add_address, p0, false))
    }

    var CheckId  = -1
    override fun getItemCount() =
        if (mList == null) {
            0
        } else {
            mList!!.size
        }

    override fun onBindViewHolder(p0: VH, p1: Int) {
        p0.view.name.text = mList!![p1].name

        p0.view.phone.text = mList!![p1].mobile

        p0.view.address.text = mList!![p1].addressInfo

        p0.view.isChoose.isChecked = mList!![p1].id == CheckId

        p0.view.setOnClickListener {
            mItemclick?.Choose(p1)
        }
        if (mList!![p1].isDefault){
            p0.view.is_default.visibility = View.VISIBLE
        }
        p0.view.address_iedt.setOnClickListener {
            mItemclick?.edit(p1)
        }
    }

    fun setData(list: Array<AddressBean>) {

        mList = list
        notifyDataSetChanged()

    }

    var mItemclick: itemclick? = null

    interface itemclick {
        fun Choose(position: Int)
        fun edit(position: Int)
    }

}


class VH(var view: View) : RecyclerView.ViewHolder(view) {

}
