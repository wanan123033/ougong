package com.ougong.shop.activity.MainLabeFrgment

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.baigui.commonlib.utils.LogUtils
import com.ougong.shop.Bean.CateryBean
import com.ougong.shop.Bean.fuckNetbean
import com.ougong.shop.R
import com.ougong.shop.base_mvp.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_label_v2.*
import kotlinx.android.synthetic.main.include_back_title.*


class LabelFragmentV2 : BaseFragment<LabeContract.View,LabeContract.Presenter>(),LabeContract.View {
    override fun initPresenter() = LabePresenter()

    override fun getDataSucess(bean: fuckNetbean<CateryBean>) {

//        tabLayout.removeAllTabs()
//        for (it in bean.data){
//            tabLayout.addTab(tabLayout.newTab().setText(it.name))
//            tabLayout.getTabAt(0)!!.setText(it.name)
//        }
        (viewpage.adapter as MyAdapter).setData(bean)
    }

    override fun setContentViewSource() =  R.layout.fragment_label_v2

    override fun initData() {
        presenter.getData()
    }

    override fun initView() {
        viewpage.adapter = MyAdapter(childFragmentManager)
        tabLayout.setupWithViewPager(viewpage)
        title_name.setText("分类")
    }

    inner class MyAdapter(fm:FragmentManager) : FragmentPagerAdapter(fm){
        override fun getItem(p0: Int): Fragment = LabelFitmentFragment().setData(mFuckNetbean!!.data[p0])

        var mFuckNetbean : fuckNetbean<CateryBean>? = null
        fun setData(data: fuckNetbean<CateryBean>){
            mFuckNetbean = data
            notifyDataSetChanged()
        }
        override fun getCount() = if (mFuckNetbean == null){0} else{
            mFuckNetbean!!.data.size}

        override fun getPageTitle(position: Int): CharSequence? {
            return mFuckNetbean?.data?.get(position)?.name
        }

    }

}