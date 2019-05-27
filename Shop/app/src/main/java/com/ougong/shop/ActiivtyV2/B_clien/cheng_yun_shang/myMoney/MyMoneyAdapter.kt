package com.ougong.shop.ActiivtyV2.B_clien.cheng_yun_shang.myMoney

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.baigui.commonlib.utils.LogUtils
import com.ougong.shop.ActiivtyV2.bean.MOneyBeanDetail
import com.ougong.shop.ActiivtyV2.bean.MyMOneyBean
import com.ougong.shop.R
//import com.squareup.leakcanary.LeakTraceElement
//import kotlinx.android.synthetic.main.activity_search.view.*
import kotlinx.android.synthetic.main.money_item.view.*
import kotlinx.android.synthetic.main.money_title.view.*
import java.math.BigDecimal
import java.util.*

class MyMoneyAdapter(var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var mList: Array<MOneyBeanDetail>? = null
    var myMOneyBean: MyMOneyBean? = null
    var onClickCallBack : OnClickCallBack? = null
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        return when (p1) {
            1 -> {
                return TitleViewHolder(
                    LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.money_title,
                        parent,
                        false
                    )
                )
            }
            else -> myViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.money_item,
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemViewType(position: Int) = if (position == 0) 1 else 2

    override fun getItemCount(): Int {
        if (myMOneyBean == null) {
            return 0
        }
        return (mList?.size ?: 0) + 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, p1: Int) {

        when (holder){
            is TitleViewHolder ->{

                val numbers = arrayOf("全部", "收入", "已提现")
                holder.view.spinner.adapter = SpinnerAdapter(context,R.layout.test_text,numbers)
                holder.view.spinner.onItemSelectedListener = onClickCallBack


                holder.view.take_out_money.setOnClickListener {
                    onClickCallBack?.OnTakeOutMoneyClick()
                }


                holder.view.confirm.setOnClickListener {
                    onClickCallBack?.OnSearchClick()
                }


                holder.view.start_time_text.text = myMOneyBean?.startTime
                holder.view.end_time_text.text = myMOneyBean?.endTime
                holder.view.start_time_text.setOnClickListener { onClickCallBack?.OnStartTimeClick() }
                holder.view.end_time_text.setOnClickListener { onClickCallBack?.OnEndTimeClick() }

                holder.view.take_out_money.setOnClickListener { onClickCallBack?.OnTakeOutMoneyClick() }

                holder.view.spinner.setSelection(myMOneyBean?.SearchType?:0)
                holder.view.money.text = myMOneyBean?.total?.setScale(2, BigDecimal.ROUND_HALF_UP).toString()

                holder.view.have_take_out.text = myMOneyBean?.expendTotal?.setScale(2, BigDecimal.ROUND_HALF_UP).toString()
                holder.view.can_take_out.text = myMOneyBean?.balance?.setScale(2, BigDecimal.ROUND_HALF_UP).toString()
                holder.view.factory_take_out.text = myMOneyBean?.companyTotal?.setScale(2, BigDecimal.ROUND_HALF_UP).toString()
                holder.view.vip_take_out.text = myMOneyBean?.designerTotal?.setScale(2, BigDecimal.ROUND_HALF_UP).toString()
            }
            is myViewHolder ->{

                holder.view.name.text = mList!![p1-1].dealData?.user_name

                holder.view.phone.text = mList!![p1-1].dealData?.user_phone


                holder.view.item_pay.text = mList!![p1-1].amount?.setScale(2, BigDecimal.ROUND_HALF_UP).toString()

                holder.view.can_pay.text = mList!![p1-1].balance?.setScale(2, BigDecimal.ROUND_HALF_UP).toString()

                if (mList!![p1-1].type == 1){
                    holder.view.status.text = "已支付"
                }
                holder.view.status.text = "已支付"

                holder.view.order_no.text = mList!![p1-1].dealData?.orderNo

                holder.view.time.text = mList!![p1-1].dealTime

            }
        }
//
//        Glide.with(App.app!!)
//            .load(mList[p1].avatar)
//            .error(Glide.with(App.app!!).load(R.drawable.ice_default))
//            .into(holder.view.profile_image)
//
//        holder.view.name.setText(mList[p1].name)
//
//        holder.view.phone.setText(mList[p1].phone)
//
//        mList[p1].time?.let {
//            holder.view.time.setText(it + "提交")
//        }
//        mList[p1].enable?.let {
//            if (mList[p1].enable!!) {
//
//                holder.view.status.text = "已通过"
//            }else{
//
//                holder.view.status.text = "未通过"
//            }
//        }
    }

}

class myViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
}

class TitleViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
}

interface OnClickCallBack : AdapterView.OnItemSelectedListener {
    fun OnStartTimeClick()
    fun OnEndTimeClick()
    fun OnTakeOutMoneyClick()
    fun OnSearchClick()
}