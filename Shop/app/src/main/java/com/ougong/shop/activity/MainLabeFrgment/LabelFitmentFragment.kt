package com.ougong.shop.activity.MainLabeFrgment

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import com.baigui.commonview.NetFrgment
import com.ougong.shop.Bean.CateryBean
import com.ougong.shop.Bean.fuckNetbean
import com.ougong.shop.R
import com.ougong.shop.adapter.LabelContentAdapter
import com.ougong.shop.base_mvp.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_label_fitment.*

class LabelFitmentFragment : BaseFragment<LabeContract.View, LabeContract.Presenter>(),LabeContract.View {
    override fun getDataSucess(bean: fuckNetbean<CateryBean>) {

    }

    override fun initPresenter() = LabePresenter()

    override fun setContentViewSource() = R.layout.fragment_label_fitment

    var mCateryBean : CateryBean? = null
    var mLabelContentAdapter: LabelContentAdapter? = null
    fun setData(cateryBean: CateryBean): Fragment{
        mCateryBean = cateryBean
        mLabelContentAdapter?.setData(mCateryBean!!)
        return this
    }

    override fun initView() {
        mLabelContentAdapter = LabelContentAdapter(activity)
        mLabelContentAdapter!!.setData(mCateryBean!!)
        recy.adapter = mLabelContentAdapter

        recy.setLayoutManager(LinearLayoutManager(activity))
    }

}
