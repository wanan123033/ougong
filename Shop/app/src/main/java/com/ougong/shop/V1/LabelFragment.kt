package com.ougong.shop.V1

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.baigui.commonview.BaseFrgment
import com.ougong.shop.R
import com.ougong.shop.activity.MainLabeFrgment.LabelFitmentFragment
import kotlinx.android.synthetic.main.fragment_label.*
import q.rorbin.verticaltablayout.adapter.TabAdapter
import q.rorbin.verticaltablayout.widget.ITabView
import java.util.*
import kotlin.collections.HashMap

class LabelFragment : BaseFrgment() {
    override fun setContentViewSource() =  R.layout.fragment_label

    override fun initData() {
    }
    override fun initView() {
//        label_recycle_view.adapter = ExpandableRecyclerViewAdapter(activity!!)
//        label_recycle_view.setLayoutManager(LinearLayoutManager(activity))

//        mVerticalTabLayout.setTabAdapter(MyPagerAdapter(getChildFragmentManager()))
        mLableFragmentVP.adapter = MyPagerAdapter(getChildFragmentManager())
        mVerticalTabLayout.setupWithViewPager(mLableFragmentVP)
        mLableFragmentVP.offscreenPageLimit = 4
    }


    private inner class MyPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm), TabAdapter {
        override fun getItem(p0: Int): Fragment {
            if (mFragmentMap.containsKey(p0)){
                return mFragmentMap[p0]!!
            }else{
                return getFragment(p0)
            }
        }

        private fun getFragment(position: Int) : Fragment{
            var f = when(position){
                0-> LabelFitmentFragment()
                else-> LabelFitmentFragment()
            }
            mFragmentMap.put(position,f)
            return f
        }


        var titles: List<String>?
        var mFragmentMap: HashMap<Int,Fragment> = HashMap()

        init {
            titles = ArrayList<String>()
            Collections.addAll(titles as ArrayList<String>, "家具", "软装", "品牌", "风格")
        }

        override fun getCount(): Int {
            return 4
        }

        override fun getBadge(position: Int): ITabView.TabBadge? {
            return null
        }

        override fun getIcon(position: Int): ITabView.TabIcon? {
            return null
        }

        override fun getTitle(position: Int): ITabView.TabTitle {

            return ITabView.TabTitle.Builder()
                .setContent(titles!![position])
                .setTextColor(activity?.resources?.getColor(R.color.indecate)!!, activity?.resources?.getColor(R.color.textcolor)!!)
                .build()
        }

        override fun getBackground(position: Int): Int {
            return R.drawable.vertial_tab_item_bg
        }


    }


}