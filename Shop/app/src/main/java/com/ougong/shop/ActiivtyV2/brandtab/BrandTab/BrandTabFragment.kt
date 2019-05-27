package com.ougong.shop.ActiivtyV2.brandtab.BrandTab

import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.widget.TextView
import com.baigui.commonlib.utils.LogUtils
import com.baigui.commonlib.utils.ScreenUtils
import com.jcodecraeer.xrecyclerview.XRecyclerView
import com.ougong.shop.ActiivtyV2.bean.BrandBeanCollection
import com.ougong.shop.ActiivtyV2.brandtab.BrandFragment
import com.ougong.shop.Bean.ProductListItem
import com.ougong.shop.Bean.fuckNetbean
import com.ougong.shop.Bean.fuckProcudtList
import com.ougong.shop.R
import com.ougong.shop.base_mvp.base.BaseFragment
import io.armcha.ribble.presentation.utils.extensions.addBottomMargin
import io.armcha.ribble.presentation.utils.extensions.addTopMargin
import kotlinx.android.synthetic.main.fragment_tab_brand.*
import kotlinx.android.synthetic.main.tab_text_child.view.*

class BrandTabFragment : BaseFragment<BrandTabContract.View, BrandTabContract.Presenter>(), BrandTabContract.View,
    TabLayout.OnTabSelectedListener {
    override fun onTabReselected(p0: TabLayout.Tab?) {

    }

    var mList = arrayListOf<BrandBeanCollection.BrandItemBean>()
    override fun onTabUnselected(p0: TabLayout.Tab?) {

    }

    override fun onTabSelected(p0: TabLayout.Tab?) {

        recycleView.setNoMore(false)
        mList.clear()
        mAdapter?.notifyDataSetChanged()
        mCurrentIndx = 1
        if (tabLayout.selectedTabPosition == 0) {
            presenter.getBrandItemList(mBrandBean.id, mCurrentIndx)
        } else {
            presenter.getBrandItemList(
                mBrandBean.id,
                mCurrentIndx,
                mStyleBean!!.data.get(tabLayout.selectedTabPosition - 1).id
            )
        }
//        mCurrentIndx = 1
    }


    var mCurrentIndx = 0
    override fun setContentViewSource() = R.layout.fragment_tab_brand

    override fun initPresenter() = BrandTabPresenter()

    override fun getStyleSucess(bean: fuckNetbean<BrandBeanCollection.BrandStyleBean>) {
        tabLayout.removeAllTabs()
        val tabView = LayoutInflater.from(activity).inflate(R.layout.tab_text_child, null)
        (tabView.textcontent as TextView).text = "全部"
        tabLayout.addTab(tabLayout.newTab().setCustomView(tabView))
        tabView.setPadding(ScreenUtils.dipTopx(activity, 12f), 0, 0, 0)
        for (i in 0..(bean.data.size - 1)) {
            val tabView = LayoutInflater.from(activity).inflate(R.layout.tab_text_child, null)
            (tabView.textcontent as TextView).text = bean.data[i].name
            tabLayout.addTab(tabLayout.newTab().setCustomView(tabView))
            if (i == bean.data.size - 1) {
                tabView.setPadding(0, 0, ScreenUtils.dipTopx(activity, 12f), 0)
            }
        }

        mStyleBean = bean
//        presenter.getBrandItemList(mBrandBean.id, 0)

//        mCurrentIndx = 1
//        presenter.getBrandItemList(mBrandBean.id, mCurrentIndx)
    }

    override fun getBrandItemSucess(bean: fuckProcudtList<BrandBeanCollection.BrandItemBean>) {

        mCurrentIndx++
        mList.addAll(bean.rows)
        mAdapter?.notifyDataSetChanged()

        recycleView.refreshComplete()

        if (bean.rows.size < 10) {
//
//            recycleView.setLoadingMoreEnabled(false)
            var te = TextView(activity)
            te.setText("没有更多了！")
            recycleView.footView.addTopMargin(ScreenUtils.dipTopx(activity, 20f))
            recycleView.footView.addBottomMargin(ScreenUtils.dipTopx(activity, 20f))

            recycleView.setNoMore(true)
            recycleView.setFootViewText("正在加载", "没有更多了！")
        }
    }

    var mStyleBean: fuckNetbean<BrandBeanCollection.BrandStyleBean>? = null
    private lateinit var mBrandBean: BrandBeanCollection.BrandBean

    fun setData(cateryBean: BrandBeanCollection.BrandBean): Fragment {
        mBrandBean = cateryBean
        return this
    }

    override fun initData() {
        presenter.getStyleList(mBrandBean.id)
    }

    var mAdapter: BrandTabAdapter? = null
    override fun initView() {
        tabLayout.addOnTabSelectedListener(this)

        mAdapter = BrandTabAdapter(activity, mList)
        recycleView.adapter = mAdapter
        recycleView.setLayoutManager(GridLayoutManager(activity, 2))

        recycleView.setPullRefreshEnabled(false)

        recycleView.setLoadingMoreEnabled(true)
        recycleView.isNestedScrollingEnabled = false

        recycleView.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onLoadMore() {
                mBrandBean?.let {

                    if (tabLayout.selectedTabPosition == 0) {
                        presenter.getBrandItemList(mBrandBean.id, mCurrentIndx)
                    } else {
                        presenter.getBrandItemList(
                            mBrandBean.id,
                            mCurrentIndx,
                            mStyleBean!!.data.get(tabLayout.selectedTabPosition - 1).id
                        )
                    }
                }
            }

            override fun onRefresh() {
            }

        })
    }

}