package com.ougong.shop.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ougong.shop.R
import com.ougong.shop.activity.orderhistory.OrderDetailActivity
import com.ougong.shop.activity.orderhistory.OrderHistoryFragment
import com.ougong.shop.activity.orderhistory.OrderOpeator
import com.ougong.shop.httpmodule.OrderHistoryJavaBean

class OrderHistory2Adapter(
    var context: Context?,
    var listener: OrderOpeator
) : RecyclerView.Adapter<OrderHistory2Adapter.OrderHistoryViewHolder>() {
    var datas : ArrayList<OrderHistoryJavaBean.Databean>? = arrayListOf()
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): OrderHistoryViewHolder {
        return OrderHistoryViewHolder(LayoutInflater.from(context).inflate(R.layout.item_order_history2,p0,false),this)
    }

    override fun getItemCount() = datas!!.size

    override fun onBindViewHolder(holder: OrderHistoryViewHolder, p1: Int) {
        holder.initData(datas!![p1])
    }

    fun addData(data: Array<OrderHistoryJavaBean.Databean>) {
        for (databean in data){
            datas?.add(databean)
        }
        notifyDataSetChanged()
    }

    fun setData(rows: Array<OrderHistoryJavaBean.Databean>) {
        datas?.clear()
        for (databean in rows){
            datas?.add(databean)
        }
        notifyDataSetChanged()
    }

    class OrderHistoryViewHolder(itemView: View,var adapter:OrderHistory2Adapter) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        override fun onClick(v: View?) {
            when(v?.id){
                R.id.tv_cannel -> {  //取消订单
                    adapter.listener.cannalOrder(databean!!.id!!)
                }
                R.id.tv_buy_it -> {  //立即购买
                    adapter.listener.pay(databean!!.id!!)
                }
                R.id.tv_qrsh  -> {   //确认收货
                    adapter.listener.qrsh(databean!!.id!!)
                }
                R.id.tv_buy_next -> {//再次购买
                    adapter.listener.gotoPay(databean!!.id!!)
                }
                R.id.tv_delete -> {  //删除订单
                    adapter.listener.delete(databean!!.id!!)
                }
            }
        }

        var recycleView:RecyclerView? = null
        var tv_brand:TextView? = null
        var tv_time:TextView? = null
        var tv_payType:TextView? = null
        var tv_cannal:TextView? = null
        var tv_buy_it:TextView? = null
        var tv_qrsh:TextView? = null
        var tv_buy_next:TextView? = null
        var tv_sum:TextView? = null
        var tv_price:TextView? = null
        var tv_delete:TextView? = null
        var databean:OrderHistoryJavaBean.Databean? = null
        init {
            recycleView = itemView.findViewById(R.id.recycleView)
            recycleView?.layoutManager = LinearLayoutManager(itemView.context)
            tv_brand = itemView.findViewById(R.id.tv_brand)
            tv_time = itemView.findViewById(R.id.tv_time)
            tv_payType = itemView.findViewById(R.id.tv_payType)
            tv_cannal = itemView.findViewById(R.id.tv_cannel)
            tv_buy_it = itemView.findViewById(R.id.tv_buy_it)
            tv_qrsh = itemView.findViewById(R.id.tv_qrsh)
            tv_buy_next = itemView.findViewById(R.id.tv_buy_next)
            tv_delete = itemView.findViewById(R.id.tv_delete)
            tv_sum = itemView.findViewById(R.id.tv_sum)
            tv_price = itemView.findViewById(R.id.tv_price)
            tv_cannal?.setOnClickListener(this)
            tv_buy_it?.setOnClickListener(this)
            tv_qrsh?.setOnClickListener(this)
            tv_buy_next?.setOnClickListener(this)
            tv_delete?.setOnClickListener(this)
        }
        fun initData(databean: OrderHistoryJavaBean.Databean) {
            this.databean = databean
            Log.e("TAG", "databean = $databean")
            tv_time?.text = databean.createTime
            tv_brand?.text = databean.brandInfoList!![0].brandSnap?.name
            tv_payType?.text = databean.statusName
            recycleView?.adapter = OrderHistoryItemAdapter(itemView.context,databean.orderInfoList!!)
            tv_sum?.text = "共"+ databean.orderInfoList!!.size+"件商品,合计："
            tv_price?.text = "￥"+databean.totalPrice
            if (databean.status!! <= -40.0 && databean.status!! >= -40.3){   //已退款
                tv_cannal?.visibility = View.GONE
                tv_buy_it?.visibility = View.GONE
                tv_qrsh?.visibility = View.GONE
                tv_buy_next?.visibility = View.GONE
                tv_delete?.visibility = View.VISIBLE
            }else if (databean.status!! <= -30.0 && databean.status!! >= -30.3){  //待退款
                tv_cannal?.visibility = View.GONE
                tv_buy_it?.visibility = View.GONE
                tv_qrsh?.visibility = View.GONE
                tv_buy_next?.visibility = View.GONE
                tv_delete?.visibility = View.GONE
            }else if (databean.status!! <= -20.0 && databean.status!! >= -20.3){  //待处理
                tv_cannal?.visibility = View.GONE
                tv_buy_it?.visibility = View.GONE
                tv_qrsh?.visibility = View.GONE
                tv_buy_next?.visibility = View.GONE
                tv_delete?.visibility = View.GONE
            }else if (databean.status!! <= -10.0 && databean.status!! >= -10.3){  //已取消
                tv_cannal?.visibility = View.GONE
                tv_buy_it?.visibility = View.GONE
                tv_qrsh?.visibility = View.GONE
                tv_buy_next?.visibility = View.GONE
                tv_delete?.visibility = View.VISIBLE
            }else if (databean.status!! in 0.0..0.3){     //未支付
                tv_cannal?.visibility = View.VISIBLE
                tv_buy_it?.visibility = View.VISIBLE
                tv_qrsh?.visibility = View.GONE
                tv_buy_next?.visibility = View.GONE
                tv_delete?.visibility = View.GONE
            }else if (databean.status!! in 10.0..10.8){   //已支付
                tv_cannal?.visibility = View.GONE
                tv_buy_it?.visibility = View.GONE
                tv_qrsh?.visibility = View.GONE
                tv_buy_next?.visibility = View.VISIBLE
                tv_delete?.visibility = View.GONE
            }else if (databean.status!! in 20.0..20.2){   //已发货
                tv_cannal?.visibility = View.GONE
                tv_buy_it?.visibility = View.GONE
                tv_qrsh?.visibility = View.VISIBLE
                tv_buy_next?.visibility = View.VISIBLE
                tv_delete?.visibility = View.GONE
            }else if (databean.status!! in 30.0..30.2){   //已收货
                tv_cannal?.visibility = View.GONE
                tv_buy_it?.visibility = View.GONE
                tv_qrsh?.visibility = View.GONE
                tv_buy_next?.visibility = View.VISIBLE
                tv_delete?.visibility = View.VISIBLE

            }

            itemView.setOnClickListener {
                val intent = Intent(itemView.context,OrderDetailActivity::class.java)
                intent.putExtra(OrderDetailActivity.ORDER_ID,databean.id)
                itemView.context.startActivity(intent)
            }
        }

    }
}
