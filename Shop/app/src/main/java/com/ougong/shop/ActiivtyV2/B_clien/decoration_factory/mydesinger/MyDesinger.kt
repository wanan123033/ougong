package com.ougong.shop.ActiivtyV2.B_clien.decoration_factory.mydesinger

import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.baigui.commonlib.kotlinUtils.StarusBarUtils
//import com.ougong.shop.ActiivtyV2.B_clien.cheng_yun_shang.myMoney.MyDesingerAdapter
import com.ougong.shop.ActiivtyV2.bean.DesingerBean
import com.ougong.shop.ConstString
import com.ougong.shop.R
import com.ougong.shop.base_mvp.base.BaseActivity
import kotlinx.android.synthetic.main.activity_my_desinger.*
import kotlinx.android.synthetic.main.include_setting_back_title.*

class MyDesinger : BaseActivity<MyDesingerContract.View, MyDesingerContract.Presenter>(), MyDesingerContract.View{

    override fun getDesingerListSucess(data: Array<DesingerBean>) {
        mList.clear()
        mList.addAll(data)
        mAdapter?.notifyDataSetChanged()
    }

    var mList = arrayListOf<DesingerBean>()

    override fun initPresenter() = MyDesingerPresenter()

    override fun setContentViewSource() = R.layout.activity_my_desinger

    var mShowRole = 0
    override fun initView() {
        super.initView()

        mShowRole = intent.getIntExtra(ConstString.DESINGER_PASS_NAME,2)

        StarusBarUtils.setStatusBarColor(this, R.color.f5f5f5)
        StarusBarUtils.setAndroidNativeLightStatusBar(this, true)

        if (mShowRole == 2) {
            title_name.setText("我的设计师")
        }else{
            title_name.setText("我的装修公司")
        }
            title_back.visibility = View.VISIBLE
        title_back.setOnClickListener { finish() }

        mAdapter = MyDesingerAdapter(this,mList)
        recycleView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recycleView.layoutManager = LinearLayoutManager(this)
        recycleView.adapter = mAdapter
        presenter.getDesingerList(mShowRole)
    }
    var mAdapter : MyDesingerAdapter? = null
}
