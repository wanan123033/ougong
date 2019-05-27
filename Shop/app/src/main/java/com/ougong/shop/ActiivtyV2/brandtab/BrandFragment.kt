package com.ougong.shop.ActiivtyV2.brandtab

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.LayoutInflater
import android.widget.TextView
import com.baigui.commonlib.utils.LogUtils
import com.ougong.shop.ActiivtyV2.bean.BrandBeanCollection
import com.ougong.shop.ActiivtyV2.brandtab.BrandTab.BrandTabFragment
import com.ougong.shop.Bean.CateryBean
import com.ougong.shop.Bean.Netbean
import com.ougong.shop.Bean.fuckNetbean
import com.ougong.shop.R
import com.ougong.shop.base_mvp.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_brand_tab.*
import kotlinx.android.synthetic.main.include_back_title.*
import q.rorbin.verticaltablayout.widget.TabView

class BrandFragment  : BaseFragment<BrandContract.View, BrandContract.Presenter>(),BrandContract.View {
    override fun initPresenter() = BrandPresenter()

    override fun getDataSucess(bean: fuckNetbean<BrandBeanCollection.BrandBean>) {

        (viewpage.adapter as brandAdapter).setData(bean)
        for(i in 0 ..(tabLayout.tabCount-1)){
            val tabView = LayoutInflater.from(activity).inflate(R.layout.tab_text, null) as TextView

            tabView.text = (viewpage.adapter as brandAdapter).getPageTitle(i)
            tabLayout.getTabAt(i)!!.customView = tabView
        }

        if(tabLayout.tabCount>=1){
            tabLayout.getTabAt(1)?.select()
        }
//        tabLayout.removeAllTabs()
//        for (it in bean.data){
//            tabLayout.addTab(tabLayout.newTab().setText(it.name))
//        }
//        tabLayout.addTab(tabLayout.newTab().setText("----------------------"))

    }

    override fun setContentViewSource() =  R.layout.fragment_brand_tab

    override fun initData() {
        presenter.getData()
    }

    override fun initView() {

        title_name.setText("品牌")

        viewpage.adapter = brandAdapter(childFragmentManager)
        tabLayout.setupWithViewPager(viewpage)

    }

    class brandAdapter(fm : FragmentManager) : FragmentPagerAdapter(fm){

        override fun getItem(p0: Int): Fragment {
            return BrandTabFragment().setData(mList[p0])
        }

        fun setData(data: fuckNetbean<BrandBeanCollection.BrandBean>){
            mList.clear()
            mList.add(BrandBeanCollection.BrandBean(null,"全部",true,0,null))
            mList.addAll(data.data)
            notifyDataSetChanged()
        }
        var mList : ArrayList<BrandBeanCollection.BrandBean> = arrayListOf()
        override fun getCount() = mList.size
//            mFuckNetbean?.let {
//                mFuckNetbean!!.data.size+1
//            }?:0


        override fun getPageTitle(position: Int): CharSequence? {
            return mList[position].name
        }
    }
}