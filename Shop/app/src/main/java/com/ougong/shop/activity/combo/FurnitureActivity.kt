package com.ougong.shop.activity.combo

import android.graphics.Color
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.ougong.shop.Bean.Netbean
import com.ougong.shop.MsgContract
import com.ougong.shop.R
import com.ougong.shop.base_mvp.base.BaseActivity
import com.ougong.shop.httpmodule.ComboTitleBean
import com.ougong.shop.utils.MessageBus
import com.ougong.shop.utils.MessageBusMessage
import com.ougong.shop.utils.Subscrition
import kotlinx.android.synthetic.main.activity_furniture.*
import kotlinx.android.synthetic.main.include_back_title.*

class FurnitureActivity :BaseActivity<FurnitureActivityContract.View,FurnitureActivityContract.Presenter>(),FurnitureActivityContract.View{
    var preNext:Int? = 0
    var mList : ArrayList<ComboTitleBean.Result> = arrayListOf()
    override fun initTitle(bean: Netbean<ComboTitleBean>) {
        viewpage.adapter = titleAdapter(supportFragmentManager).setData(bean)
        tabLayout.setupWithViewPager(viewpage)
//        frags.clear()
//        mList.clear()
//        mList.add(ComboTitleBean.Result(null,"全部",-1,0))
//        mList.addAll(bean.data.rows!!)
//        for (i in 0 .. (tabLayout.tabCount-1)){
//            frags.add(ComboStyleFragment().setData(mList.get(i)))
//        }
        for(i in 0 ..(tabLayout.tabCount-1)){
            val tabView = LayoutInflater.from(this).inflate(R.layout.tab_text, null) as TextView
            tabView.text = (viewpage.adapter as titleAdapter).getPageTitle(i)
            tabLayout.getTabAt(i)!!.customView = tabView
            tabView.setOnClickListener {
                tabLayout.getTabAt(i)?.select()
                if (preNext == i){
                    (viewpage.adapter as titleAdapter).frags[i].initStyle()
                }else{
                    (viewpage.adapter as titleAdapter).frags[i].showStyle()
                }
                preNext = i
                for (j in 0 .. (tabLayout.tabCount-1)){
                    (tabLayout.getTabAt(j)!!.customView as TextView).setTextColor(Color.parseColor("#999999"))
                }
                (tabLayout.getTabAt(i)!!.customView as TextView).setTextColor(Color.BLACK)
            }
        }
        if(tabLayout.tabCount>=1){
            tabLayout.getTabAt(0)?.select()
            (tabLayout.getTabAt(0)!!.customView as TextView).setTextColor(Color.BLACK)
        }



    }

    override fun initPresenter() = FurniturePresenter()

    override fun setContentViewSource(): Int {
        return R.layout.activity_furniture
    }

    override fun initView() {
        super.initView()
        title_name.text = "套餐"
        title_back.visibility = View.VISIBLE
        title_back.setOnClickListener {
            finish()
        }
        presenter.getOneTitle()
        MessageBus.getBus().register(this)
    }

    class titleAdapter (fm : FragmentManager) : FragmentPagerAdapter(fm){
        var frags:ArrayList<ComboStyleFragment> = arrayListOf()
        var titles:ArrayList<ComboTitleBean.Result> = arrayListOf()
        override fun getItem(p0: Int): ComboStyleFragment {
            return frags[p0]
        }

        override fun getCount(): Int {
            return frags.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titles[position].name
        }

        fun setData(bean: Netbean<ComboTitleBean>): titleAdapter? {
            titles.clear()
            titles.add(ComboTitleBean.Result(null,"全部",-1,0))
            for (result in bean.data.rows!!){
                titles.add(result)
            }
            frags.clear()
            for (title in titles){
                frags.add(ComboStyleFragment().setData(title,title.name.equals("固定套餐")))
            }
            return this
        }
    }
    @Subscrition(action = MsgContract.RESH_COMBO_NUM)
    public fun onEventMessage(msg:MessageBusMessage){
        var num:Int = msg.data as Int
        title_name.text = "套餐("+num+")"
    }
}
