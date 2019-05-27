package com.ougong.shop.activity.ProductList.midle

import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.baigui.commonlib.utils.ScreenUtils
import com.jcodecraeer.xrecyclerview.XRecyclerView
import com.ougong.shop.Bean.CateryBean
import com.ougong.shop.Bean.Netbean
import com.ougong.shop.Bean.ProductListItem
import com.ougong.shop.Bean.fuckProcudtList
import com.ougong.shop.ConstString
import com.ougong.shop.R
import com.ougong.shop.activity.Product.ProductPresenter
import com.ougong.shop.activity.Product.ProductContract
import com.ougong.shop.activity.ProductList.ProductBotomListPresenter
import com.ougong.shop.activity.ProductList.ProductBottomContract
import com.ougong.shop.activity.ProductList.bottom.ProductListAdapter
import com.ougong.shop.base_mvp.base.BaseFragment
import io.armcha.ribble.presentation.utils.extensions.addBottomMargin
import io.armcha.ribble.presentation.utils.extensions.addTopMargin
import kotlinx.android.synthetic.main.activity_product_midle_content.*

class ContentFragment : BaseFragment<ProductBottomContract.View, ProductBottomContract.Presenter>(),
    ProductBottomContract.View, View.OnClickListener {

    /**
     * 0 人气降低 1 增
     * 2 价格降低 3 增
     *
     * 因为需要增加记忆上个tab功能，所以，更新一下状态。111 是人气降 价格降， 121 是人气升价格降 起作用的是  人气
     *
     * 221 是人气升 价格降， 221 是人气升 价格升 起作用的是  价格
     *
     * f放弃了，直接用两个变量控制吧。
     */
    var orderStatus = 0

    var peopleStatus = 1
    var priceStatus = 1
    var curentStatus = 1

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.people_order -> {
                mPage = 1
                mList.clear()
                mAdapter?.notifyDataSetChanged()
                curser1.visibility = View.INVISIBLE
                var order = ConstString.DOWN_ORDER
                if (curentStatus == 1) { //这是人气排序，只用和原来相反即可，顺序颠倒
                    if (peopleStatus == 1) {
                        peopleStatus = 2
                        people_order_ice.setImageResource(R.drawable.up_right)
                        order = ConstString.UP_ORDER
                    } else {
                        people_order_ice.setImageResource(R.drawable.down_right)
                        peopleStatus = 1
                        order = ConstString.DOWN_ORDER
                    }
                } else {//这里是之前点击了别的按钮，这里的话，就需要显示指示器。
                    if (peopleStatus == 1) {
                        people_order_ice.setImageResource(R.drawable.down_right)
//                        price_order_ice.setImageResource(R.drawable.up_right)
                    } else {
                        people_order_ice.setImageResource(R.drawable.up_right)
                    }
                    curentStatus = 1
                    curser0.visibility = View.VISIBLE
                }
                request()
//
//                mBean?.let {
//                    presenter.getProduct(id = mBean!!.id, page = mPage, sort = ConstString.HOT_ORDER, order = order)
//                }
            }
            R.id.price_order -> {
                mPage = 1
                mList.clear()
                mAdapter?.notifyDataSetChanged()
                curser0.visibility = View.INVISIBLE
                var order = ConstString.DOWN_ORDER

                if (curentStatus == 2) { //这是人气排序，只用和原来相反即可，顺序颠倒
                    if (priceStatus == 1) {
                        priceStatus = 2

                        price_order_ice.setImageResource(R.drawable.up_right)
                        order = ConstString.UP_ORDER
                    } else {
                        price_order_ice.setImageResource(R.drawable.down_right)
                        priceStatus = 1
                        order = ConstString.DOWN_ORDER
                    }
                } else {//其他状态，就直接默认值
                    if (priceStatus == 1) {
                        price_order_ice.setImageResource(R.drawable.down_right)
                        order = ConstString.UP_ORDER
                    } else {
                        price_order_ice.setImageResource(R.drawable.up_right)
                        order = ConstString.DOWN_ORDER
                    }
                    curser1.visibility = View.VISIBLE
                    curentStatus = 2
                }

                request()
//                presenter.getProduct(mBean!!.id, sort = ConstString.PRICE_ORDER, order = order)

            }
        }
    }


    var mList = arrayListOf<ProductListItem>()

    var mAdapter: ProductListAdapter? = null


    var mPage = 0

    override fun setContentViewSource() = R.layout.activity_product_midle_content

    override fun initPresenter() = ProductBotomListPresenter()

    override fun getProductSucess(it: Netbean<fuckProcudtList<ProductListItem>>) {

        mPage++

        mList.addAll(it.data.rows)

        mAdapter?.notifyDataSetChanged()
        recycleView.refreshComplete()
        if (it.data.rows.size < 10) {
//
//            recycleView.setLoadingMoreEnabled(false)
//            recycleView.footView.addTopMargin(ScreenUtils.dipTopx(activity, 20f))
//            recycleView.footView.addBottomMargin(ScreenUtils.dipTopx(activity, 20f))

            recycleView.setNoMore(true)
        }
    }

    private fun request() {
        var sort = ConstString.HOT_ORDER
        var order = ConstString.DOWN_ORDER
        if (curentStatus == 2) {
            sort = ConstString.PRICE_ORDER
            if (priceStatus == 2) {
                order = ConstString.UP_ORDER
            }
        } else {
            if (peopleStatus == 2) {
                order = ConstString.UP_ORDER
            }
        }

        mBean?.apply {
            presenter.getProduct(this.id, page = mPage, sort = sort, order = order)
        }
    }

    var mBean: CateryBean? = null
    override fun initData() {
        super.initData()
        if (mPage == 0) {
            request()
        }
    }

    fun setData(bean: CateryBean): Fragment {
        mBean = bean
        return this
    }


    override fun initView() {


        curser0.visibility = View.VISIBLE

        recycleView.setFootViewText("正在加载", "没有更多了！")
        recycleView.footView.setPadding(0,ScreenUtils.dipTopx(activity, 20f),0,ScreenUtils.dipTopx(activity, 20f))

        people_order.setOnClickListener(this)
        price_order.setOnClickListener(this)
        mAdapter = ProductListAdapter(activity, mList)

        recycleView.adapter = mAdapter

        recycleView.setPullRefreshEnabled(false)
        recycleView.setLayoutManager(GridLayoutManager(activity, 2))

        recycleView.setLoadingMoreEnabled(true)

        recycleView.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onLoadMore() {
                mBean?.let {
                    request()
                }
            }

            override fun onRefresh() {
            }

        })
    }
}

