package com.ougong.shop.activity.combo

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.TextView
import com.jcodecraeer.xrecyclerview.ProgressStyle
import com.jcodecraeer.xrecyclerview.XRecyclerView
import com.ougong.shop.Bean.Netbean
import com.ougong.shop.Bean.fuckNetbean
import com.ougong.shop.MsgContract
import com.ougong.shop.R
import com.ougong.shop.adapter.ComboAdapter
import com.ougong.shop.base_mvp.base.BaseFragment
import com.ougong.shop.httpmodule.ComboBean
import com.ougong.shop.httpmodule.ComboStyleBean
import com.ougong.shop.httpmodule.ComboTitleBean
import com.ougong.shop.utils.MessageBus
import com.ougong.shop.utils.MessageBusMessage
import kotlinx.android.synthetic.main.fragment_combo_style.*


class ComboStyleFragment : BaseFragment<ComboStyleFragmentContract.View,ComboStyleFragmentContract.Presenter>(),ComboStyleFragmentContract.View,
    AdapterView.OnItemClickListener {

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        (recycleView.adapter as ComboAdapter).clearData()
        //初始化RecyclerView
        mCurrentPage = 1
        val itemId = (parent!!.adapter.getItem(position) as ComboStyleBean).id
        styleId = itemId
        presenter.getComboData(itemId,mCurrentPage, datas!!.id)
        gv_style.visibility = View.GONE

        val list = (gv_style.adapter as bandStyleAdapter).getList()
        for (item in list){
            item.enable = item.id == itemId
        }
        (gv_style.adapter as bandStyleAdapter).notifyDataSetChanged()

    }

    override fun initComboData(bean: Netbean<ComboBean>) {
        recycleView.refreshComplete()

        if (recycleView.adapter == null) {
            val array:ArrayList<ComboBean.DataBean> = arrayListOf()
            array.addAll(bean.data.rows!!.asList())
            recycleView.adapter = ComboAdapter(activity, array,false,isGuding)
        }else{
            val array:ArrayList<ComboBean.DataBean> = arrayListOf()
            if (bean.data.rows != null){
                array.addAll(bean.data.rows!!.asList())
                (recycleView.adapter as ComboAdapter).addData(array)
            }
        }
        if (mCurrentPage >= bean.data.total!! || bean.data.rows == null){
            recycleView.setNoMore(true)
            recycleView.setLoadingMoreEnabled(false)
            (recycleView.adapter as ComboAdapter).setFooter(true)
        }
        if (styleId == null && datas!!.id == null && mCurrentPage == 1)
            MessageBus.getBus().post(MessageBusMessage(MsgContract.RESH_COMBO_NUM,bean.data.total))
    }

    var styleId:Int? = null
    var mCurrentPage:Int = 1
    var isGuding:Boolean? = false

    override fun initStyleData(bean: fuckNetbean<ComboStyleBean>) {
        val list:ArrayList<ComboStyleBean> = arrayListOf()
        list.add(ComboStyleBean(null,"全部","",true))
        list.addAll(bean.data.asList())
        for (item in list){
            item.enable = item.id == null
        }
        gv_style.adapter = bandStyleAdapter(activity,list)
    }
    var datas:ComboTitleBean.Result? = null

    override fun setContentViewSource() = R.layout.fragment_combo_style

    override fun initPresenter() = ComboStylePresenter()
    fun setData(get: ComboTitleBean.Result, equals: Boolean): ComboStyleFragment {
        this.datas = get
        this.isGuding = equals
        return this
    }

    override fun initView() {
        super.initView()
        gv_style.onItemClickListener = this
        if (presenter != null) {
            presenter.getStyleData(datas!!.id)
        }
        mCurrentPage = 1
        presenter.getComboData(null,mCurrentPage, datas!!.id)
    }

    override fun initData() {
        super.initData()
        recycleView.layoutManager = LinearLayoutManager(activity)
        recycleView.setPullRefreshEnabled(false)
        recycleView.setLoadingMoreEnabled(true)
        recycleView.getDefaultRefreshHeaderView().setRefreshTimeVisible(true)
        recycleView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader)
        recycleView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate)
        recycleView.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onLoadMore() {
                if (presenter != null&& datas != null) {

                    mCurrentPage++
                    presenter.getComboData(styleId, mCurrentPage, datas!!.id)
                }
            }

            override fun onRefresh() {
            }
        })
        recycleView.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                isGrid = false
                initgvStyleView()
            }
        })
    }
    var isGrid = true
    fun showStyle() {
        if(gv_style != null){
            if (isVisible){
                gv_style.visibility = View.VISIBLE
                view_fen.visibility = View.VISIBLE
            }
            isGrid = gv_style.visibility == View.GONE
        }

    }

    override fun onResume() {
        super.onResume()
        showStyle()
    }

    private fun initgvStyleView() {
        if (isGrid) {
            gv_style.visibility = View.VISIBLE
            view_fen.visibility = View.VISIBLE
        } else {
            gv_style.visibility = View.GONE
            view_fen.visibility = View.GONE
        }
    }

    fun initStyle() {
        if (isGrid){
            gv_style.visibility = View.VISIBLE
            view_fen.visibility = View.VISIBLE
        }else{
            gv_style.visibility = View.GONE
            view_fen.visibility = View.GONE
        }
        isGrid = gv_style.visibility == View.GONE
    }

    inner class bandStyleAdapter(private val activity: Context, private val data: ArrayList<ComboStyleBean>) : BaseAdapter() {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var conView : TextView= LayoutInflater.from(activity).inflate(R.layout.item_style,null,false) as TextView
            conView.text = getItem(position).name
            if (getItem(position).enable){
                conView.setTextColor(Color.BLACK)
                conView.setBackgroundResource(R.drawable.img_boder)
            }else{
                conView.setTextColor(Color.parseColor("#999999"))
                conView.setBackgroundResource(R.drawable.shape_style_item_nomal)
            }
            return conView
        }


        override fun getItem(position: Int): ComboStyleBean {
            return data.get(position)
        }

        override fun getItemId(position: Int): Long {
            return position + 0L
        }

        override fun getCount(): Int {
            return data.size
        }

        fun getList():ArrayList<ComboStyleBean>{
            return data
        }
    }
}
