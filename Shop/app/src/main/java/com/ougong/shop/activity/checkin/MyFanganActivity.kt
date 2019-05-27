package com.ougong.shop.activity.checkin

import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.jcodecraeer.xrecyclerview.ProgressStyle
import com.jcodecraeer.xrecyclerview.XRecyclerView
import com.ougong.shop.AccountHelper
import com.ougong.shop.Bean.Netbean
import com.ougong.shop.R
import com.ougong.shop.activity.Account.login.LogInActivity
import com.ougong.shop.base_mvp.base.BaseActivity
import com.ougong.shop.httpmodule.MyCheckLinFanganBean
import io.armcha.ribble.presentation.utils.extensions.start
import kotlinx.android.synthetic.main.activity_fangan_mine.*
import kotlinx.android.synthetic.main.include_back_title.*

class MyFanganActivity : BaseActivity<MyFanganActivityContract.View,MyFanganActivityContract.Presenter>(),MyFanganActivityContract.View {
    var mPageNum:Int = 0
    override fun initFangAnData(bean: Netbean<MyCheckLinFanganBean>) {
        rv_fa_mine.refreshComplete()
        if (mPageNum == 1){
            (rv_fa_mine.adapter as FaMineAdapter).clearData()
        }
        (rv_fa_mine.adapter as FaMineAdapter).addData(bean.data.rows)
        if (bean.data.rows != null && rv_fa_mine.adapter!!.itemCount == bean.data.total){
            (rv_fa_mine.adapter as FaMineAdapter).showFooter()
        }
    }

    override fun setContentViewSource() = R.layout.activity_fangan_mine
    override fun initPresenter() = MyFanganPresenter()

    override fun initView() {
        super.initView()
        title_name.text = "我的收藏"
        title_back.visibility = View.VISIBLE
        title_back.setOnClickListener {
            finish()
        }
        rv_fa_mine.layoutManager = LinearLayoutManager(this)
        rv_fa_mine.adapter =FaMineAdapter(this)
        rv_fa_mine.setPullRefreshEnabled(true)
        rv_fa_mine.setLoadingMoreEnabled(true)
        rv_fa_mine.getDefaultRefreshHeaderView().setRefreshTimeVisible(true)
        rv_fa_mine.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader)
        rv_fa_mine.setLoadingMoreProgressStyle(ProgressStyle.BallRotate)
        rv_fa_mine.setLoadingListener(object : XRecyclerView.LoadingListener{
            override fun onRefresh() {
                mPageNum = 1
                presenter.getMyFangan(18,mPageNum)
            }

            override fun onLoadMore() {
                mPageNum++
                presenter.getMyFangan(18,mPageNum)
            }

        })
        if (AccountHelper.isLogin){
            presenter.getMyFangan(18,1)
        }else{
            start<LogInActivity>()
            finish()
        }

    }


    class FaMineAdapter(var context:Context) : RecyclerView.Adapter<FaMineViewHolder>(){
        var rows:ArrayList<MyCheckLinFanganBean.RowBean>? = arrayListOf()
        var isFooter:Boolean = false
        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): FaMineViewHolder {
            val view:View = LayoutInflater.from(context).inflate(R.layout.item_fa_mine,p0,false)
            return FaMineViewHolder(view)
        }

        override fun getItemCount(): Int {
            return rows!!.size
        }

        override fun onBindViewHolder(holder: FaMineViewHolder, p1: Int) {
            holder.initData(rows!![p1],isFooter,itemCount)

        }

        fun addData(rows: Array<MyCheckLinFanganBean.RowBean>?) {
            for (row in rows!!){
                this.rows!!.add(row)
            }
            notifyDataSetChanged()
        }

        fun clearData() {
            this.rows!!.clear()
            notifyDataSetChanged()
        }

        fun showFooter() {
            isFooter = true
            notifyDataSetChanged()
        }

    }

    class FaMineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var tv_name:TextView? = null
        var tv_price:TextView? = null
        var tv_hx:TextView? = null
        var tv_data:TextView? = null
        var ll_footer_view:LinearLayout? = null

        var rowBean: MyCheckLinFanganBean.RowBean? = null
        init{
            tv_name = itemView.findViewById(R.id.tv_name)
            tv_price = itemView.findViewById(R.id.tv_price)
            tv_hx = itemView.findViewById(R.id.tv_hx)
            tv_data = itemView.findViewById(R.id.tv_data)
            ll_footer_view = itemView.findViewById(R.id.ll_footer_view)
            itemView.findViewById<View>(R.id.rl_fa).setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            val intent = Intent(itemView.context,FanganDatailActivity::class.java)
            intent.putExtra(FanganDatailActivity.FANG_ID, rowBean!!.id)
            itemView.context.startActivity(intent)
        }

        fun initData(
            rowBean: MyCheckLinFanganBean.RowBean,
            footer: Boolean,
            p1: Int
        ) {
            this.rowBean = rowBean
            tv_name?.text = rowBean.name
            tv_hx?.text = rowBean.houseTypeName+"|"+rowBean.area+"㎡"
            tv_data?.text = rowBean.createTime
            tv_price?.text = "共"+totalPrice(rowBean.data)+"件商品"
            if (footer && adapterPosition == p1){
                ll_footer_view?.visibility = View.VISIBLE
            }else{
                ll_footer_view?.visibility = View.GONE
            }
        }

        private fun totalPrice(data: Array<MyCheckLinFanganBean.DataBean>?): Int {
            var count :Int = 0
            for (databean in data!!){
                for (product in databean.products){
                    count += product.count
                }
            }
            return count
        }
    }
}