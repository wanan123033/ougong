package com.ougong.shop.activity.checkin

import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.baigui.commonlib.kotlinUtils.extensions.Fromate
import com.baigui.commonlib.utils.ToastUtils
import com.ougong.shop.Bean.Netbean
import com.ougong.shop.R
import com.ougong.shop.activity.orderpay.QuickOrderActivity
import com.ougong.shop.adapter.MyRoomDatailAdapter
import com.ougong.shop.base_mvp.base.BaseActivity
import com.ougong.shop.httpmodule.CheckLinFanganDatailBean
import kotlinx.android.synthetic.main.activity_fangan_datail.*
import kotlinx.android.synthetic.main.include_back_title.*
import kotlinx.android.synthetic.main.item_fa_mine.*

class FanganDatailActivity :BaseActivity<FanganDatailContract.View,FanganDatailContract.Presenter>(),FanganDatailContract.View {
    var bean:CheckLinFanganDatailBean? = null
    override fun deleteFanganSucess(bean: Netbean<String>) {
        ToastUtils.toast(this,"删除成功")
        finish()
    }

    override fun initFanganDatail(bean: Netbean<CheckLinFanganDatailBean>) {
        this.bean = bean.data
        (rv_fa_datail.adapter as FaDatailAdapter).setData(bean.data.data)
        title_name.text = bean.data.name
        tv_name.text = bean.data.houseTypeName+"|"+bean.data.area+"㎡"
        tv_hx.text = bean.data.createTime
        tv_pricecdcd.text = bean.data.totalMoney!!.Fromate()
        tv_goumai.text = "立即购买("+totalCount(bean.data.data)+")"

    }

    private fun totalCount(data: Array<CheckLinFanganDatailBean.RoomBean>?): Int {
        var count = 0
        for (room in data!!){
            for (product in room.products){
                count += product.count
            }
        }
        return count
    }

    override fun setContentViewSource() = R.layout.activity_fangan_datail
    override fun initPresenter() = FanganDatailPresenter()
    companion object {
        var FANG_ID = "FANG_ID"
    }

    override fun initView() {
        super.initView()
        title_name.text = "方案详情"
        title_back.visibility = View.VISIBLE
        title_back.setOnClickListener {
            finish()
        }
        tv_delete.visibility = View.VISIBLE
        tv_price.visibility = View.GONE
        tv_data.visibility = View.GONE
        val id = intent.getIntExtra(FANG_ID,0)
        rv_fa_datail.layoutManager = LinearLayoutManager(this)
        rv_fa_datail.adapter = FaDatailAdapter(this)

        presenter.getFanganDatail(id)

        tv_delete.setOnClickListener {
            presenter.deleteFangan(id)
        }
        tv_goumai.setOnClickListener {
            val intent = Intent(applicationContext,QuickOrderActivity::class.java)
            intent.putExtra(QuickOrderActivity.ORDER_TYPE,1)
            intent.putExtra(QuickOrderActivity.ORDER_STR_CONTENT,getProductString())
            intent.putExtra(QuickOrderActivity.ORDER_DATA,getProductString())
            startActivity(intent)
        }
    }

    private fun getProductString(): String? {
        val sb = StringBuffer()
        for (room in bean!!.data!!){
            for (product in room.products){
                if (sb.isEmpty()){
                    sb.append(""+product.specId+"-"+product.count)
                }else{
                    sb.append(","+product.specId+"-"+product.count)
                }
            }
        }
        return sb.toString()
    }

    class FaDatailAdapter(var context: Context) :RecyclerView.Adapter<FaDatailHolder>(){
        var datas:Array<CheckLinFanganDatailBean.RoomBean>? = null
        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): FaDatailHolder {
            return FaDatailHolder(LayoutInflater.from(context).inflate(R.layout.item_fangan_datail,p0,false))
        }

        override fun getItemCount(): Int {
            if (datas == null){
                return 0
            }
            return datas!!.size
        }

        override fun onBindViewHolder(holder: FaDatailHolder, p1: Int) {
            holder.initData(datas!![p1])
        }

        fun setData(data: Array<CheckLinFanganDatailBean.RoomBean>?) {
            this.datas = data
            notifyDataSetChanged()
        }


    }
    class FaDatailHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun initData(roomBean: CheckLinFanganDatailBean.RoomBean) {
            tv_roomName?.text = roomBean.roomName
            (rv_fa_datail?.adapter as MyRoomDatailAdapter).setData(roomBean.products)
        }

        var rv_fa_datail:RecyclerView? = null
        var tv_roomName:TextView? = null
        init {
            rv_fa_datail = itemView.findViewById(R.id.rv_fa_datail)
            tv_roomName = itemView.findViewById(R.id.tv_roomName)
            rv_fa_datail?.layoutManager = LinearLayoutManager(itemView.context)
            rv_fa_datail?.adapter = MyRoomDatailAdapter(itemView.context)
        }
    }
}