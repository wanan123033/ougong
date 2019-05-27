package com.ougong.shop.activity.mainHomepage

import android.os.Bundle
import android.support.v7.widget.StaggeredGridLayoutManager
import com.baigui.commonlib.utils.LogUtils
import com.baigui.commonview.NetFrgment
import com.ougong.shop.Bean.*
import com.ougong.shop.R
import com.ougong.shop.V1.HomepagerRecycleAdapter
import com.ougong.shop.activity.MainLabeFrgment.LabeContract
import com.ougong.shop.adapter.HomePageAdapter
import com.ougong.shop.base_mvp.base.BaseFragment
import io.armcha.ribble.presentation.utils.extensions.log
import kotlinx.android.synthetic.main.fragment_homepage.*
import kotlinx.android.synthetic.main.include_back_title.*

class HomePageFragment : BaseFragment<MainHomePageContract.View, MainHomePageContract.Presenter>(),
    MainHomePageContract.View, HomePageAdapter.onBannerchange {
    override fun onchange(postion: Int, bannerPostion: Int) {

        if (mbean == null){
            return
        }
        if (postion == 1) {
            presenter.getProduct1(mbean!!.data[0].child[0].child[bannerPostion].id)
        }
        if (postion == 2){
            presenter.getProduct2(mbean!!.data[0].child[0].child[bannerPostion].id)
        }
    }

    var mbean: fuckNetbean<CateryBean>? = null
    override fun getAllListSucess(bean: fuckNetbean<CateryBean>) {
        mHomepagerRecycleAdapter?.setData(bean)
        mbean = bean

        //    presenter.getProduct(mbean!!.data[0].child[0].child[0].id)
    }


    override fun getProduceSucess1(bean: Netbean<fuckProcudtList<ProductListItem>>) {


        mHomepagerRecycleAdapter?.setShowData(bean.data, 1)


    }


    override fun getProduceSucess2(bean: Netbean<fuckProcudtList<ProductListItem>>) {

        mHomepagerRecycleAdapter?.setShowData(bean.data, 2)

    }

    override fun initPresenter() = HomepagePresenter()

    override fun setContentViewSource() = R.layout.fragment_homepage

    private var mHomepagerRecycleAdapter: HomePageAdapter? = null

    init {
//        homepagerRecycleAdapter = HomepagerRecycleAdapter(activity)
    }

    override fun initView() {
        mHomepagerRecycleAdapter = HomePageAdapter(activity)
        xcrecyclerView.setAdapter(mHomepagerRecycleAdapter)
        //这里不用自定义的流式布局也是可以的，这里这是根据特定需要简单自定义了一个
        xcrecyclerView.setLayoutManager(StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL))

        mHomepagerRecycleAdapter!!.addBannerchangeListener(this)
        presenter.getAllList()
        title_name.setText(getString(R.string.home_page_title))
    }
}