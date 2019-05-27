package com.ougong.shop.ActiivtyV2.brandtab.BrandActivity

import android.support.design.widget.TabLayout
import com.ougong.shop.R
import com.ougong.shop.base_mvp.base.BaseActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.baigui.commonlib.kotlinUtils.StarusBarUtils
import com.baigui.commonlib.utils.ScreenUtils
import com.jcodecraeer.xrecyclerview.XRecyclerView
import com.ougong.shop.ActiivtyV2.bean.BrandDetailBean
import com.ougong.shop.ActiivtyV2.bean.BrandDetailTagBean
import com.ougong.shop.Bean.Netbean
import com.ougong.shop.Bean.ProductListItem
import com.ougong.shop.Bean.fuckNetbean
import com.ougong.shop.Bean.fuckProcudtList
import com.ougong.shop.ConstString
import io.armcha.ribble.presentation.utils.extensions.addBottomMargin
import io.armcha.ribble.presentation.utils.extensions.addTopMargin
import kotlinx.android.synthetic.main.activity_brand.*
import kotlinx.android.synthetic.main.fragment_tab_brand.*
import kotlinx.android.synthetic.main.include_back_title.*
import kotlinx.android.synthetic.main.tab_view.view.*


class BrandActivity : BaseActivity<BrandContract.View, BrandContract.Presenter>(), BrandContract.View,
    TabLayout.OnTabSelectedListener {
    override fun onTabReselected(p0: TabLayout.Tab?) {}

    override fun onTabUnselected(p0: TabLayout.Tab?) {}

    override fun onTabSelected(p0: TabLayout.Tab?) {

        mBean.conten.clear()
        mAdapter?.notifyDataSetChanged()
        mCurrentIndex = 1
        mRecyclerView.loadMoreComplete()

        mRecyclerView.setNoMore(false)
        p0?.let {
            if (p0.tag == 0) {
                presenter.getProductList(mBrandId, null, 0)
            } else {
                presenter.getProductList(mBrandId, p0.tag as Int, 0)
            }
        }

    }

    var mCurrentIndex = 0;
    override fun getProductListSucess(it: Netbean<fuckProcudtList<ProductListItem>>) {
        mBean.conten.addAll(it.data.rows)
        mAdapter?.notifyDataSetChanged()

        mCurrentIndex++

        mRecyclerView.refreshComplete()

        if (it.data.rows.size < 10) {
//
//            recycleView.setLoadingMoreEnabled(false)
            var te = TextView(this)
            te.setText("没有更多了！")
            mRecyclerView.footView.addTopMargin(ScreenUtils.dipTopx(this, 20f))
            mRecyclerView.footView.addBottomMargin(ScreenUtils.dipTopx(this, 20f))

            mRecyclerView.setNoMore(true)
            mRecyclerView.setFootViewText("正在加载", "没有更多了！")
        }
    }

    override fun initPresenter() = brandPresenter()

    override fun getBrandDetailSucess(bean: BrandDetailBean) {
        mBean.title = bean
        mAdapter?.notifyDataSetChanged()
    }

    override fun getBrandTagSucess(bean: fuckNetbean<BrandDetailTagBean>) {
        val view = LayoutInflater.from(this).inflate(R.layout.tab_view, mRecyclerView, false)

        view.tab.addTab(view.tab.newTab().setText("全部").setTag(0))

        for (name in bean.data) {
            view.tab.addTab(view.tab.newTab().setText(name.name).setTag(name.id))
        }

        view.tab.addOnTabSelectedListener(this)
        mAdapter?.tabView = view
        mBean.head.clear()
        mBean.head.addAll(bean.data)
        mAdapter?.notifyDataSetChanged()

        if (mCurrentIndex == 0) {

            presenter.getProductList(mBrandId, null, mCurrentIndex)
            mCurrentIndex = 1
        }
    }

    override fun setContentViewSource() = R.layout.activity_brand

    private var mAdapter: BrandAdapter? = null

    var mBrandId = 0
    override fun initView() {
        StarusBarUtils.setStatusBarColor(this, R.color.f5f5f5)
        StarusBarUtils.setAndroidNativeLightStatusBar(this, true)
        title_name.text = intent.getStringExtra(ConstString.PRODUCT_ITEM_NAME)
        mBrandId = intent.getIntExtra(ConstString.PRODUCT_ITEM, 0)
        presenter.getBrandDetail(mBrandId)
        presenter.getBrandTagList(mBrandId)

        title_back.visibility = View.VISIBLE
        title_back.setOnClickListener { finish() }
        mRecyclerView.setPullRefreshEnabled(false)
        mRecyclerView.setLayoutManager(StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL))
        mAdapter = BrandAdapter(this, mBean)
        mRecyclerView.setAdapter(mAdapter)

        mRecyclerView.setLoadingMoreEnabled(true)
        mRecyclerView.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onLoadMore() {
                mAdapter?.tabView?.let {

                    if (mAdapter!!.tabView!!.tab.selectedTabPosition == 0) {
                        presenter.getProductList(mBrandId, null, mCurrentIndex)
                    } else {
                        presenter.getProductList(mBrandId, mAdapter!!.stickyExampleModels.head[mAdapter!!.tabView!!.tab.selectedTabPosition].id, mCurrentIndex)
                    }

                }
            }

            override fun onRefresh() {
            }

        })

    }


    /**
     * 用来盛放接口返回的数据
     */
    private val mBean: BrandDetailAdaptertBean = BrandDetailAdaptertBean()

    companion object {
        var titleHeight = 80
    }
}

