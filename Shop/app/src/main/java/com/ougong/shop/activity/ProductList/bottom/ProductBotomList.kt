package com.ougong.shop.activity.ProductList.bottom

import android.graphics.drawable.Drawable
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.widget.TextView
import com.baigui.commonlib.utils.ScreenUtils
import com.jcodecraeer.xrecyclerview.XRecyclerView
import com.ougong.shop.AccountHelper
import com.ougong.shop.Bean.CateryBean
import com.ougong.shop.Bean.Netbean
import com.ougong.shop.Bean.ProductListItem
import com.ougong.shop.Bean.fuckProcudtList
import com.ougong.shop.ConstString
import com.ougong.shop.R
import com.ougong.shop.activity.ProductList.ProductBotomListPresenter
import com.ougong.shop.activity.ProductList.ProductBottomContract
import com.ougong.shop.activity.ShopCar.ShopCarActivity
import com.ougong.shop.base_mvp.base.BaseActivity
import io.armcha.ribble.presentation.utils.extensions.start
import kotlinx.android.synthetic.main.activity_product_botom_list.*
import kotlinx.android.synthetic.main.include_back_title.*
import kotlinx.android.synthetic.main.include_shop_car_ice.*


class ProductBotomList : BaseActivity<ProductBottomContract.View, ProductBottomContract.Presenter>(),
    ProductBottomContract.View, View.OnClickListener {

    var orderStatus = 0

    //这里完全可以保存直接请求的状态，不过这里也有一定好处。
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
//
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
            }
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

    var mList = arrayListOf<ProductListItem>()

    var mAdapter: ProductListAdapter? = null


    var mPage = 1

    override fun setContentViewSource() = R.layout.activity_product_botom_list

    override fun initPresenter() = ProductBotomListPresenter()

    override fun getProductSucess(it: Netbean<fuckProcudtList<ProductListItem>>) {

        mPage++

        mList.addAll(it.data.rows)

        mAdapter?.notifyDataSetChanged()
        recycleView.refreshComplete()
        if (it.data.rows.size < 10) {
//
//            recycleView.setLoadingMoreEnabled(false)
            var te = TextView(this)
//            te.setText("没有更多了！")
//            recycleView.footView.addTopMargin(ScreenUtils.dipTopx(this, 20f))
//            recycleView.footView.addBottomMargin(ScreenUtils.dipTopx(this, 20f))

            recycleView.setNoMore(true)
//            recycleView.setFootViewText("正在加载", "没有更多了！")
        }
    }

    var mBean: CateryBean? = null
    override fun initData() {
        super.initData()
        mBean = intent.getSerializableExtra("data") as CateryBean
        title_name.setText(mBean?.name)
        title_back.visibility = View.VISIBLE
        request()
    }


    override fun initView() {

        refeshViewShopCarIce()
        shopcar_ice.setOnClickListener { start<ShopCarActivity>() }

        recycleView.setFootViewText("正在加载", "没有更多了！")
        recycleView.footView.setPadding(0,ScreenUtils.dipTopx(this, 20f),0,ScreenUtils.dipTopx(this, 20f))
        title_back.visibility = View.VISIBLE
        title_back.setOnClickListener { finish() }

        curser0.visibility = View.VISIBLE

        people_order.setOnClickListener(this)
        price_order.setOnClickListener(this)
        mAdapter = ProductListAdapter(this, mList)

        recycleView.adapter = mAdapter

        recycleView.setPullRefreshEnabled(false)
        recycleView.setLayoutManager(GridLayoutManager(this, 2))

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

    fun refeshViewShopCarIce() {
        if (AccountHelper.globalDataTemp.shopCarCount > 0) {
            dot_shopcar_count.visibility = View.VISIBLE
            dot_shopcar_count.text = AccountHelper.globalDataTemp.shopCarCount.toString()
        } else {
            dot_shopcar_count.visibility = View.GONE
        }
    }
}
